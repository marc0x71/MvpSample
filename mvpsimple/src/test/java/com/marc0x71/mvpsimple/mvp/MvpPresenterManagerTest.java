package com.marc0x71.mvpsimple.mvp;

import android.os.Bundle;

import com.marc0x71.mvpsimple.mvp.presenter.MvpPresenter;
import com.marc0x71.mvpsimple.mvp.view.MvpView;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created on 17/06/2016.
 */
public class MvpPresenterManagerTest {

    private static final String NAME1 = "presenter1";
    private static final String NAME2 = "presenter2";

    MvpPresenterManager manager = MvpPresenterManager.getInstance();
    MvpPresenter presenter1 = new MvpPresenter() {

        @Override
        public MvpView getView() {
            return null;
        }

        @Override
        public void attachView(MvpView view) {

        }

        @Override
        public void detachView() {

        }

        @Override
        public boolean isClosable() {
            return false;
        }

        @Override
        public boolean isViewAttached() {
            return false;
        }

        @Override
        public void onRestore() {

        }

        @Override
        public void onNewInstance() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public void onLoadInstanceState(Bundle bundle) {

        }

        @Override
        public void onSaveInstanceState(Bundle bundle) {

        }
    };

    @Test
    public void present() {
        manager.add(NAME1, presenter1);
        assertTrue(manager.contains(NAME1));
        assertEquals(presenter1, manager.get(NAME1));
        manager.remove(NAME1);
        assertFalse(manager.contains(NAME1));
        assertNull(manager.get(NAME1));
    }

    @Test
    public void notPresent() {
        assertFalse(manager.contains(NAME2));
        assertNull(manager.get(NAME2));
        manager.remove(NAME2);
        assertFalse(manager.contains(NAME2));
        assertNull(manager.get(NAME2));
    }
}