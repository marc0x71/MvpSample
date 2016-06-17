package com.marc0x71.mvpsimple.mvp.viewstate;

import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;

/**
 * Created on 17/06/2016.
 */
public class MvpViewStateHelper {
    private static final String TAG = "MvpViewStateHelper";
    private static int operationCount = 1;
    MvpViewState viewState;
    SparseArray<ViewUpdater> array = new SparseArray<>();
    MvpViewState.ViewStateListener viewStateListener = new MvpViewState.ViewStateListener() {
        @Override
        public void apply(int operation, Parcelable data) {
            Log.d(TAG, "apply: operation: " + operation + " data: " + data);
            ViewUpdater updater = array.get(operation);
            if (updater == null) {
                Log.e(TAG, "apply: unexpected updater for operation " + operation);
                return;
            }
            updater.apply();
        }
    };
    MvpViewState.ViewStateTransactionListener viewStateTransactionListener = new MvpViewState.ViewStateTransactionListener() {
        @Override
        public void begin() {
            Log.d(TAG, "begin");
            array.clear();
        }

        @Override
        public void end() {
            Log.d(TAG, "end");
            array.clear();
        }
    };

    public MvpViewStateHelper(MvpViewState viewState) {
        this.viewState = viewState;
        viewState.setViewStateListener(viewStateListener);
        viewState.setViewStateTransactionListener(viewStateTransactionListener);
    }

    public void add(ViewUpdater updater) {
        operationCount++;
        Log.d(TAG, "add: new operation: " + operationCount);
        array.append(operationCount, updater);
        updater.apply();
        viewState.add(operationCount, null);
    }

    public MvpViewState.ViewStateListener getStateListener() {
        return viewStateListener;
    }

    public interface ViewUpdater {
        void apply();
    }

    public static abstract class ViewUpdaterWithData implements ViewUpdater {
        Parcelable data;

        public ViewUpdaterWithData(Parcelable data) {
            this.data = data;
        }

        @Override
        public void apply() {
            apply(data);
        }

        protected abstract void apply(Parcelable data);
    }
}
