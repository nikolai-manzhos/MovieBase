package com.defaultapps.moviebase.domain.repository;


import com.defaultapps.moviebase.data.models.responses.movie.MovieDetailResponse;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.models.responses.person.PersonResponse;

import java.util.List;

import io.reactivex.Single;

public interface ApiRepository {
    Single<List<MoviesResponse>> requestHomeData();
    Single<MoviesResponse> requestSearchResults(String query, int page);
    Single<MoviesResponse> requestGenreMovies(String genreId, int page);
    Single<MovieDetailResponse> requestMovieDetails(int movieId);
    Single<PersonResponse> requestPersonDetails(int personId);
}
