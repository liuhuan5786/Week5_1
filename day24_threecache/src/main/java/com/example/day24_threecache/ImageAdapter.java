package com.example.day24_threecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.day24_threecache.cache.ImageAsyncTask;
import com.example.day24_threecache.cache.MyLruCache;
import com.example.day24_threecache.cache.StorageUtils;

import static android.R.attr.bitmap;
import static android.R.attr.data;

/**
 * Created by lenovo on 2017/4/7.
 */
public class ImageAdapter extends android.widget.BaseAdapter {

    private Context context;
    private String[] urls;
    private MyLruCache mMyLruCache;

    public ImageAdapter(Context context, String[] urls) {
        this.context = context;
        this.urls = urls;

        //最大运行内存的八分之一
        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);
        mMyLruCache = new MyLruCache(maxSize);
    }

    @Override
    public int getCount() {
        return urls != null ? urls.length:0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

    ViewHolder holder = null;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item,viewGroup,false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {

            holder = (ViewHolder)view.getTag();

        }
        //绑定数据给holder
        bindData(holder,urls[i]);

        return view;


    }

    private void bindData(final ViewHolder holder, final String url) {

//三级缓存
        //内存缓存--->LruCache

        Bitmap bitmap = mMyLruCache.get(url);

        if (bitmap != null) {
            holder.mImageView.setImageBitmap(bitmap);
        }else {
            //磁盘中是否存在数据
            bitmap = StorageUtils.get(url,context);

            if (bitmap != null) {
                holder.mImageView.setImageBitmap(bitmap);
                //保存到内存缓存中
                mMyLruCache.put(url,bitmap);
            }else {
                //磁盘没有数据就行网络请求
                //异步下载
                new ImageAsyncTask(context, new ImageAsyncTask.Callback() {
                    @Override
                    public void onSuccess(Bitmap bitmap) {
                        holder.mImageView.setImageBitmap(bitmap);
                    //磁盘保存---->在异步任务中
                       // StorageUtils.put(url,bitmap,context);
                        //内存保存
                        mMyLruCache.put(url,bitmap);
                    }

                    @Override
                    public void onFailure(String info) {
                        Log.d("flag", "----------------->onFailure: " +info);
                    }
                }).execute(url);
            }
        }
    }

    private static class ViewHolder{

        private ImageView mImageView;

        public ViewHolder(View view){

          mImageView =(ImageView) view.findViewById(R.id.img);

        }
    }
}
