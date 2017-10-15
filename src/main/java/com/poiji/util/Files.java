package com.poiji.util;

/**
 * Created by hakan on 17/01/2017.
 */
public final class Files {

    private Files() {
    }

    public static String getExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i >= 0) {
            return fileName.substring(i);
        }
        return fileName;
    }
}
