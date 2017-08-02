package com.defaultapps.moviebase.ui.base;

import android.support.annotation.CallSuper;

import io.reactivex.disposables.CompositeDisposable;


public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @CallSuper
    @Override
    public void onAttach(V view) {
        this.view = view;
    }

    @CallSuper
    @Override
    public void onDetach() {
        compositeDisposable.clear();
        view = null;
    }

    protected V getView() {
        return view;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

}
