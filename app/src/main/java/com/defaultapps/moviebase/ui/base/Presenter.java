package com.defaultapps.moviebase.ui.base;

/**
 * Created on 5/14/2017.
 */

public interface Presenter<V extends BaseView> {
    void onAttach(V view);
    void onDetach();
}
