package com.defaultapps.moviebase.ui.base;

import io.reactivex.disposables.CompositeDisposable;


public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V view;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onAttach(V view) {
        this.view = view;
    }

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
