package com.marc0x71.mvpsample.mvp;

/**
 * Created on 16/06/2016.
 */
public abstract class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {
    private V view;
    private boolean attached = false;
    private MvpViewState viewState = null;

    protected void setViewStateListener(MvpViewState.ViewStateListener listener) {
        viewState = new MvpViewState(listener);
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

    protected MvpViewState getViewState() {
        return viewState;
    }
}
