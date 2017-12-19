package com.defaultapps.moviebase.ui.base;

public interface Navigator<View extends MvpView> {
    void onAttach(View v);
    void onDetach();

    void toLoginActivity();
    void toSignInActivity();
    void toMovieActivity(int movieId);

    void finishActivity();
}
