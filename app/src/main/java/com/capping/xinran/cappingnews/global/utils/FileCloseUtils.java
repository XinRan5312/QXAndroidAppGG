package com.capping.xinran.cappingnews.global.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by qixinh on 16/4/8.
 */
public class FileCloseUtils {
    public  static void closeFile(Closeable closeable){
        if(closeable==null) return;
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  static void closeFiles(Closeable...closeables){
        if(closeables==null||closeables.length==0) return;
    }
}
