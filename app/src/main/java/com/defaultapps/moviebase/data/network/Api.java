package com.defaultapps.moviebase.data.network;

import com.defaultapps.moviebase.data.models.responses.movie.MovieInfoResponse;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.models.responses.person.PersonInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
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

    @GET("discover/movie")
    Observable<MoviesResponse> discoverMovies(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("sort_by") String sort,
            @Query("include_adult") Boolean adult,
            @Query("page") int page,
            @Query("with_genres") String genres);

    @GET("discover/movie")
    Observable<MoviesResponse> discoverMovies(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("include_adult") Boolean adult,
            @Query("page") int page,
            @Query("with_genres") String genres);

    @GET("search/movie")
    Observable<MoviesResponse> getSearchQuery(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int page,
            @Query("include_adult") Boolean adult);

    @GET("movie/{movie_id}")
    Observable<MovieInfoResponse> getMovieInfo(
            @Path("movie_id") int movieId,
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("append_to_response") String appendToResponse);

    @GET("person/{person_id}")
    Observable<PersonInfo> getPersonInfo(
            @Path("person_id") int personId,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("append_to_response") String appendToResponse);
}
