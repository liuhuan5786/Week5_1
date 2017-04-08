package com.example.day24_threecache;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

/*
三级缓存  内存缓存  最近最少使用原则,将频繁使用的数据保存到内存中 (不是持久化存储,优点是速度快)
 磁盘缓存
  网络
 */
public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ProgressBar empty;
    private BaseAdapter adapter;
    private String[] urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initListView();

        initData();
    }

    private void initData() {

        urls = ImageUrl.imageurls;

    }


    private void initListView() {

        //适配器
       adapter = new ImageAdapter(this,urls);

        listView.setAdapter(adapter);

        listView.setEmptyView(empty);
    }

    private void initView() {

        listView = (ListView) findViewById(R.id.list);
        empty = (ProgressBar) findViewById(R.id.pro);

    }
}
