package com.zhangheng.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.zhangheng.bean.MyException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.zhangheng.file.FileUtil.fileSizeStr;

/**
 * @author: ZhangHeng
 * @email: zhangheng_0805@163.com
 * @date: 2024-04-07 17:38
 * @version: 1.0
 * @description: 文件分片下载
 */
public class FileSplitDownload {

    private static ZHTransferLisenter transferLisenter;

    /**
     * 设置下载监听
     * @param transferLisenter
     */
    public void setTransferLisenter(ZHTransferLisenter transferLisenter) {
        this.transferLisenter = transferLisenter;
    }

    /**
     * 分片大小
     */
    private static int BLOCK_SIZE=2*1024*1024;
    /**
     * 临时缓存文件后缀
     */
    private static String TEMP_FILE_SUFFIX=".tmp";
    /**
     * 保存目录
     * 为空当前目录下 ./files/download/
     */
    private static String DOWNLOAD_PATH;

    private static String FILE_NAME;
    /**
     * 是否检验文件md5
     */
    private static boolean Verify_File_Md5=true;
    /**
     * 查询进度的时间间隔，单位为毫秒
     */
    private static int PROGRESS_UPDATE_INTERVAL = 1000;

    /**
     * 线程数
     */
    private static int THREAD_NUM = 10;

    /**
     * 构造
     * @param chunk 下载分块大小
     * @param threads 下载线程数
     * @param verify_md5 是否验证文件MD5
     */
    public FileSplitDownload(int chunk,int threads,boolean verify_md5) {
        if(chunk>1024)
            BLOCK_SIZE=chunk;
        if (threads>0)
            THREAD_NUM=threads;

        Verify_File_Md5=verify_md5;
    }

    /**
     * 构造
     * @param chunk 下载分块大小
     * @param threads 下载线程数
     * @param progress_interval 下载进度间隔时间（ms）
     * @param verify_md5 是否验证文件MD5
     */
    public FileSplitDownload(int chunk,int threads,int progress_interval,boolean verify_md5) {
        if(chunk>1024)
            BLOCK_SIZE=chunk;
        if (threads>0)
            THREAD_NUM=threads;
        if (progress_interval>0)
            PROGRESS_UPDATE_INTERVAL=progress_interval;
        Verify_File_Md5=verify_md5;
    }

    public FileSplitDownload() {
    }

    /**
     * 分片下载
     * @param url
     * @param save_path
     * @param filename
     * @param md5
     * @throws Exception
     */
    public void downloadSplit(String url,String save_path,String filename,String md5) throws Exception {
        download(url,save_path,filename,md5);
    }

