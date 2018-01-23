package com.defaultapps.moviebase.data.local;

import android.content.res.AssetManager;

import com.defaultapps.moviebase.data.models.responses.genres.Genres;
import com.google.gson.Gson;

import java.io.InputStream;

import javax.inject.Inject;

import io.reactivex.Single;


public class LocalService {

    private AssetManager assetManager;

    @Inject
    LocalService(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public Single<Genres> readGenresFromResources(){
        return Single.fromCallable(() -> {
            String json;
            InputStream is = assetManager.open("genres.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            //noinspection ResultOfMethodCallIgnored
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            return new Gson().fromJson(json, Genres.class);
        });
    }
}
