package com.marc0x71.mvpsimple.mvp.presenter;

import android.os.Bundle;

import com.marc0x71.mvpsimple.mvp.view.MvpView;

/**
 * Created on 16/06/2016.
 */
public interface MvpPresenter<V extends MvpView> {
    V getView();

    void attachView(V view);

    void detachView();

    boolean isClosable();

    boolean isViewAttached();

    void onRestore();

    void onNewInstance();

    void onDestroy();

    void onLoadInstanceState(Bundle bundle);

    void onSaveInstanceState(Bundle bundle);
}
