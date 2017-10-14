package com.defaultapps.moviebase.ui.bookmarks;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.BaseViewTest;
import com.defaultapps.moviebase.ui.base.BaseActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;


public class BookmarksViewTest extends BaseViewTest {

    @Mock
    private BookmarksPresenterImpl presenter;

    @Mock
    private FavoritesAdapter favoritesAdapter;

    private BookmarksViewImpl bookmarksView;

    @NonNull
    @Override
    protected Class provideActivityClass() {
        return BaseActivity.class;
    }

    @Nullable
    @Override
    protected Intent provideActivityIntent() {
        return null;
    }

    @Override
    protected Integer provideLayoutId() {
        return R.layout.activity_main;
    }

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        super.setup();
        MockitoAnnotations.initMocks(this);
        bookmarksView = new BookmarksViewImpl();
        bookmarksView.presenter = presenter;
        bookmarksView.favoritesAdapter = favoritesAdapter;
    }

    @Test
    public void shouldStartFragment() {
        activity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentFrame, bookmarksView)
                .commit();

        verify(fragmentComponent).inject(bookmarksView);
        verify(presenter).onAttach(bookmarksView);
    }
}
