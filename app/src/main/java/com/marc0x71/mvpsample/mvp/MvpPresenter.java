package com.marc0x71.mvpsample.mvp;

import android.os.Bundle;

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
