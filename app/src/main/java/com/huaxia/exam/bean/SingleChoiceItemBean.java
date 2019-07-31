package com.huaxia.exam.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class SingleChoiceItemBean implements Parcelable {
    private String option;  //选项
    private String context;  //内容
    private boolean isAnswer;  //是否是答案
    private boolean isChecked; //是否选中

    public SingleChoiceItemBean() {

    }

    public SingleChoiceItemBean(String option, String context, boolean isAnswer, boolean isChecked) {
        this.option = option;
        this.context = context;
        this.isAnswer = isAnswer;
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "SingleChoiceItemBean{" +
                "option='" + option + '\'' +
                ", context='" + context + '\'' +
                ", isAnswer=" + isAnswer +
                ", isChecked=" + isChecked +
                '}';
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.option);
        dest.writeString(this.context);
        dest.writeByte(this.isAnswer ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected SingleChoiceItemBean(Parcel in) {
        this.option = in.readString();
        this.context = in.readString();
        this.isAnswer = in.readByte() != 0;
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<SingleChoiceItemBean> CREATOR = new Creator<SingleChoiceItemBean>() {
        @Override
        public SingleChoiceItemBean createFromParcel(Parcel source) {
            return new SingleChoiceItemBean(source);
        }

        @Override
        public SingleChoiceItemBean[] newArray(int size) {
            return new SingleChoiceItemBean[size];
        }
    };
}
