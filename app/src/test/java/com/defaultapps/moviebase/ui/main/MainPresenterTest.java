package com.defaultapps.moviebase.ui.main;

import com.defaultapps.moviebase.data.local.AppPreferencesManager;
import com.defaultapps.moviebase.ui.common.NavigationView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MainPresenterTest {

    @Mock
    private NavigationView view;

    @Mock
    private AppPreferencesManager preferencesManager;

    private MainContract.MainPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenterImpl(preferencesManager);
        presenter.onAttach(view);
    }

    @Test
    public void shouldCheckFirstTimeUser() {
        when(preferencesManager.getFirstTimeUser()).thenReturn(true);
        presenter.checkFirstTimeUser();
        verify(view).displayLoginActivity();
        verify(preferencesManager).setFirstTimeUser(false);
    }
}
