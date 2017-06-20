package com.defaultapps.moviebase.ui.bookmarks;

import com.defaultapps.moviebase.data.usecase.BookmarksUseCaseImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BookmarksPresenterTest {
    @Mock
    BookmarksUseCaseImpl useCase;

    @Mock
    BookmarksContract.BookmarksView view;

    private final String ITEM_KEY = "Wk28JAIPNi929NA";
    private BookmarksPresenterImpl presenter;
    private TestScheduler testScheduler;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new BookmarksPresenterImpl(useCase);
        presenter.onAttach(view);
        testScheduler = new TestScheduler();
    }

    @Test
    public void removeItemFromFavoritesSuccess() throws Exception {
        Observable<Boolean> observable = Observable.just(true).subscribeOn(testScheduler);
        when(useCase.removeItemFromDatabase(anyObject())).thenReturn(observable);

        presenter.removeItemFromFavorites(ITEM_KEY);
        testScheduler.triggerActions();

        verify(view, never()).displayErrorMessage();
    }

    @Test
    public void removeItemFromFavoritesFailure() throws Exception {
        Observable<Boolean> observable = Observable.error(new Exception("Transaction error."));
        when(useCase.removeItemFromDatabase(anyObject())).thenReturn(observable);

        presenter.removeItemFromFavorites(ITEM_KEY);

        verify(view).displayErrorMessage();
    }
}
