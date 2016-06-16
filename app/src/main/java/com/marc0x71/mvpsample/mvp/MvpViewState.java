package com.marc0x71.mvpsample.mvp;

import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 16/06/2016.
 */
public class MvpViewState<V extends MvpView> {

    private static final String TAG = "MvpViewState";
    List<State> states = new ArrayList<>();
    ViewStateListener viewStateListener;

    public MvpViewState(ViewStateListener viewStateListener) {
        this.viewStateListener = viewStateListener;
    }

    public void restore() {
        Log.d(TAG, "restore...");
        for (int i = 0; i < states.size(); i++) {
            State state = states.get(i);
            Log.d(TAG, "restore: id=" + state.id);
            viewStateListener.apply(state.id, state.data);
        }
    }

    public void clear() {
        states.clear();
    }

    public void add(int operation, Parcelable data) {
        states.add(new State(operation, data));
    }

    public interface ViewStateListener {
        void apply(int operation, Parcelable data);
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
