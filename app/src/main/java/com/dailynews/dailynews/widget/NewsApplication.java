package com.dailynews.dailynews.widget;

import android.app.Application;
import android.net.Uri;

import com.blankj.utilcode.util.Utils;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

/**
 * Created by Administrator on 2017/5/7.
 */

public class NewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final OkHttpClient client = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

        final Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(client))
                .listener(new Picasso.Listener() {

                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        exception.printStackTrace();
                    }
                }).build();

        Picasso.setSingletonInstance(picasso);

        Utils.init(getApplicationContext());
    }
}
