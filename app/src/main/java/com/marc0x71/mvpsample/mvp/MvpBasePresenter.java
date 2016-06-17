package com.marc0x71.mvpsample.mvp;

import android.os.Bundle;

/**
 * Created on 16/06/2016.
 */
public abstract class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private V view;
    private boolean attached = false;
    private MvpViewState viewState = null;
    private boolean retainInstance = false;

    protected void setViewStateListener(MvpViewState.ViewStateListener listener) {
        viewState = new MvpViewState(listener);
        viewState.setViewStateProvider(new MvpViewState.ViewStateProvider() {
            @Override
            public boolean isViewActive() {
                return isViewAttached();
            }
        });
    }

    @Override
    public V getView() {
        return view;
    }

    @Override
    public void attachView(V view) {
        attached = true;
        this.view = view;
    }

    @Override
    public void detachView() {
        attached = false;
        this.view = getNullView();
    }

    @Override
    public boolean isClosable() {
        return retainInstance || viewState == null || viewState.isTransactionComplete();
    }

    @Override
    public boolean isViewAttached() {
        return attached;
    }

    protected abstract V getNullView();

    @Override
    public void onRestore() {
        viewState.restore();
    }

    @Override
    public void onNewInstance() {
    }

    @Override
    public void onDestroy() {
    }

    @SuppressWarnings("unused")
    protected boolean isRetainInstance() {
        return retainInstance;
    }

    @SuppressWarnings("unused")
    protected void setRetainInstance(boolean retainInstance) {
        this.retainInstance = retainInstance;
    }

    protected MvpViewState getViewState() {
        return viewState;
    }

    @Override
    public void onLoadInstanceState(Bundle bundle) {
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
    }
}
