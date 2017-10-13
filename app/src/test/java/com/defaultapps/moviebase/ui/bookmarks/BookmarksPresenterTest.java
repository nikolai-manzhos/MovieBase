package com.defaultapps.moviebase.ui.bookmarks;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BookmarksPresenterTest {

    private BookmarksPresenterImpl presenter;
    private BookmarksContract.BookmarksView view;

    @Before
    public void setup() {
        view = mock(BookmarksContract.BookmarksView.class);
        presenter = new BookmarksPresenterImpl();
        presenter.onAttach(view);
    }

    @Test
    public void shouldDisplayNoUserView() {
        presenter.displayNoUserView();

        verify(view).showNoUserMessage();
    }
}
