package com.zhangheng.util;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.util.*;
import java.util.regex.Pattern;

public class NetworkUtil {

    public static String getIp() {
        InetAddress address = getInetAddresses();
        return address != null ? address.getHostAddress() : "127.0.0.1";
    }

    public static String getHostname() {
        InetAddress address = getInetAddresses();
        return address != null ? address.getHostName() : "localhost";
    }

    public static InetAddress getInetAddresses() {
        List<InetAddress> addresses = getExactInetAddresses();
        if (!addresses.isEmpty()) {
            for (InetAddress address : addresses) {
                if (address instanceof Inet4Address) {
                    return address;
                }
            }
            return addresses.get(0);
        }
        return null;
    }

    public static List<InetAddress> getExactInetAddresses() {
        List<InetAddress> inetAddressList = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface anInterface = networkInterfaces.nextElement();
                if (anInterface.isUp()) {
                    Enumeration<InetAddress> inetAddresses = anInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            try {
                                //Inet6AddressImpl.isReachable可能抛=异常: java.net.SocketException: 无法指定被请求的地址
                                if (!inetAddress.isReachable(50)) {
                                    continue;
                                }
                            } catch (IOException e) {
                                continue;
                            }
                            if (inetAddress.isSiteLocalAddress()) {
                                inetAddressList.add(inetAddress);
                            } else {
                                inetAddressList.add(0, inetAddress);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return inetAddressList;
    }

    public static int getRandomPort() {
        return getRandomPort(null, null);
    }

    public static int getRandomPort(Integer minPort, Integer maxPort) {
        int tempPort = 0;
        int tryTimes = 1000;
        Random random = null;
        if (minPort == null && maxPort == null) {
            minPort = maxPort = 0;
        } else {
            if (minPort == null) {
                minPort = 2;
            }
            if (maxPort == null) {
                maxPort = 65534;
            }
            if (minPort >= maxPort || maxPort > 65534 || minPort < 2) {
                throw new IllegalArgumentException("端口范围错误");
            }
            random = new Random();
            tryTimes = maxPort - minPort;
        }
        while (tryTimes-- > 0) {
            if (random != null) {
                tempPort = random.nextInt(maxPort) % (maxPort - minPort + 1) + minPort;
            }
            try (ServerSocket serverSocket = new ServerSocket(tempPort)) {
                return serverSocket.getLocalPort();
            } catch (IOException ignored) {
            }
        }
        return 0;
    }

    public static boolean isPortUsed(int port) {
        try (ServerSocket ignored = new ServerSocket(port)) {
            return false;
        } catch (IOException e) {
            return true;
        }
    }

    private static final String[] HEADERS_TO_TRY = {
        "X-Forwarded-For",
        "Proxy-Client-IP",
        "WL-Proxy-Client-IP",
        "HTTP_X_FORWARDED_FOR",
        "HTTP_X_FORWARDED",
        "HTTP_X_CLUSTER_CLIENT_IP",
        "HTTP_CLIENT_IP",
        "HTTP_FORWARDED_FOR",
        "HTTP_FORWARDED",
        "HTTP_VIA",
        "REMOTE_ADDR",
        "X-Real-IP",
        "X-Ngrok-IP",
    };

    /**
     * 获取用户真实IP地址
     * 不使用request.getRemoteAddr();
     * 的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * @param request     请求对象
     * @param headerNames ip地址的头部名称
     * @return 请求用户的IP地址
     */
    public static String getClientIp(HttpServletRequest request, String[] headerNames) {
        String ip = null;
        for (String header : headerNames) {
            ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                break;
            }
        }
        //如果没有代理，则获取真实ip
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.indexOf(',') > 0) {
            ip = ip.substring(0, ip.indexOf(','));
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    /**
     * 获取用户真实IP地址
     *
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        return getClientIp(request, HEADERS_TO_TRY);
    }

    private static final Pattern IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

    private static final Pattern IPV6_STD_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

    public static boolean isIPv4Address(final String input) {
        return IPV4_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6StdAddress(final String input) {
        return IPV6_STD_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6HexCompressedAddress(final String input) {
        return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6Address(final String input) {
        return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input);
    }
}
