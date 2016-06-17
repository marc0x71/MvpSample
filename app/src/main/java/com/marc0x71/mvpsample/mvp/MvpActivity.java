package com.marc0x71.mvpsample.mvp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created on 16/06/2016.
 */
public abstract class MvpActivity<V extends MvpView, P extends MvpBasePresenter> extends AppCompatActivity {
    private P presenter = null;
    private boolean restored;

    protected abstract P createPresenter();

    protected abstract V getView();

    protected abstract String getPresenterName();

    protected P getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		initializePresenter(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
		super.onSaveInstanceState(outState, outPersistentState);
		presenter.onSaveInstanceState(outState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		presenter.onSaveInstanceState(outState);
	}

	@Override
	protected void onStart() {
        super.onStart();
        if (restored)
            presenter.onRestore();
        else
            presenter.onNewInstance();
        restored = false;
    }

	@SuppressWarnings("unchecked")
	private void initializePresenter(Bundle savedInstanceState) {
		restored = false;
        presenter = (P) MvpPresenterManager.getInstance().get(getPresenterName());
        if (presenter == null) {
            presenter = (P) MvpPresenterManager.getInstance().add(getPresenterName(), createPresenter());
			presenter.onLoadInstanceState(savedInstanceState);
		} else {
            restored = true;
        }
        presenter.attachView(getView());
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
		if (presenter.isClosable()) {
			presenter.onDestroy();
			MvpPresenterManager.getInstance().remove(getPresenterName());
			presenter = null;
		}
	}
}
