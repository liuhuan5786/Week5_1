package com.example.day24_threecache.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lenovo on 2017/4/7.
 */

public class StorageUtils {

    public static Bitmap get(String url, Context context){

        File cache = context.getExternalFilesDir("cache");
        url = url.substring(url.lastIndexOf("/")+1);
        File file = new File(cache,url);

        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    public static void put(String url, byte[] data, Context context) {

        File cache = context.getExternalFilesDir("cache");
        url.substring(url.lastIndexOf("/"));
        File file = new File(cache,url);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(data,0,data.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
