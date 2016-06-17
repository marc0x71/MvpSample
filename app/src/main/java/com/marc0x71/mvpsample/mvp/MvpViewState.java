package com.marc0x71.mvpsample.mvp;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/06/2016.
 */
public class MvpViewState {

    List<State> states = new ArrayList<>();
    ViewStateListener viewStateListener;
    ViewStateProvider viewStateProvider;
    private boolean transactionComplete = false;

    public MvpViewState(ViewStateListener viewStateListener) {
        this.viewStateListener = viewStateListener;
    }

    public void restore() {
        for (int i = 0; i < states.size(); i++) {
            State state = states.get(i);
            viewStateListener.apply(state.id, state.data);
        }
        if (transactionComplete) {
            states.clear();
        }
        transactionComplete = false;
    }

    public void add(int operation, Parcelable data) {
        states.add(new State(operation, data));
    }

    public void begin() {
        transactionComplete = false;
        states.clear();
    }

    public void end() {
        transactionComplete = true;
        if (viewStateProvider.isViewActive()) {
            states.clear();
        }
    }

    public void setViewStateProvider(ViewStateProvider viewStateProvider) {
        this.viewStateProvider = viewStateProvider;
    }

    public boolean isTransactionComplete() {
        return transactionComplete;
    }

    public interface ViewStateListener {
        void apply(int operation, Parcelable data);
    }

    public interface ViewStateProvider {
        boolean isViewActive();
    }

    private class State {
        int id;
        Parcelable data;

        public State(int id, Parcelable data) {
            this.id = id;
            this.data = data;
        }
    }
}
