package com.elalex.utils;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneralUtils {
    public static void addHeader(HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    public static void addHeaderPost(HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
    public static String getDateTime()
    {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
       return (formatter.format(date));
    }

    public static String padLeft(String s, int n) {
        return String.format("%" + n + "s", s);
    }
    public static String padRight(String s, int n) {
        return String.format("%-" + n + "s", s);
    }
}
