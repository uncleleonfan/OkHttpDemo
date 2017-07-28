package com.itheima.okhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGson = new Gson();
        loadDataSync();
//        loadDataAysnc();
//        loadNewsArrayData();
//        loadStringArray();
    }

    private void loadStringArray() {
        String url = "http://10.0.2.2:8080/zhbj/stringarray.json";
        Request request = new Request.Builder().get().url(url).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }
            //在子线程中回调
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Type type = new TypeToken<List<String>>(){}.getType();
                List<String> list = mGson.fromJson(result, type);
                for (int i = 0; i < list.size(); i++) {
                    Log.d(TAG, "onResponse: " + list.get(i));
                }
            }
        });
    }

    private void loadNewsArrayData() {
        String url = "http://10.0.2.2:8080/zhbj/array.json";
        Request request = new Request.Builder().get().url(url).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }
            //在子线程中回调
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Type type = new TypeToken<List<NewsItemBean>>(){}.getType();
                List<NewsItemBean> list = mGson.fromJson(result, type);
                for (int i = 0; i < list.size(); i++) {
                    String title = list.get(i).getTitle();
                    Log.d(TAG, "onResponse: " + title);
                }

            }
        });
    }

    private void loadDataAysnc() {
        String url = "https://api.github.com/users/uncleleonfan";
        Request request = new Request.Builder().get().url(url).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }
            //在子线程中回调
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d(TAG, "onResponse: " + result);
            }
        });
    }

    private void loadDataSync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "https://api.github.com/users/uncleleonfan";
                Request request = new Request.Builder().get().url(url).build();
                OkHttpClient okHttpClient = new OkHttpClient();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String string = response.body().string();
                    Log.d(TAG, "loadDataSync: " + string);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
