package com.hwqgooo.databinding.model.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.hwqgooo.databinding.BR;

/**
 * Created by weiqiang on 2016/6/10.
 */
public class Girl extends BaseObservable implements Parcelable {
    public static final Creator<Girl> CREATOR = new Creator<Girl>() {
        @Override
        public Girl createFromParcel(Parcel in) {
            return new Girl(in);
        }

        @Override
        public Girl[] newArray(int size) {
            return new Girl[size];
        }
    };
    private String desc;
    private String url;

    public Girl(String desc, String url) {
        this.desc = desc;
        this.url = url;
    }

    protected Girl(Parcel in) {
        desc = in.readString();
        url = in.readString();
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    @Bindable
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(BR.desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(desc);
        parcel.writeString(url);
    }
}
