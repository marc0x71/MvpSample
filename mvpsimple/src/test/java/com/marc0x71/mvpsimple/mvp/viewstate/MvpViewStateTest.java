package com.marc0x71.mvpsimple.mvp.viewstate;

import android.os.Parcelable;

import com.marc0x71.mvpsimple.data.Value;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created on 17/06/2016.
 */
public class MvpViewStateTest {

    MvpViewState viewState;

    @Mock
    MvpViewState.ViewStateListener viewStateListener;

    @Mock
    MvpViewState.ViewStateProvider viewStateProvider;

    @Mock
    MvpViewState.ViewStateTransactionListener viewStateTransactionListener;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        viewState = new MvpViewState(null);
    }

    @Test
    public void nullListeners() {
        viewState.begin();
        viewState.add(100, null);
        viewState.end();
        assertTrue(viewState.isTransactionComplete());
        viewState.restore();
        assertFalse(viewState.isTransactionComplete());
    }

    @Test
    public void singleActions() {
        viewState = new MvpViewState(viewStateListener);
        viewState.set(100, null);
        assertTrue(viewState.isTransactionComplete());
        viewState.restore();
        verify(viewStateListener).apply(eq(100), isNull(Parcelable.class));
        assertFalse(viewState.isTransactionComplete());

        verifyNoMoreInteractions(viewStateListener);
    }

    @Test
    public void multiActions() {
        viewState = new MvpViewState(viewStateListener);
        viewState.begin();
        viewState.add(100, null);
        viewState.add(200, null);
        viewState.add(300, null);
        viewState.end();
        assertTrue(viewState.isTransactionComplete());
        viewState.restore();
        verify(viewStateListener).apply(eq(100), isNull(Parcelable.class));
        verify(viewStateListener).apply(eq(200), isNull(Parcelable.class));
        verify(viewStateListener).apply(eq(300), isNull(Parcelable.class));
        assertFalse(viewState.isTransactionComplete());

        verifyNoMoreInteractions(viewStateListener);
    }

    @Test
    public void multiActionsWithParameter() {
        viewState = new MvpViewState(viewStateListener);
        viewState.begin();
        viewState.add(100, new Value(100));
        viewState.add(200, new Value(200));
        viewState.add(300, new Value(300));
        viewState.end();
        assertTrue(viewState.isTransactionComplete());
        viewState.restore();
        verify(viewStateListener).apply(eq(100), eq(new Value(100)));
        verify(viewStateListener).apply(eq(200), eq(new Value(200)));
        verify(viewStateListener).apply(eq(300), eq(new Value(300)));
        assertFalse(viewState.isTransactionComplete());

        verifyNoMoreInteractions(viewStateListener);
    }

    @Test
    public void singleActionsWithParameter() {
        viewState = new MvpViewState(viewStateListener);
        viewState.set(100, new Value(150));
        assertTrue(viewState.isTransactionComplete());
        viewState.restore();
        verify(viewStateListener).apply(eq(100), eq(new Value(150)));
        assertFalse(viewState.isTransactionComplete());

        verifyNoMoreInteractions(viewStateListener);
    }
}