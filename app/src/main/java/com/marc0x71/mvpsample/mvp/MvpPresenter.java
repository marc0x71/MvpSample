package com.marc0x71.mvpsample.mvp;

/**
 * Created on 16/06/2016.
 */
public interface MvpPresenter<V extends MvpView> {
    V getView();

    void attachView(V view);

    void detachView();

    boolean isViewAttached();

    void onRestore();

    void onNewInstance();
}
