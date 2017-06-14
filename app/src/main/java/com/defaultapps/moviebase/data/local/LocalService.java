package com.defaultapps.moviebase.data.local;

import android.content.Context;
import android.content.res.Resources;

import com.defaultapps.moviebase.data.models.responses.genres.Genres;
import com.defaultapps.moviebase.di.ApplicationContext;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;


public class LocalService {

    private Context context; // Application context

    @Inject
    public LocalService(@ApplicationContext Context context) {
        this.context = context;
    }

    public Genres readGenresFromResources() throws IOException{
        String json;
        InputStream is = Resources.getSystem().getAssets().open("genres.json");
        int size = is.available();
        byte[] buffer = new byte[size];
        //noinspection ResultOfMethodCallIgnored
        is.read(buffer);
        is.close();
        json = new String(buffer, "UTF-8");

        return new Gson().fromJson(json, Genres.class);
    }
}
