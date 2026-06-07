package com.zhangheng.util;

import cn.hutool.core.io.IoUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * @author: ZhangHeng
 * @email: zhangheng_0805@163.com
 * @date: 2026/06/07 星期日 18:51
 * @version: 1.0
 * @description:
 */
public class HttpURLConnectionUtil {
    public final static int connectTimeout = 10_000;
    public final static int readTimeout = 60_000;

    public static HttpURLConnection getRequest(String urlString, Map<String, String> headers, int connectTimeout, int readTimeout) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        setHeaders(connection, headers);
        return connection;
    }

    public static HttpURLConnection getRequest(String urlString, Map<String, String> headers) throws IOException {
        return getRequest(urlString, headers, connectTimeout, readTimeout);
    }

    public static HttpURLConnection postFormRequest(String url, Map<String, String> params, Charset charset, Map<String, String> headers, int connectTimeout, int readTimeout) throws IOException {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        conn.setRequestMethod("POST");
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        conn.setDoOutput(true); // 允许输出流

        // 默认表单头
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        setHeaders(conn, headers);
        // 拼接参数
        String paramStr = buildParams(params, charset);
        try (OutputStream os = conn.getOutputStream()) {
            os.write(paramStr.getBytes());
            os.flush();
        }

        return conn;
    }

    public static HttpURLConnection postJsonRequest(String url, String json, Charset charset, Map<String, String> headers, int connectTimeout, int readTimeout) throws IOException {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        conn.setRequestMethod("POST");
        conn.setConnectTimeout(connectTimeout);
        conn.setReadTimeout(readTimeout);
        conn.setDoOutput(true);

        // 默认JSON头
        conn.setRequestProperty("Content-Type", "application/json; charset=" + charset.name());
        setHeaders(conn, headers);
        try (ByteArrayInputStream is = new ByteArrayInputStream(json.getBytes(charset));
             OutputStream os = conn.getOutputStream()) {
            IoUtil.copy(is, os);
        }
        return conn;
    }

    /**
     * 设置请求头
     */
    public static void setHeaders(HttpURLConnection conn, Map<String, String> headers) {
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 拼接表单参数
     */
    public static String buildParams(Map<String, String> params, Charset charset) throws IOException {
        if (params == null || params.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(URLEncoder.encode(entry.getKey(), charset.name()))
                    .append("=")
                    .append(URLEncoder.encode(entry.getValue(), charset.name()))
                    .append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static String buildParams(Map<String, String> params) throws IOException {
        return buildParams(params, StandardCharsets.UTF_8);
    }

    public static String responseBodyStr(HttpURLConnection connection, Charset charset) throws IOException {
        String encoding = connection.getContentEncoding();
        try (InputStream inputStream = connection.getResponseCode() == HttpURLConnection.HTTP_OK ? connection.getInputStream() : connection.getErrorStream();
             InputStream in = "gzip".equalsIgnoreCase(encoding) ? new GZIPInputStream(inputStream) : inputStream;
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, charset))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }
    }

    public static String responseBodyStr(HttpURLConnection connection) throws IOException {
        return responseBodyStr(connection, StandardCharsets.UTF_8);
    }

    public static int responseCode(HttpURLConnection connection) throws IOException {
        return connection.getResponseCode();
    }

    public static Map<String, List<String>> responseHeaders(HttpURLConnection connection) {
        return connection.getHeaderFields();
    }

    public static void close(HttpURLConnection connection) {
        if (connection != null) {
            connection.disconnect();
        }
    }

    /**
     * 从响应头中提取所有Cookie，拼接成请求头可用的 Cookie 字符串
     *
     * @param headerFields connection.getHeaderFields()
     * @return name=value; name2=value2; ...
     */
    public static String parseCookie(Map<String, List<String>> headerFields) {
        List<String> setCookies = headerFields.get("Set-Cookie");
        if (setCookies == null || setCookies.isEmpty()) {
            return null;
        }

        StringBuilder cookieSb = new StringBuilder();
        for (String setCookie : setCookies) {
            // 截取 ; 前面的部分（只保留 key=value）
            int semicolonIndex = setCookie.indexOf(";");
            if (semicolonIndex > 0) {
                String kv = setCookie.substring(0, semicolonIndex);
                cookieSb.append(kv).append("; ");
            }
        }

        // 去掉最后多余的 ;
        if (cookieSb.length() > 0) {
            cookieSb.setLength(cookieSb.length() - 2);
        }
        return cookieSb.toString();
    }
}
