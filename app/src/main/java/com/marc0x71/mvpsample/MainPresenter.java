package com.marc0x71.mvpsample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;

import com.marc0x71.mvpsample.model.Number;
import com.marc0x71.mvpsimple.mvp.presenter.MvpBasePresenter;
import com.marc0x71.mvpsimple.mvp.viewstate.MvpViewState;

/**
 * Created on 15/06/2016.
 */
public class MainPresenter extends MvpBasePresenter<MainView> {

	public static final String NAME = "MainPresenter";
	private static final String TAG = "MainPresenter";
	private static final int SHOW_LOADING = 100;
	private static final int SHOW_NUMBER = 101;
	private static final int HIDE_LOADING = 102;
	Handler handler;
	Number number = new Number();
	MvpViewState.ViewStateListener viewStateListener = new MvpViewState.ViewStateListener() {
		@Override
		public void apply(int operation, Parcelable data) {
			switch (operation) {
				case SHOW_LOADING:
					getView().showLoading();
					break;
				case HIDE_LOADING:
					getView().hideLoading();
					break;
				case SHOW_NUMBER:
					Number number = (Number) data;
					getView().show(number.getNumber());
					break;
			}
		}

	};

	public MainPresenter() {
		super();
		setViewStateListener(viewStateListener);
	}

	public void generate() {
		getViewState().begin();

		getView().showLoading();
		getViewState().add(SHOW_LOADING, null);

		handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				number.generate();
				Log.d(TAG, "updating view, new number=" + number);

				getView().show(number.getNumber());
				getViewState().add(SHOW_NUMBER, number);

				getView().hideLoading();
				getViewState().add(HIDE_LOADING, null);

				getViewState().end();
			}
		}, 5000);
	}

	@Override
	protected MainView getNullView() {
		return new MainView() {
			@Override
			public void show(int number) {
			}

			@Override
			public void showLoading() {
			}

			@Override
			public void hideLoading() {
			}

			@Override
			public void initialize() {

			}
		};
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}

	@Override
	public void onLoadInstanceState(Bundle bundle) {
		super.onLoadInstanceState(bundle);
		Log.d(TAG, "onLoadInstanceState");
	}

	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		Log.d(TAG, "onSaveInstanceState");
	}
}
