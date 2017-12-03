package com.defaultapps.moviebase.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.di.ActivityContext;
import com.defaultapps.moviebase.ui.base.BaseActivity;
import com.defaultapps.moviebase.ui.base.MvpPresenter;
import com.defaultapps.moviebase.ui.base.Navigator;
import com.defaultapps.moviebase.ui.bookmarks.BookmarksViewImpl;
import com.defaultapps.moviebase.ui.common.DefaultNavigator;
import com.defaultapps.moviebase.ui.discover.DiscoverViewImpl;
import com.defaultapps.moviebase.ui.home.HomeViewImpl;
import com.defaultapps.moviebase.ui.main.MainContract.MainPresenter;
import com.defaultapps.moviebase.ui.search.SearchViewImpl;
import com.roughike.bottombar.BottomBar;

import javax.inject.Inject;

import butterknife.BindView;

import static com.defaultapps.moviebase.utils.AppConstants.RC_LOGIN;
import static com.defaultapps.moviebase.utils.AppConstants.RC_SIGN_IN;

public class MainActivity extends BaseActivity implements MainContract.MainView {

    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @Inject
    MainPresenter presenter;

    @ActivityContext
    @Inject
    DefaultNavigator defaultNavigator;

    @Override
    protected int provideLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected MvpPresenter providePresenter() {
        return presenter;
    }

    @Override
    protected Navigator provideNavigator() {
        return defaultNavigator;
    }

    @Override
    public void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.checkFirstTimeUser();
        if (savedInstanceState == null) {
            selectItem(R.id.tab_home);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                selectItem(bottomBar.getCurrentTabId());
                Toast.makeText(getApplicationContext(), "You're signed in!", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Sign in canceled", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == RC_LOGIN) {
            if (resultCode == RESULT_OK) {
                defaultNavigator.toSignInActivity();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomBar.setOnTabSelectListener(this::selectItem, false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bottomBar.removeOnTabSelectListener();
    }

    @Override
    public void displayLoginActivity() {
        defaultNavigator.toLoginActivity();
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
