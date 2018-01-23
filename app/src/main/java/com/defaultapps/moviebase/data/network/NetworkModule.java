package com.defaultapps.moviebase.data.network;

import com.defaultapps.moviebase.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @Singleton
    @Provides
    Api provideApi() {
        Retrofit retrofit = buildRetrofit();
        return retrofit.create(Api.class);
    }

    private Retrofit buildRetrofit() {
        final String BASE_URL = "https://api.themoviedb.org/3/";
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(buildOkHttpClient())
                .baseUrl(BASE_URL)
                .build();
    }

    private OkHttpClient buildOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        final int TIMEOUT = 10;
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }
}
