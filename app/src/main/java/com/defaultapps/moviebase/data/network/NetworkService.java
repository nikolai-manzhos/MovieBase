package com.defaultapps.moviebase.data.network;

import com.defaultapps.moviebase.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class NetworkService {

    private Retrofit retrofit;

    @Inject
    public NetworkService() {
        retrofit = provideRetrofit();
    }

    public Api getNetworkCall() {
        return retrofit.create(Api.class);
    }

    private Retrofit provideRetrofit() {
        final String BASE_URL = "https://api.themoviedb.org/3/";
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideOkHttpClient())
                .baseUrl(BASE_URL)
                .build();
    }

    private OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        final int TIMEOUT = 20;
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }
}
