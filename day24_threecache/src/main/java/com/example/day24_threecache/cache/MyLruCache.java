package com.example.day24_threecache.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import static android.R.attr.bitmap;

/**
 * Created by lenovo on 2017/4/7.
 * LruCache==链表
 */
//增强版的List或者Map，可以向其中存入数据
//LruCache算法，最近最少使用原则，删除哪些不常用的数据
public class MyLruCache extends LruCache<String,Bitmap> {

    //返回存入的数据的大小
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount();
    }

    public MyLruCache(int maxSize) {
        super(maxSize);


    }
}
