package com.zhangheng.system;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MonitorUtil {
    /**
     * 获取每个盘符的空间
     *
     * @return {path:路径,totalSpace:总字节数,freeSpace:剩余字节数}
     */
    public static List<Map<String, Object>> getDiskSize() {
        File[] files = File.listRoots();
        List<Map<String, Object>> list = new ArrayList<>();
        for (File file : files) {
            long totalSpace = file.getTotalSpace();
            long freeSpace = file.getFreeSpace();
            String path = file.getPath();
            Map<String, Object> disk = new HashMap<>();
            disk.put("path", path);
            disk.put("totalSpace", totalSpace);
            disk.put("freeSpace", freeSpace);
            list.add(disk);
        }
        return list;
    }

    /**
     * 根据路径获取根路径大小
     *
     * @param paths
     * @return {path:路径,totalSpace:总字节数,freeSpace:剩余字节数}
     */
    public static List<Map<String, Object>> getDiskSize(String... paths) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        Set<String> filter = new HashSet<>();
        for (String path : paths) {
            FileStore fileStore = Files.getFileStore(Paths.get(path));
            Map<String, Object> disk = new HashMap<>();
            String root = fileStore.toString();
            if (filter.add(root)) {
                disk.put("path", root);
                disk.put("totalSpace", fileStore.getTotalSpace());
                disk.put("freeSpace", fileStore.getUsableSpace());
                list.add(disk);
            }
        }
        return list;
    }

    public static Map<String, Object> getJVMMemory() {
        Runtime runtime = Runtime.getRuntime();
        long totalJvmMemory = runtime.totalMemory();
        long freeJvmMemory = runtime.freeMemory();
        Map<String, Object> jvmMemoryMap = new HashMap<>();
        jvmMemoryMap.put("totalJvmMemory", totalJvmMemory);
        jvmMemoryMap.put("freeJvmMemory", freeJvmMemory);
        return jvmMemoryMap;
    }

    /**
     * 获取系统属性
     * @param property
     * java.version Java的运行环境版本
     * java.vendor Java的运行环境供应商
     * java.home Java的安装路径
     * os.name 操作系统的名称
     * os.arch 操作系统的构架
     * os.version 操作系统的版本
     * user.name 用户的账户名称
     * user.home 用户的主目录
     * user.dir 用户的当前工作目录
     * file.separator 文件分隔符
     * path.separator 路径分隔符
     * line.separator 行分隔符
     * @return
     */
    public static String getProperty(String property) {
        return System.getProperties().getProperty(property);
    }

    /**
     * 获取系统环境变量
     * @param env
     * USERNAME:获取用户名
     * COMPUTERNAME:获取计算机名
     * USERDOMAIN:获取计算机域名
     * @return
     */
    public static String getEnv(String env) {
        return System.getenv().get(env);
    }

}
