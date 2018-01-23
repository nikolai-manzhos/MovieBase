package com.defaultapps.moviebase.domain.usecase;

import com.defaultapps.moviebase.data.models.responses.movies.MoviesResponse;
import com.defaultapps.moviebase.domain.repository.ApiRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.TestScheduler;
import io.reactivex.subjects.BehaviorSubject;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GenreUseCaseTest {

    @Mock
    private ApiRepository apiRepository;

    private GenreUseCase genreUseCase;
    private TestScheduler testScheduler;

    private MoviesResponse actualResponse;
    private Throwable actualException;
    private final String GENRE_ID = "2979254";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        genreUseCase = new GenreUseCaseImpl(apiRepository);
    }

    @Test
    public void requestGenreDataSuccess() throws Exception {
        MoviesResponse expectedResponse = random(MoviesResponse.class);
        Single<MoviesResponse> single = Single.just(expectedResponse);

        when(apiRepository.requestGenreMovies(GENRE_ID, 1))
                .thenReturn(single);

        genreUseCase.requestGenreData(GENRE_ID, true).subscribe(moviesResponse -> actualResponse = moviesResponse);

        testScheduler.triggerActions();

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void requestGenreDataFailure() throws Exception {
        Exception expectedException = new Exception("Network exception.");
        Single<MoviesResponse> single = Single.error(expectedException);

        when(apiRepository.requestGenreMovies(GENRE_ID, 1))
                .thenReturn(single);

        genreUseCase.requestGenreData(GENRE_ID, true).subscribe(
                moviesResponse -> {},
                err -> actualException = err
        );

        testScheduler.triggerActions();

        assertNotNull(actualException);
        assertEquals(expectedException, actualException);
    }

    @Test
    public void requestMoreGenreDataSuccess() throws Exception {
        MoviesResponse response = provideRandomMoviesResponse();
        changeBehaviorSubject(response);

        MoviesResponse loadMoreResponse = provideRandomMoviesResponse();
        Single<MoviesResponse> single = Single.just(loadMoreResponse);

        when(apiRepository.requestGenreMovies(eq(GENRE_ID), anyInt()))
                .thenReturn(single);

        genreUseCase.requestMoreGenreData(GENRE_ID)
                .subscribe(
                        moviesResponse -> assertEquals(moviesResponse.getPage(), loadMoreResponse.getPage()),
                        err -> {}
                );

        //If previous call still ongoing it should be disposed and new one fired.
        genreUseCase.requestMoreGenreData(GENRE_ID)
                .subscribe();

        testScheduler.triggerActions();
    }

    @Test
    public void requestMoreGenreDataFailure() throws Exception {
        MoviesResponse response = provideRandomMoviesResponse();
        changeBehaviorSubject(response);

        Throwable throwable = new Throwable("Exception");
        Single<MoviesResponse> single = Single.error(throwable);

        when(apiRepository.requestGenreMovies(eq(GENRE_ID), anyInt()))
                .thenReturn(single);

        genreUseCase.requestMoreGenreData(GENRE_ID)
                .subscribe(
                        moviesResponse -> {},
                        err -> assertEquals(throwable, err)
                );

        testScheduler.triggerActions();
    }

    @Test
    public void shouldDisposeOnForce() throws Exception {
        Field disposableField = GenreUseCaseImpl.class.getDeclaredField("genreDisposable");
        disposableField.setAccessible(true);

        Disposable disposable = mock(Disposable.class);
        disposableField.set(genreUseCase, disposable);
        setupEmptyResponse();

        genreUseCase.requestGenreData(GENRE_ID, true);

        verify(disposable).dispose();
    }

    private void changeBehaviorSubject(MoviesResponse response) throws Exception {
        Field field = GenreUseCaseImpl.class.getDeclaredField("genreBehaviorSubject");
        field.setAccessible(true);
        field.set(genreUseCase, BehaviorSubject.create());
        //noinspection unchecked
        ((BehaviorSubject<MoviesResponse>)field.get(genreUseCase)).onNext(response);
    }

    private MoviesResponse provideRandomMoviesResponse() {
        return random(MoviesResponse.class);
    }

    private void setupEmptyResponse() {
        Single<MoviesResponse> single = Single.just(new MoviesResponse());

        when(apiRepository.requestGenreMovies(GENRE_ID, 1))
                .thenReturn(single);
    }
}
