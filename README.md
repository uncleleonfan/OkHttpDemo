# 添加依赖 #
    compile 'com.squareup.okhttp3:okhttp:3.8.1'
    compile 'com.google.code.gson:gson:2.8.0'

# 发送同步请求 #
    private void loadDataSync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://10.0.2.2:8080/zhbj/categories.json";
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
# 发送异步请求 #
    private void loadDataAysnc() {
        String url = "http://10.0.2.2:8080/zhbj/categories.json";
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

# 解析字符串数组 #
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

# 解析新闻数组 #
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