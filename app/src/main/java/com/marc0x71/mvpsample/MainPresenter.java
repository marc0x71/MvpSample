package com.marc0x71.mvpsample;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;

import com.marc0x71.mvpsample.model.Number;
import com.marc0x71.mvpsimple.mvp.presenter.MvpBasePresenter;
import com.marc0x71.mvpsimple.mvp.viewstate.MvpViewStateHelper;

/**
 * Created on 15/06/2016.
 */
public class MainPresenter extends MvpBasePresenter<MainView> {

	public static final String NAME = "MainPresenter";
	private static final String TAG = "MainPresenter";
	Handler handler;
	Number number = new Number();
    MvpViewStateHelper viewStateHelper;

	public MainPresenter() {
		super();
        setViewStateListener(null);
        viewStateHelper = new MvpViewStateHelper(getViewState());
    }


    public void generate() {
        getViewState().begin();

        viewStateHelper.add(new MvpViewStateHelper.ViewUpdater() {
            @Override
            public void apply() {
                getView().showLoading();
            }
        });

		handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				number.generate();
				Log.d(TAG, "updating view, new number=" + number);

                viewStateHelper.add(new MvpViewStateHelper.ViewUpdaterWithData(number) {
                    @Override
                    protected void apply(Parcelable data) {
                        Number number = (Number) data;
                        getView().show(number.getNumber());
                    }
                });

                viewStateHelper.add(new MvpViewStateHelper.ViewUpdater() {
                    @Override
                    public void apply() {
                        getView().hideLoading();
                    }
                });

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
