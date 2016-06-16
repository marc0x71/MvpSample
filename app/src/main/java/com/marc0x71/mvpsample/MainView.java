package com.marc0x71.mvpsample;

import com.marc0x71.mvpsample.mvp.MvpView;

/**
 * Created on 15/06/2016.
 */
public interface MainView extends MvpView {
    void show(int number);

    void showLoading();

    void hideLoading();

    void initialize();
}
