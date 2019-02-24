package com.elalex.utils;

import javax.servlet.http.HttpServletResponse;

public class GeneralUtils {
    public static void addHeader(HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }
}
