package com.marc0x71.mvpsample.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

/**
 * Created on 16/06/2016.
 */
public class Number implements Parcelable {
    public final Creator<Number> CREATOR = new Creator<Number>() {
        @Override
        public Number createFromParcel(Parcel in) {
            return new Number(in);
        }

        @Override
        public Number[] newArray(int size) {
            return new Number[size];
        }
    };
    private Random random = new Random();
    private int number;

    public Number() {
    }

    protected Number(Parcel in) {
        number = in.readInt();
    }

    public void generate() {
        number = random.nextInt(10);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
    }

    public int getNumber() {
        return number;
    }
}
