package com.defaultapps.moviebase.data.network;

import com.defaultapps.moviebase.data.models.responses.movie.MovieInfoResponse;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.models.responses.person.PersonInfo;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Api {

    @GET("movie/popular")
    Single<MoviesResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("page") int page);

    @GET("movie/upcoming")
    Single<MoviesResponse> getUpcomingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("page") int page);

    @GET("movie/now_playing")
    Single<MoviesResponse> getNowPlaying(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("page") int page);

    @GET("discover/movie")
    Single<MoviesResponse> discoverMovies(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("sort_by") String sort,
            @Query("include_adult") Boolean adult,
            @Query("page") int page,
            @Query("with_genres") String genres);

    @GET("discover/movie")
    Single<MoviesResponse> discoverMovies(
            @Query("api_key") String apiKey,
            @Query("language") String lang,
            @Query("include_adult") Boolean adult,
            @Query("page") int page,
            @Query("with_genres") String genres);

    @GET("search/movie")
    Single<MoviesResponse> getSearchQuery(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int page,
            @Query("include_adult") Boolean adult);

    @GET("movie/{movie_id}")
    Single<MovieInfoResponse> getMovieInfo(
            @Path("movie_id") int movieId,
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("append_to_response") String appendToResponse);

    @GET("person/{person_id}")
    Single<PersonInfo> getPersonInfo(
            @Path("person_id") int personId,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("append_to_response") String appendToResponse);
}
