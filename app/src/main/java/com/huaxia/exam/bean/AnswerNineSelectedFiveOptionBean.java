package com.huaxia.exam.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AnswerNineSelectedFiveOptionBean implements Parcelable {
    private int index = -1;
    private String value = "";
    private boolean isChecked = false;
    private boolean visible = false;
    private boolean questionBorder = false;

    public AnswerNineSelectedFiveOptionBean() {
    }

    public AnswerNineSelectedFiveOptionBean(int index, String value, boolean isChecked, boolean visible, boolean questionBorder) {
        this.index = index;
        this.value = value;
        this.isChecked = isChecked;
        this.visible = visible;
        this.questionBorder = questionBorder;
    }

    @Override
    public String toString() {
        return "AnswerNineSelectedFiveOptionBean{" +
                "index=" + index +
                ", value='" + value + '\'' +
                ", isChecked=" + isChecked +
                ", visible=" + visible +
                ", questionBorder=" + questionBorder +
                '}';
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isQuestionBorder() {
        return questionBorder;
    }

    public void setQuestionBorder(boolean questionBorder) {
        this.questionBorder = questionBorder;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.value);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.visible ? (byte) 1 : (byte) 0);
        dest.writeInt(this.index);
        dest.writeByte(this.questionBorder ? (byte) 1 : (byte) 0);
    }

    protected AnswerNineSelectedFiveOptionBean(Parcel in) {
        this.value = in.readString();
        this.isChecked = in.readByte() != 0;
        this.visible = in.readByte() != 0;
        this.index = in.readInt();
        this.questionBorder = in.readByte() != 0;
    }

    public static final Creator<AnswerNineSelectedFiveOptionBean> CREATOR = new Creator<AnswerNineSelectedFiveOptionBean>() {
        @Override
        public AnswerNineSelectedFiveOptionBean createFromParcel(Parcel source) {
            return new AnswerNineSelectedFiveOptionBean(source);
        }

        @Override
        public AnswerNineSelectedFiveOptionBean[] newArray(int size) {
            return new AnswerNineSelectedFiveOptionBean[size];
        }
    };
}

