package com.defaultapps.moviebase.data.network;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Api {

    @GET("movie/popular")
    Observable<MoviesResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("page") int page);

    @GET("movie/upcoming")
        Observable<MoviesResponse> getUpcomingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("page") int page);

    @GET("movie/now_playing")
    Observable<MoviesResponse> getNowPlaying(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("page") int page);

    @GET("/search/movie")
    Observable<MoviesResponse> getSearchQuery(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int page,
            @Query("include_adult") Boolean adult);
}
