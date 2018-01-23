package com.defaultapps.moviebase.data.repository;


import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import java.util.List;
import io.reactivex.Single;

public interface ApiRepository {
    Single<List<MoviesResponse>> requestHomeData();
    Single<MoviesResponse> requestSearchResults(String query, int page);
}
