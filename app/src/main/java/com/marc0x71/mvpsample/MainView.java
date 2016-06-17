package com.marc0x71.mvpsample;

import com.marc0x71.mvpsimple.mvp.view.MvpView;

/**
 * Created on 15/06/2016.
 */
public interface MainView extends MvpView {
    void show(int number);

    void showLoading();

    void hideLoading();

    void initialize();
}
