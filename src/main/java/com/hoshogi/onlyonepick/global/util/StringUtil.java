package com.hoshogi.onlyonepick.global.util;

public class StringUtil {

    public static String removeFileExtension(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
