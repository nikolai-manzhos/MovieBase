package com.defaultapps.moviebase.ui.base;

import android.support.annotation.CallSuper;

import com.defaultapps.easybind.PresenterClass;
import com.defaultapps.easybind.calls.OnAttach;
import com.defaultapps.easybind.calls.OnDetach;
import com.defaultapps.easybind.calls.OnDispose;
import com.defaultapps.moviebase.data.base.UseCase;

import io.reactivex.disposables.CompositeDisposable;

@PresenterClass
public abstract class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private V view;
    private UseCase[] useCases;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public BasePresenter(UseCase... useCases) {
        this.useCases = useCases;
    }

    @OnAttach
    @CallSuper
    @Override
    public void onAttach(V view) {
        this.view = view;
    }

    @OnDetach
    @CallSuper
    @Override
    public void onDetach() {
        compositeDisposable.clear();
        view = null;
    }

    @OnDispose
    @Override
    public void disposeUseCaseCalls() {
        for (UseCase useCase: useCases) {
            useCase.dispose();
        }
    }

    protected V getView() {
        return view;
    }

    protected CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

}
