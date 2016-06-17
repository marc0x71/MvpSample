package com.marc0x71.mvpsample.mvp;

import java.util.HashMap;

/**
 * Created on 16/06/2016.
 */
public class MvpPresenterManager {

    static MvpPresenterManager instance = null;
    HashMap<String, MvpPresenter> presenters = new HashMap<>();

    public static MvpPresenterManager getInstance() {
        if (instance == null) instance = new MvpPresenterManager();
        return instance;
    }

    public MvpPresenter get(String name) {
		if (contains(name)) {
			return presenters.get(name);
        }
        return null;
    }

    public boolean contains(String name) {
        return presenters.containsKey(name);
    }

    public MvpPresenter add(String name, MvpPresenter presenter) {
        presenters.put(name, presenter);
        return presenter;
	}

	public void remove(String name) {
		if (contains(name)) {
			presenters.remove(name);
		}
	}
}
