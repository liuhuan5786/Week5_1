package com.example.day24_threecache.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;

import static android.R.attr.data;
import static android.os.Build.VERSION_CODES.M;

/**
 * Created by lenovo on 2017/4/7.
 */

public class ImageAsyncTask extends AsyncTask<String,Void,Bitmap> {

    private Context context;
    private Callback mCallback;

    public ImageAsyncTask(Context context, Callback mCallback) {
        this.context = context;
        this.mCallback = mCallback;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        byte[] data = new byte[0];
        try {
            data = HttpUtils.getBytesFromUrl(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (data != null&& data.length>0) {
            StorageUtils.put(params[0],data,context);
        }

        return BitmapFactory.decodeByteArray(data,0,data.length);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            mCallback.onSuccess(bitmap);

        }else {
            mCallback.onFailure("网络异常");
        }
    }

    //接口回调
    public interface Callback {
        void onSuccess(Bitmap bitmap);

        void onFailure(String info);
    }
}
