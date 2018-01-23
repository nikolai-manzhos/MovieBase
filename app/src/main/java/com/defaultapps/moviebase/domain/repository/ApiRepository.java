package com.defaultapps.moviebase.domain.repository;


import com.defaultapps.moviebase.data.models.responses.movie.MovieInfoResponse;
import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.data.models.responses.person.PersonInfo;

import java.util.List;
import io.reactivex.Single;

public interface ApiRepository {
    Single<List<MoviesResponse>> requestHomeData();
    Single<MoviesResponse> requestSearchResults(String query, int page);
    Single<MoviesResponse> requestGenreMovies(String genreId, int page);
    Single<MovieInfoResponse> requestMovieInfoResponse(int movieId);
    Single<PersonInfo> requestPersonInfo(int personId);
}
