package com.cos.photogramstart.util;

public class Script {
    public static String back(String message) {
        // 경고창을 띄우고 뒤로
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('" + message + "');");
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString();
    }
}

