package com.example.administrator.mofang.solution;

import android.os.Parcel;
import android.os.Parcelable;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/2/16.
 */

public class Case extends BmobObject implements Parcelable {
    String type;//三阶二阶
    String name;//OLL PLL
    String caseName;// PLL 20
    BmobFile picture;//公式的图片
    String solution;//公式的具体解法


    public Case() {
    }

    public Case(String type, String name, String caseName, BmobFile picture, String solution) {
        this.type = type;
        this.name = name;
        this.caseName = caseName;
        this.picture = picture;
        this.solution = solution;
    }

    protected Case(Parcel in) {
        type = in.readString();
        name = in.readString();
        caseName = in.readString();
        solution = in.readString();
    }

    public static final Creator<Case> CREATOR = new Creator<Case>() {
        @Override
        public Case createFromParcel(Parcel in) {
            return new Case(in);
        }

        @Override
        public Case[] newArray(int size) {
            return new Case[size];
        }
    };

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public BmobFile getPicture() {
        return picture;
    }

    public void setPicture(BmobFile picture) {
        this.picture = picture;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(name);
        parcel.writeString(caseName);
        parcel.writeString(solution);
    }
}
