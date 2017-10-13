package com.defaultapps.moviebase.ui.user;

import com.defaultapps.moviebase.data.firebase.FavoritesManager;
import com.defaultapps.moviebase.utils.NetworkUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserPresenterTest {

    @Mock
    private FavoritesManager favoritesManager;

    @Mock
    private NetworkUtil networkUtil;

    @Mock
    private UserContract.UserView view;

    private UserPresenterImpl presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new UserPresenterImpl(favoritesManager, networkUtil);
        presenter.onAttach(view);
    }

    @Test
    public void shouldLogoutIfConnected() {
        final boolean NETWORK_STATUS = true;
        when(networkUtil.checkNetworkConnection()).thenReturn(NETWORK_STATUS);
        presenter.logout();

        verify(favoritesManager).invalidate();
        verify(view).logoutFromAccount();
    }

    @Test
    public void shouldShowErrorIfNotConnected() {
        final boolean NETWORK_STATUS = false;
        when(networkUtil.checkNetworkConnection()).thenReturn(NETWORK_STATUS);
        presenter.logout();

        verify(view).displayLogoutError();
    }
}
