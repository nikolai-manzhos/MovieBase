package com.defaultapps.moviebase.ui.user;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.firebase.FavoritesManager;
import com.defaultapps.moviebase.utils.NetworkUtil;
import com.google.firebase.auth.FirebaseUser;

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

    @Mock
    private FirebaseUser firebaseUser;

    private UserPresenterImpl presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new UserPresenterImpl(favoritesManager, networkUtil, firebaseUser);
        presenter.onAttach(view);
    }

    @Test
    public void shouldRedirectToLoginIfUserIsNull() {
        presenter = new UserPresenterImpl(favoritesManager, networkUtil, null);
        presenter.onAttach(view);
        presenter.performActionWithAccount();

        verify(view).redirectToAuth();
    }

    @Test
    public void shouldLogoutIfConnected() {
        final boolean NETWORK_STATUS = true;
        when(networkUtil.checkNetworkConnection()).thenReturn(NETWORK_STATUS);
        presenter.performActionWithAccount();

        verify(favoritesManager).invalidate();
        verify(view).logoutFromAccount();
    }

    @Test
    public void shouldShowErrorIfNotConnected() {
        final boolean NETWORK_STATUS = false;
        when(networkUtil.checkNetworkConnection()).thenReturn(NETWORK_STATUS);
        presenter.performActionWithAccount();

        verify(view).displayError(R.string.user_logout_error);
    }

    @Test
    public void shouldDisplayNoUserViewOnEmptyUser() {
        presenter = new UserPresenterImpl(favoritesManager, networkUtil, null);
        presenter.onAttach(view);

        presenter.checkUserStatus();

        verify(view).displayNoUserView();
    }

    @Test
    public void shouldDisplayUserViewOnPresentUser() {
        presenter.checkUserStatus();

        verify(view).displayUserInfoView(firebaseUser);
    }
}
