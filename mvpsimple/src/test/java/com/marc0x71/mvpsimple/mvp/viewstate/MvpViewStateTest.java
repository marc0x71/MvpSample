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
import static org.mockito.Mockito.when;

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
		assertTrue(viewState.isPendingViewActions());
		viewState.restore();
		assertFalse(viewState.isPendingViewActions());
	}

    @Test
    public void singleActions() {
        viewState = new MvpViewState(viewStateListener);
		viewState.setViewStateTransactionListener(viewStateTransactionListener);
		viewState.setViewStateProvider(viewStateProvider);
		when(viewStateProvider.isViewActive()).thenReturn(false);

        viewState.set(100, null);
		assertTrue(viewState.isPendingViewActions());
		viewState.restore();
		verify(viewStateTransactionListener).begin();
		verify(viewStateListener).apply(eq(100), isNull(Parcelable.class));
		assertFalse(viewState.isPendingViewActions());

        verifyNoMoreInteractions(viewStateListener);
		verifyNoMoreInteractions(viewStateTransactionListener);
	}

    @Test
    public void multiActions() {
        viewState = new MvpViewState(viewStateListener);
		viewState.setViewStateTransactionListener(viewStateTransactionListener);
		viewState.setViewStateProvider(viewStateProvider);
		when(viewStateProvider.isViewActive()).thenReturn(false);

        viewState.begin();
        viewState.add(100, null);
        viewState.add(200, null);
        viewState.add(300, null);
        viewState.end();
		assertTrue(viewState.isPendingViewActions());
		viewState.restore();
		verify(viewStateTransactionListener).begin();
		verify(viewStateListener).apply(eq(100), isNull(Parcelable.class));
        verify(viewStateListener).apply(eq(200), isNull(Parcelable.class));
        verify(viewStateListener).apply(eq(300), isNull(Parcelable.class));
		assertFalse(viewState.isPendingViewActions());

        verifyNoMoreInteractions(viewStateListener);
		verifyNoMoreInteractions(viewStateTransactionListener);
	}

    @Test
    public void multiActionsWithParameter() {
        viewState = new MvpViewState(viewStateListener);
		viewState.setViewStateTransactionListener(viewStateTransactionListener);
		viewState.setViewStateProvider(viewStateProvider);
		when(viewStateProvider.isViewActive()).thenReturn(false);

        viewState.begin();
        viewState.add(100, new Value(100));
        viewState.add(200, new Value(200));
        viewState.add(300, new Value(300));
        viewState.end();
		assertTrue(viewState.isPendingViewActions());
		viewState.restore();
		verify(viewStateTransactionListener).begin();
		verify(viewStateListener).apply(eq(100), eq(new Value(100)));
        verify(viewStateListener).apply(eq(200), eq(new Value(200)));
        verify(viewStateListener).apply(eq(300), eq(new Value(300)));
		assertFalse(viewState.isPendingViewActions());

        verifyNoMoreInteractions(viewStateListener);
		verifyNoMoreInteractions(viewStateTransactionListener);
	}

    @Test
    public void singleActionsWithParameter() {
        viewState = new MvpViewState(viewStateListener);
		viewState.setViewStateTransactionListener(viewStateTransactionListener);
		viewState.setViewStateProvider(viewStateProvider);
		when(viewStateProvider.isViewActive()).thenReturn(false);

		viewState.set(100, new Value(150));
		assertTrue(viewState.isPendingViewActions());
		viewState.restore();
		verify(viewStateTransactionListener).begin();
		verify(viewStateListener).apply(eq(100), eq(new Value(150)));
		assertFalse(viewState.isPendingViewActions());

        verifyNoMoreInteractions(viewStateListener);
		verifyNoMoreInteractions(viewStateTransactionListener);
	}

	@Test
	public void singleActionsWithViewActive() {
		viewState = new MvpViewState(viewStateListener);
		viewState.setViewStateTransactionListener(viewStateTransactionListener);
		viewState.setViewStateProvider(viewStateProvider);
		when(viewStateProvider.isViewActive()).thenReturn(true);

		viewState.set(100, null);
		assertTrue(viewState.isPendingViewActions());
		viewState.restore();
		verify(viewStateTransactionListener).begin();
		verify(viewStateTransactionListener).end();
		assertFalse(viewState.isPendingViewActions());

		verifyNoMoreInteractions(viewStateListener);
		verifyNoMoreInteractions(viewStateTransactionListener);
	}

	@Test
	public void multiActionsWithViewActive() {
		viewState = new MvpViewState(viewStateListener);
		viewState.setViewStateTransactionListener(viewStateTransactionListener);
		viewState.setViewStateProvider(viewStateProvider);
		when(viewStateProvider.isViewActive()).thenReturn(true);

		viewState.begin();
		viewState.add(100, null);
		viewState.add(200, null);
		viewState.add(300, null);
		viewState.end();
		assertTrue(viewState.isPendingViewActions());
		viewState.restore();
		verify(viewStateTransactionListener).begin();
		verify(viewStateTransactionListener).end();
		assertFalse(viewState.isPendingViewActions());

		verifyNoMoreInteractions(viewStateListener);
		verifyNoMoreInteractions(viewStateTransactionListener);
	}

	@Test
	public void multiActionsWithParameterAndViewActive() {
		viewState = new MvpViewState(viewStateListener);
		viewState.setViewStateTransactionListener(viewStateTransactionListener);
		viewState.setViewStateProvider(viewStateProvider);
		when(viewStateProvider.isViewActive()).thenReturn(true);

		viewState.begin();
		viewState.add(100, new Value(100));
		viewState.add(200, new Value(200));
		viewState.add(300, new Value(300));
		viewState.end();
		assertTrue(viewState.isPendingViewActions());
		viewState.restore();
		verify(viewStateTransactionListener).begin();
		verify(viewStateTransactionListener).end();
		assertFalse(viewState.isPendingViewActions());

		verifyNoMoreInteractions(viewStateListener);
		verifyNoMoreInteractions(viewStateTransactionListener);
	}

	@Test
	public void singleActionsWithParameterAndViewActive() {
		viewState = new MvpViewState(viewStateListener);
		viewState.setViewStateTransactionListener(viewStateTransactionListener);
		viewState.setViewStateProvider(viewStateProvider);
		when(viewStateProvider.isViewActive()).thenReturn(true);

		viewState.set(100, new Value(150));
		assertTrue(viewState.isPendingViewActions());
		viewState.restore();
		verify(viewStateTransactionListener).begin();
		verify(viewStateTransactionListener).end();
		assertFalse(viewState.isPendingViewActions());

		verifyNoMoreInteractions(viewStateListener);
		verifyNoMoreInteractions(viewStateTransactionListener);
	}

}
