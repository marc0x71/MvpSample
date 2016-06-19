package com.marc0x71.mvpsimple.mvp.viewstate;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/06/2016.
 */
public class MvpViewState {

    private List<State> states = new ArrayList<>();
    private ViewStateListener viewStateListener;
    private ViewStateProvider viewStateProvider;
    private ViewStateTransactionListener viewStateTransactionListener;
    private boolean pendingViewActions = false;

    public MvpViewState(ViewStateListener viewStateListener) {
        this.viewStateListener = viewStateListener;
    }

    public void restore() {
        for (int i = 0; i < states.size(); i++) {
            State state = states.get(i);
            if (viewStateListener != null) viewStateListener.apply(state.id, state.data);
        }
        if (pendingViewActions) {
            states.clear();
        }
        pendingViewActions = false;
    }

    public void add(int operation, Parcelable data) {
        states.add(new State(operation, data));
    }

    public void set(int operation, Parcelable data) {
        begin();
        add(operation, data);
        end();
    }

    public void begin() {
        pendingViewActions = false;
        states.clear();
        if (viewStateTransactionListener != null) viewStateTransactionListener.begin();
    }

    public void end() {
        pendingViewActions = true;
        if (viewStateProvider != null && viewStateProvider.isViewActive()) {
            states.clear();
            if (viewStateTransactionListener != null) viewStateTransactionListener.end();
        }
    }

    public void setViewStateProvider(ViewStateProvider viewStateProvider) {
        this.viewStateProvider = viewStateProvider;
    }

    public void setViewStateTransactionListener(ViewStateTransactionListener viewStateTransactionListener) {
        this.viewStateTransactionListener = viewStateTransactionListener;
    }

    public void setViewStateListener(ViewStateListener viewStateListener) {
        this.viewStateListener = viewStateListener;
    }

    public boolean isPendingViewActions() {
        return pendingViewActions;
    }

    public interface ViewStateListener {
        void apply(int operation, Parcelable data);
    }

    public interface ViewStateTransactionListener {
        void begin();

        void end();
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
