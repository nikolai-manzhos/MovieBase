package com.defaultapps.moviebase.ui.main;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseActivity;
import com.defaultapps.moviebase.ui.bookmarks.BookmarksViewImpl;
import com.defaultapps.moviebase.ui.discover.DiscoverContract;
import com.defaultapps.moviebase.ui.discover.DiscoverViewImpl;
import com.defaultapps.moviebase.ui.genre.GenreViewImpl;
import com.defaultapps.moviebase.ui.home.HomeViewImpl;
import com.defaultapps.moviebase.ui.search.SearchViewImpl;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.joanzapata.iconify.widget.IconButton;
import com.roughike.bottombar.BottomBar;

import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements DiscoverContract.DiscoverCallback {

    private static final int RC_SIGN_IN = 1;

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuthState -> {
            FirebaseUser user = firebaseAuthState.getCurrentUser();
            if (user != null) {
                //Signed In Get User Details
            } else {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setIsSmartLockEnabled(false)
                                .setTheme(R.style.DarkTheme)
                                .setLogo(R.mipmap.ic_launcher_round)
                                .setProviders(
                                        AuthUI.GOOGLE_PROVIDER,
                                        AuthUI.FACEBOOK_PROVIDER,
                                        AuthUI.EMAIL_PROVIDER)
                                .build(),
                        RC_SIGN_IN);
            }
        };


        if (savedInstanceState == null) {
            selectItem(R.id.tab_home);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "You're signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
        initBottomBar();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
        bottomBar.removeOnTabSelectListener();
    }

    @Override
    public void genreSelected(String genreId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentFrame, new GenreViewImpl())
                .addToBackStack(null)
                .commit();
    }

    private void initBottomBar() {
        bottomBar.setOnTabSelectListener(this::selectItem, false);
    }

    private void selectItem(int tabId) {
        switch (tabId) {
            case R.id.tab_home:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, new HomeViewImpl())
                        .commit();
                break;
            case R.id.tab_discover:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, new DiscoverViewImpl())
                        .commit();
                break;
            case R.id.tab_bookmarks:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, new BookmarksViewImpl())
                        .commit();
                break;
            case R.id.tab_search:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFrame, new SearchViewImpl())
                        .commit();
                break;
            default:
                break;
        }
    }
}
