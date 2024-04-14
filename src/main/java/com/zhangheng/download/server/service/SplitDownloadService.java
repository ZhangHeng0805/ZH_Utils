package com.zhangheng.download.server.service;

import cn.hutool.crypto.digest.DigestUtil;
import com.zhangheng.download.server.entity.Range;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: ZhangHeng
 * @email: zhangheng_0805@163.com
 * @date: 2024-04-13 20:40
 * @version: 1.0
 * @description:
 */
public class SplitDownloadService {


    public static void download(File file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!file.exists()) {
            response.sendError(404, "下载的目标文件不存在！The downloaded target file does not exist!");
        }
        if (!file.isFile()) {
            response.sendError(500, "下载的目标文件不是文件！The downloaded target file is not a file!");
        }
        String filename = file.getName();
        long length = file.length();
        // 将需要下载的文件段发送到客服端，准备流.
        RandomAccessFile input = null;
        ServletOutputStream output = null;
        try {
            input = new RandomAccessFile(file, "r");
            output = response.getOutputStream();
            //最后修改时间
            long lastModified = file.lastModified();
            //初始化response.
            String encode = URLEncoder.encode(filename, "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + encode);
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", length + "");
            //文件修改时间
            response.setDateHeader("Last-Modified", lastModified);
            //过期时间
            response.setDateHeader("Expires", System.currentTimeMillis() + 604800000L);
            //输出Range到response
            if("HEAD".equalsIgnoreCase(request.getMethod())) {
                //文件签名，判断文件是否被修改
                response.setHeader("ETag", DigestUtil.md5Hex(file));
            }else {
                if (!response.isCommitted()) {
                    Range full = new Range(0, length - 1, length);
                    List<Range> ranges = new ArrayList<>();
                    dealRanges(full, request.getHeader("Range"), ranges, response, length);
                    response.setBufferSize(20480);
                    outputRange(response, ranges, input, output, full, length);
                    response.flushBuffer();
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (input != null)
                input.close();
            if (output != null)
                output.close();
        }
    }

    /**
     * 处理请求中的Range(多个range或者一个range，每个range范围)
     *
     * @param range    :
     * @param ranges   :
     * @param response :
     * @param length   :
     */
    public static void dealRanges(Range full, String range, List<Range> ranges, HttpServletResponse response,
                                  long length) throws IOException {
        if (range != null) {
            // Range 头的格式必须为 "bytes=n-n,n-n,n-n...". 如果不是此格式, 返回 416.
            if (!range.matches("^bytes=\\d*-\\d*(,\\d*-\\d*)*$")) {
                response.setHeader("Content-Range", "bytes */" + length);
                response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                return;
            }

            // 处理传入的range的每一段.
            for (String part : range.substring(6).split(",")) {
                part = part.split("/")[0];
                // 对于长度为100的文件，以下示例返回:
                // 50-80 (50 to 80), 40- (40 to length=100), -20 (length-20=80 to length=100).
                int delimiterIndex = part.indexOf("-");
                long start = Range.subLong(part, 0, delimiterIndex);
                long end = Range.subLong(part, delimiterIndex + 1, part.length());

                //如果未设置起始点，则计算的是最后的 end 个字节；设置起始点为 length-end，结束点为length-1
                //如果未设置结束点，或者结束点设置的比总长度大，则设置结束点为length-1
                if (start == -1) {
                    start = length - end;
                    end = length - 1;
                } else if (end == -1 || end > length - 1) {
                    end = length - 1;
                }

                // 检查Range范围是否有效。如果无效，则返回416.
                if (start > end) {
                    response.setHeader("Content-Range", "bytes */" + length);
                    response.sendError(HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE);
                    return;
                }
                // 添加Range范围.
                ranges.add(new Range(start, end, end - start + 1));
            }
        } else {
            //如果未传入Range，默认下载整个文件
            ranges.add(full);
        }
    }


    /**
     * output写流输出到response
     *
     * @param response :
     * @param ranges   :
     * @param input    :
     * @param output   :
     * @param full     :
     * @param length   :
     */
    public static void outputRange(HttpServletResponse response, List<Range> ranges, RandomAccessFile input,
                                   ServletOutputStream output, Range full, long length) throws IOException {
        if (ranges.isEmpty() || ranges.get(0) == full) {
            // 返回整个文件.
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Range", "bytes " + full.getStart() + "-" + full.getEnd() + "/" + full.getTotal());
            response.setHeader("Content-length", String.valueOf(full.getLength()));
            response.setStatus(HttpServletResponse.SC_OK); // 200.
            Range.copy(input, output, length, full.getStart(), full.getLength());
        } else if (ranges.size() == 1) {
            // 返回文件的一个分段.
            Range r = ranges.get(0);
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("Content-Range", "bytes " + r.getStart() + "-" + r.getEnd() + "/" + r.getTotal());
            response.setHeader("Content-length", String.valueOf(r.getLength()));
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.
            // 复制单个文件分段.
            Range.copy(input, output, length, r.getStart(), r.getLength());
        } else {
            // 返回文件的多个分段.
            response.setContentType("multipart/byteranges; boundary=MULTIPART_BYTERANGES");
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT); // 206.

            // 复制多个文件分段.
            for (Range r : ranges) {
                //为每个Range添加MULTIPART边界和标题字段
                output.println();
                output.println("--MULTIPART_BYTERANGES");
                output.println("Content-Type: application/octet-stream;charset=UTF-8");
                output.println("Content-length: " + r.getLength());
                output.println("Content-Range: bytes " + r.getStart() + "-" + r.getEnd() + "/" + r.getTotal());
                // 复制多个需要复制的文件分段当中的一个分段.
                Range.copy(input, output, length, r.getStart(), r.getLength());
            }

            // 以MULTIPART文件的边界结束.
            output.println();
            output.println("--MULTIPART_BYTERANGES--");
        }
    }

}
