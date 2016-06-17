package com.marc0x71.mvpsimple.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created on 16/06/2016.
 */
public class Value implements Parcelable {
    public final Creator<Value> CREATOR = new Creator<Value>() {
        @Override
        public Value createFromParcel(Parcel in) {
            return new Value(in);
        }

        @Override
        public Value[] newArray(int size) {
            return new Value[size];
        }
    };
    private int number = 0;

    public Value() {
    }

    public Value(int number) {
        this.number = number;
    }

    protected Value(Parcel in) {
        number = in.readInt();
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

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Value value = (Value) o;

        return number == value.number;

    }

    @Override
    public int hashCode() {
        return number;
    }
}
