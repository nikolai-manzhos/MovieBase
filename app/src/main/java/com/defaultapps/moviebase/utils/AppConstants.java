package com.defaultapps.moviebase.utils;


public class AppConstants {

    public static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w300/";

    public static final String GENRE_ID = "genre_id";
    public static final String GENRE_NAME = "genre_name";

    public static final String MOVIE_ID = "movie_id";

    public static final String PERSON_ID = "staff_id";

    private AppConstants() {
        throw new AssertionError("This class cannot be instantiated.");
    }
}
