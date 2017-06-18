package com.defaultapps.moviebase.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.data.firebase.LoggedUser;
import com.defaultapps.moviebase.ui.base.BaseActivity;
import com.defaultapps.moviebase.ui.bookmarks.BookmarksViewImpl;
import com.defaultapps.moviebase.ui.discover.DiscoverViewImpl;
import com.defaultapps.moviebase.ui.home.HomeViewImpl;
import com.defaultapps.moviebase.ui.search.SearchViewImpl;
import com.defaultapps.moviebase.utils.OnBackPressedListener;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roughike.bottombar.BottomBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements FirebaseAuth.AuthStateListener {

    private static final int RC_SIGN_IN = 1;
    private boolean firstTimeLaunch;

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @Inject
    LoggedUser loggedUser;

    private FirebaseAuth firebaseAuth;
    private OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        firstTimeLaunch = savedInstanceState == null;

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            if (firstTimeLaunch) {
                selectItem(R.id.tab_home);
                firstTimeLaunch = false;
            }
            Log.d("MainActivity", user.getUid());
            loggedUser.setFirebaseuser(user);
            //Signed In Get User Details
        } else {
            //noinspection deprecation
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setTheme(R.style.DarkTheme)
                            .setLogo(R.mipmap.ic_launcher_round)
                            .setProviders(
                                    AuthUI.GOOGLE_PROVIDER,
                                    AuthUI.EMAIL_PROVIDER)
                            .build(),
                    RC_SIGN_IN);
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
    public void onBackPressed() {
       if (onBackPressedListener != null && onBackPressedListener.onBackPressed()) {
           super.onBackPressed();
       } else if (onBackPressedListener == null) {
           super.onBackPressed();
       }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(this);
        initBottomBar();
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(this);
        bottomBar.removeOnTabSelectListener();
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
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
