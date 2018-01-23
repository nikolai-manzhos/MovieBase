package com.defaultapps.moviebase.domain.usecase;

import com.defaultapps.moviebase.data.firebase.FavoritesManager;
import com.defaultapps.moviebase.data.models.responses.movie.MovieInfoResponse;
import com.defaultapps.moviebase.domain.repository.ApiRepository;
import com.defaultapps.moviebase.utils.ResponseOrError;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import javax.inject.Provider;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.TestScheduler;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieUseCaseTest {

    @Mock
    private ApiRepository apiRepository;

    @Mock
    private Provider<FirebaseUser> firebaseUserProvider;

    @Mock
    private FavoritesManager favoritesManager;

    private MovieUseCase movieUseCase;
    private TestScheduler testScheduler;
    private final int MOVIE_ID = 9876021;


    private MovieInfoResponse actualResponse;
    private Boolean actualFabState;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        movieUseCase = new MovieUseCaseImpl(apiRepository,
                favoritesManager,
                firebaseUserProvider);
    }

    @Test
    public void requestMovieDataSuccess() throws Exception {
        MovieInfoResponse expectedResponse = new MovieInfoResponse();
        FirebaseUser fakeUser = mock(FirebaseUser.class);
        expectedResponse.setId(MOVIE_ID);
        Single<MovieInfoResponse> single = Single.just(expectedResponse).subscribeOn(testScheduler);

        when(apiRepository.requestMovieInfoResponse(MOVIE_ID))
                .thenReturn(single);
        when(firebaseUserProvider.get()).thenReturn(fakeUser);

        movieUseCase.requestMovieData(MOVIE_ID, false).subscribe(
                movieInfoResponse -> actualResponse = movieInfoResponse,
                err -> {}
        );
        testScheduler.triggerActions();

        verify(favoritesManager).fetchAllFavs();
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getCurrentStateSuccess() throws Exception {
        Observable<Boolean> observable = Observable.just(true).subscribeOn(testScheduler);

        when(favoritesManager.getIsFavoriteObservable(MOVIE_ID)).thenReturn(observable);

        movieUseCase.getCurrentState(MOVIE_ID).subscribe(
                isActive -> actualFabState = isActive,
                err -> {}
        );
        testScheduler.triggerActions();

        assertEquals(true, (boolean) actualFabState);

    }

    @Test
    public void addOrRemoveFromFavoritesSuccess() throws Exception {
        Observable<ResponseOrError<Boolean>> observable = Observable.just(ResponseOrError.fromData(true)).subscribeOn(testScheduler);
        when(favoritesManager.addOrRemoveFromFavs(anyInt(), anyString())).thenReturn(observable);

        final String POSTER_PATH = "/aJn9XeesqsrSLKcHfHP4u5985hn.jpg";
        movieUseCase.addOrRemoveFromDatabase(MOVIE_ID, POSTER_PATH).subscribe(
                responseOrError -> assertEquals(responseOrError.isData(), true),
                err -> {}
        );
        testScheduler.triggerActions();
    }

    @Test
    public void shouldDisposeOnForce() throws Exception {
        Field disposableField = MovieUseCaseImpl.class.getDeclaredField("movieInfoDisposable");
        disposableField.setAccessible(true);

        Disposable disposable = mock(Disposable.class);
        disposableField.set(movieUseCase, disposable);
        setupEmptyResponse();

        movieUseCase.requestMovieData(MOVIE_ID, true);
        verify(disposable).dispose();
    }

    @Test
    public void shouldDisposeOnNewMovieId() throws Exception {
        Field currentIdField = MovieUseCaseImpl.class.getDeclaredField("currentId");
        Field disposableField = MovieUseCaseImpl.class.getDeclaredField("movieInfoDisposable");
        currentIdField.setAccessible(true);
        disposableField.setAccessible(true);

        Disposable disposable = mock(Disposable.class);
        int currentId = 231;
        disposableField.set(movieUseCase, disposable);
        currentIdField.set(movieUseCase, currentId);
        setupEmptyResponse();

        movieUseCase.requestMovieData(MOVIE_ID, false);
        verify(disposable).dispose();
        final int DEFAULT_VALUE = -1;
        assertEquals(DEFAULT_VALUE, currentIdField.getInt(movieUseCase));
    }

    @Test
    public void shouldReturnUserStatus() {
        FirebaseUser fakeUser = mock(FirebaseUser.class);
        when(firebaseUserProvider.get()).thenReturn(fakeUser);
        assertTrue(movieUseCase.getUserState());
    }

    private void setupEmptyResponse() {
        Single<MovieInfoResponse> single = Single.just(new MovieInfoResponse());
        when(apiRepository.requestMovieInfoResponse(MOVIE_ID))
                .thenReturn(single);
    }
}
