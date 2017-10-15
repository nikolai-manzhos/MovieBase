package com.defaultapps.moviebase.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.ui.base.BaseActivity;
import com.defaultapps.moviebase.ui.bookmarks.BookmarksViewImpl;
import com.defaultapps.moviebase.ui.common.NavigationView;
import com.defaultapps.moviebase.ui.discover.DiscoverViewImpl;
import com.defaultapps.moviebase.ui.home.HomeViewImpl;
import com.defaultapps.moviebase.ui.login.LoginActivity;
import com.defaultapps.moviebase.ui.search.SearchViewImpl;
import com.defaultapps.moviebase.utils.Utils;
import com.defaultapps.moviebase.utils.listener.OnBackPressedListener;
import com.firebase.ui.auth.AuthUI;
import com.roughike.bottombar.BottomBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.defaultapps.moviebase.utils.AppConstants.RC_LOGIN;
import static com.defaultapps.moviebase.utils.AppConstants.RC_SIGN_IN;

public class MainActivity extends BaseActivity implements NavigationView {

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @Inject
    MainPresenterImpl presenter;

    private OnBackPressedListener onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActivityComponent().inject(this);
        ButterKnife.bind(this);
        presenter.onAttach(this);
        presenter.checkFirstTimeUser();
        if (savedInstanceState == null) {
            selectItem(R.id.tab_home);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                selectItem(bottomBar.getCurrentTabId());
                Toast.makeText(getApplicationContext(), "You're signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Sign in canceled", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == RC_LOGIN) {
            if (resultCode == RESULT_OK) {
                //redirect to login activity
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setTheme(R.style.DarkTheme)
                                .setLogo(R.mipmap.ic_launcher_round)
                                .setProviders(Utils.getProvidersList())
                                .build(),
                        RC_SIGN_IN);
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
        initBottomBar();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bottomBar.removeOnTabSelectListener();
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void displayLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, RC_LOGIN);
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