    /**
     * 分片下载 不校验md5
     * @param url
     * @param save_path
     * @param filename
     * @throws Exception
     */
    public static void download(String url,String save_path,String filename) throws Exception {
        Verify_File_Md5=false;
        download(url,save_path,filename,null);
    }
    /**
     * 分片下载
     * @param url
     * @param save_path
     * @param filename
     * @param md5
     * @throws Exception
     */
    public static void download(String url,String save_path,String filename,String md5) throws Exception {

        if (!isRangeDownloadSupported(url))
            throw new MyException("URL does not support sharded download（不不支持分片下载）: "+url);
        // 文件名赋值
        FILE_NAME = filename;
        DOWNLOAD_PATH = save_path;
        // 获取后缀名
        // String suffix = filename.substring(filename.lastIndexOf(StrPool.DOT) + 1);
        // 获取文件名（不包含后缀）
        String name = filename.substring(0, filename.lastIndexOf("."));

        // 获取连接 得到完整文件的长度
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        long fileSize = connection.getContentLengthLong();
        String fileSizeStr=fileSizeStr(fileSize);
        // windows 跟 linux 层级分隔符
        String separator = File.separator;
        String filesDirectoryPath,tempDirectoryPath;
        if (DOWNLOAD_PATH==null) {
            // 当前所在目录
            String currentPath = System.getProperty("user.dir");
            // 设置文件下载所在目录 files/xxx
            filesDirectoryPath = currentPath + separator + "files" + separator;
            // 完整文件保存目录
            DOWNLOAD_PATH = filesDirectoryPath + "download" + separator;

        }else {
            filesDirectoryPath=DOWNLOAD_PATH;
        }

        // 临时目录名 文件名+md5 如：temp/xxx_xxx
        String tempDirectoryName = name + "_" + md5;
        // 临时目录路径 temp/文件名+md5/临时文件名 如：temp/0260e4ce2175f7632f543af764e7a3d8/xxx-temp.txt
        tempDirectoryPath = DOWNLOAD_PATH + "temp" + separator + tempDirectoryName + separator;
//        System.out.println("Download File Size:"+fileSizeStr);
//        System.out.println("Temporary file directory path:"+tempDirectoryPath);
//        System.out.println("Complete file save directory:"+DOWNLOAD_PATH);

        transferLisenter.start(new Date(),DOWNLOAD_PATH,FILE_NAME,fileSize);


        // 将下载文件的大小和分片数量计算出来
        int splitNum = (int) Math.ceil((double) fileSize / BLOCK_SIZE);
        // log.info(">>>总分片数 :_{}", splitNum);

        // 先判断目录是否存在 文件保存目录跟临时文件保存目录
        ArrayList<String> directoryPaths = new ArrayList<>();
        directoryPaths.add(tempDirectoryPath);
        directoryPaths.add(filesDirectoryPath);
        directoryPaths.add(DOWNLOAD_PATH);
        for (String directoryPath : directoryPaths) {
            Path path = Paths.get(directoryPath);
            if (!Files.isDirectory(path)) {
                try {
                    // Path absolutePath = path.toAbsolutePath();
                    // // log.info(">>>需要创建文件夹的绝对路径:{}", absolutePath);
                    Files.createDirectories(path);
                    // log.info(">>>文件夹创建成功 ...");
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }

        // 获取配置文件中的线程配置 启动线程下载每个分片
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUM);
        for (int i = 0; i < splitNum; i++) {
            int splitIndex = i;
            String tempFileName = tempDirectoryPath + i + "_" + name + TEMP_FILE_SUFFIX;
            // Path absolutePath = Paths.get(tempFileName).toAbsolutePath();
            // log.info(">>>临时文件的所在位置 :_{} ", absolutePath);
            File outFile = new File(tempFileName);
            executor.execute(() -> {
                try {
                    downloadSplit(url, splitIndex, outFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        // 等待所有分片下载完毕
        executor.shutdown();

        // 下载总进度条
        int count=0;
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(PROGRESS_UPDATE_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 统计下载进度
            long totalDownloaded = 0;
            for (int i = 0; i < splitNum; i++) {
                File tempFile = new File(tempDirectoryPath + i + "_" + name + TEMP_FILE_SUFFIX);
                totalDownloaded += tempFile.exists() ? tempFile.length() : 0;
            }

            // 已经下载的文件大小
            long downloaded = totalDownloaded;
            double progress = (double) downloaded / fileSize * 100;
            String progress_format = String.format("%.2f", progress);
//            System.out.println(DateUtil.now()+"---Downloaded: "+ progress_format +"% "+fileSizeStr(downloaded)+"/"+fileSizeStr);
            count++;
            transferLisenter.running(new Date(), PROGRESS_UPDATE_INTERVAL*count,downloaded,fileSize,progress_format);
        }

        /*用于设定超时时间及单位。
        当等待超过设定时间时，会监测ExecutorService是否已经关闭，若关闭则返回true，否则返回false。
        一般情况下会和shutdown方法组合使用。*/
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);


        // 判断分片文件是否全部下载完成
        if (!isAllChunksDownloaded(tempDirectoryPath, splitNum)) {
            throw new RuntimeException("Not all chunks are downloaded yet!");
        }

        // 合并文件
        mergeChunks(tempDirectoryPath, md5);

        // 删除临时文件目录以及临时文件
        deleteTempDirectory(tempDirectoryPath);

    }
    /**
     * 删除临时目录
     *
     * @param tempDirectoryPath 临时目录路径
     * @throws IOException ioexception
     */
    private static void deleteTempDirectory(String tempDirectoryPath) throws IOException {

        /*删除目录
        使用Java的文件IO API来遍历目标目录中的每个文件，
        其中使用了Files.walk(directory)方法来遍历目录下的所有文件，
        并且使用了File::delete方法来逐个删除目录下的文件。
        最终，通过sorted()方法来保证我们能够在删除文件之前先删除包含更多文件的目录。*/

        Path directory = Paths.get(tempDirectoryPath);
        // 检查目录是否存在
        if (!Files.isDirectory(directory)) {
            // log.info(">>>目录不存在 ...");
            return;
        }
        Files.walk(directory)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }


    /**
     * 分片下载
     *
     * @param url            url
     * @param splitIndex     第几分片
     * @param temporaryFiles 临时文件
     * @throws IOException ioexception
     */
    private static void downloadSplit(String url, int splitIndex, File temporaryFiles) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        long startByte = (long) splitIndex * BLOCK_SIZE;
        long endByte = (long) (splitIndex + 1) * BLOCK_SIZE - 1;

        // 这里判断进行断点续传
        if (temporaryFiles.exists()) {
            // 获取此临时文件还缺少的的部分
            long downloadedBytes = temporaryFiles.length();
            startByte = startByte + downloadedBytes;
            connection.setRequestProperty("Range", "bytes=" + startByte + "-" + endByte);
        } else {
            // 文件不存在说明是第一次下载，不用续传
            connection.setRequestProperty("Range", "bytes=" + startByte + "-" + endByte);
        }


        /*log.info(">>>此临时文件的起始位置 :_{}", startByte);
        log.info(">>>此临时文件的结束位置 :_{}", endByte);*/

        InputStream in = connection.getInputStream();
        RandomAccessFile out = new RandomAccessFile(temporaryFiles, "rw");
        byte[] buffer = new byte[1024];
        int len;

        if (temporaryFiles.exists()) {
            // 从尾部继续写入
            out.seek(out.length());
        }
        // 开始写入
        // log.info(">>>开始写入到此临时文件 :_{}", temporaryFiles);
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        // 关闭流
        out.close();
        in.close();

        // 关闭此连接
        connection.disconnect();
    }


    /**
     * 将文件分片合并成一个完整的文件
     *
     * @param tempDirectoryPath 分片文件所在的目录
     * @param md5               md5
     * @throws IOException ioexception
     */
    private static void mergeChunks(String tempDirectoryPath, String md5) throws IOException {
        File chunksDir = new File(tempDirectoryPath);
        // 获取分片文件列表
        List<File> chunkFiles = Arrays.stream(Objects.requireNonNull(chunksDir.listFiles((dir, name) -> name.endsWith(".tmp"))))
                .collect(Collectors.toList());
        // 按文件名升序排序
        chunkFiles = chunkFiles.stream().sorted(Comparator.comparingInt(file -> Integer.parseInt(StrUtil.subBefore(file.getName(), StrPool.UNDERLINE, false))))
                .collect(Collectors.toList());

        // 文件输出路径
        Path filePath = Paths.get(DOWNLOAD_PATH, FILE_NAME);
        // 判断文件是否存在，如果存在就加数字编号
        int index = 0;
        while (Files.exists(filePath)) {
            index++;
            String newName = addNumberSuffix(FILE_NAME, index);
            filePath = Paths.get(DOWNLOAD_PATH, newName);
        }

        File mergedFile= filePath.toFile();

        FileOutputStream os = new FileOutputStream(mergedFile, true);
        byte[] buffer = new byte[1024];
        int len;

        for (File file : chunkFiles) {
            InputStream is = Files.newInputStream(file.toPath());
            /*log.info(">>>>开始合并文件 ...");
            log.info(">>>>start_merging_files ...");*/
            while ((len = is.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }

            // 输入流关闭
            is.close();
        }
        // 流关闭
        os.close();
        // 合并完成
        String fileMd5 = DigestUtil.md5Hex(mergedFile);
//        System.out.println("Verifying file integrity MD5: "+fileMd5);
//        if (Objects.equals(fileMd5, md5)) {
        if (!Verify_File_Md5||Objects.equals(fileMd5, md5)) {
            // log.error(">>>> md5值匹配 文件完整");
//            System.out.println("File validation succeeded ");
            Path absolutePath = filePath.toAbsolutePath();
            // log.info(">>>>完整文件保存路径 :_{}", absolutePath);
//            System.out.println("The location where the file is saved : "+ absolutePath);
            transferLisenter.end(new Date(),absolutePath.toString(),fileMd5,true);
        } else {
            // log.error(">>>> md5值不匹配 文件损坏");
//            System.out.println("File validation failed ...... delete download file");
            // 提示出错重新下载 删除掉临时目录跟合并完成的文件
            deleteTempDirectory(tempDirectoryPath);
            FileUtil.del(filePath);
            transferLisenter.end(new Date(),null,fileMd5,false);
            // 提示。。。
        }
    }

    /**
     * 判断分片文件是否全部下载完成
     *
     * @param tempDirectoryPath 临时目录路径
     * @param totalChunks       总分片数
     * @return boolean
     */
    private static boolean isAllChunksDownloaded(String tempDirectoryPath, int totalChunks) {
        File chunksDir = new File(tempDirectoryPath);
        File[] files = chunksDir.listFiles(file -> file.getName().endsWith(".tmp"));
        return files != null && files.length == totalChunks;
    }


    /**
     * 添加后缀数量
     * 如第一次下载了 test.txt，下一次再下载此文件，文件保存时文件名为test(1).txt，以此类推
     *
     * @param fileName 文件名称
     * @param index    指数
     * @return {@link String}
     */
    private static String addNumberSuffix(String fileName, int index) {
        // 如果文件名没有后缀，则在文件名后面添加编号
        // 如果有后缀，则在后缀前面添加编号
        int dotIndex = fileName.lastIndexOf(".");
        StringBuilder sb = new StringBuilder(fileName.length() + 2);
        sb.append(dotIndex >= 0 ? fileName.substring(0, dotIndex) : fileName);
        sb.append("(").append(index).append(")");
        if (dotIndex >= 0) {
            sb.append(fileName.substring(dotIndex));
        }
        return sb.toString();
    }


    /**
     * 验证URL是否支持分片下载
     * @param urlStr
     * @return
     */
    public static boolean isRangeDownloadSupported(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            Map<String, String> responseHeaders = getResponseHeaders(connection);
//            System.out.println(responseHeaders);
            String Length = responseHeaders.get("Content-Length");
//            String Range = responseHeaders.get("Content-Range");
            String acceptRanges = responseHeaders.get("Accept-Ranges");
            int contentLength = Integer.parseInt(Length);
            if (acceptRanges != null && contentLength > 0) {
//                long start = 0;
//                long end = contentLength - 1;
//                String flag=acceptRanges+" "+ start + "-" + end+"/"+Length;
//                if (flag.equals(Range)) {
                    int responseCode = connection.getResponseCode();
                    return responseCode == HttpURLConnection.HTTP_PARTIAL || responseCode == HttpURLConnection.HTTP_OK;
//                }else
//                    return false;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取响应头
     * @param connection
     * @return
     * @throws Exception
     */
    private static Map<String, String> getResponseHeaders(HttpURLConnection connection) throws Exception {
        Map<String, String> headers = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {

            List<String> values = entry.getValue();
            String value="";
            for (int i = 0; i <values.size() ; i++) {
                if (i==values.size()-1){
                    value+=values.get(i);
                }else {
                    value+=values.get(i)+";";
                }
            }
            headers.put(entry.getKey(), value);
        }
        return headers;
    }
//    /**
//     * 文件大小格式化
//     * @param size 文件字节
//     * @return 格式化字符串
//     */
//    public static String fileSizeStr(Long size) {
//        double length = Double.valueOf(String.valueOf(size));
//        String[] unit = new String[]{"B", "KB", "MB", "GB", "TB","PB"};
//        int i = 0;
//        while (true) {
//            if (length < 1024) {
//                if (i == 0)
//                    return length + unit[i];
//                else
//                    return Math.round(length * 100) / 100.0+unit[i];
//            } else {
//                length = length / 1024.0;
//            }
//            i++;
//        }
//    }

    public interface ZHTransferLisenter{
        void start(Date now,String path,String fileName,Long fileSize);
        void running(Date now,int consuming_ms,Long Transferred,Long fileSize,String progress);
        void end(Date now,String save_path,String md5,Boolean verify_result);
    }

}
