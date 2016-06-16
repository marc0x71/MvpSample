package com.marc0x71.mvpsample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.marc0x71.mvpsample.mvp.MvpActivity;

public class MainActivity extends MvpActivity<MainView, MainPresenter> implements MainView {
    private static final String TAG = "MainActivity";
    TextView text;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.text);
        status = (TextView) findViewById(R.id.status);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().generate();
            }
        });
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        Log.d(TAG, "createPresenter");
        return new MainPresenter();
    }

    @Override
    protected MainView getView() {
        return this;
    }

    @Override
    protected String getPresenterName() {
        return MainPresenter.NAME;
    }

    @Override
    public void show(int number) {
        Log.d(TAG, "show: " + number);
        text.setText(String.format("%03d", number));
    }

    @Override
    public void showLoading() {
        Log.d(TAG, "showLoading");
        text.setText("-");
        status.setText("Caricamento in corso...");
    }

    @Override
    public void hideLoading() {
        Log.d(TAG, "hideLoading");
        status.setText("Caricamento completato!");
    }

    @Override
    public void initialize() {
        text.setText("");
        status.setText("");
    }

}
