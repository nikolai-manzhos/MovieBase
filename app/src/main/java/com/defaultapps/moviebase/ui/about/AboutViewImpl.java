package com.defaultapps.moviebase.ui.about;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.defaultapps.moviebase.BuildConfig;
import com.defaultapps.moviebase.R;
import com.defaultapps.moviebase.di.FragmentContext;
import com.defaultapps.moviebase.ui.base.BaseFragment;
import com.defaultapps.moviebase.ui.base.Navigator;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import easybind.Layout;
import easybind.bindings.BindNavigator;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;


@Layout(id = R.layout.fragment_about, name = "About")
public class AboutViewImpl extends BaseFragment {

    private static final String GITHUB_REPO_LINK = "https://github.com/NikolayManzhos/MovieBase";

    @BindView(R.id.app_icon)
    ImageView appIcon;

    @BindView(R.id.app_name)
    TextView appName;

    @BindView(R.id.app_version)
    TextView appVersion;

    @BindView(R.id.github)
    ImageView githubIcon;

    @BindNavigator
    @FragmentContext
    @Inject
    Navigator navigator;

    @Inject
    AboutAnimationFactory animationFactory;

    private Disposable animDisposable;

    @Override
    protected void inject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        appVersion.setText(BuildConfig.VERSION_NAME);

        Single.timer(100, TimeUnit.MILLISECONDS)
                .doOnSubscribe(disposable -> animDisposable = disposable)
                .subscribe(__ -> performInAnimations(), Timber::d);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        animDisposable.dispose();
    }

    @OnClick(R.id.app_icon)
    void onIconClick() {
        appIcon.startAnimation(animationFactory.createRotateAnimation());
    }

    @OnClick(R.id.github)
    void onGithubClick() {
        navigator.openLink(GITHUB_REPO_LINK);
        githubIcon.animate()
                .setDuration(200L)
                .scaleX(1.2f)
                .scaleY(1.2f)
                .withEndAction(() -> githubIcon.animate()
                        .setDuration(200L)
                        .scaleX(1f)
                        .scaleY(1f)
                        .start())
                .start();
    }

    private void performInAnimations() {
        appIcon.startAnimation(animationFactory.createTranslateAnimation(-appIcon.getHeight()));
        githubIcon.startAnimation(animationFactory.createScaleAnimation());
    }
}
