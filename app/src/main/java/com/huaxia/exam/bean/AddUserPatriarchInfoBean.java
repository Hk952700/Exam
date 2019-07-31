package com.huaxia.exam.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AddUserPatriarchInfoBean implements Parcelable {
    private String userName;  //学生姓名
    private String userSchool; //学生学校
    private String userNumberplate;  //桌牌号
    private int isPromotion;  //是否晋级
    private String patriarchName; //家长姓名
    private String patriarchPhone; //家长电话

    public AddUserPatriarchInfoBean() {
    }

    public AddUserPatriarchInfoBean(String userName, String userSchool, String userNumberplate, int isPromotion, String patriarchName, String patriarchPhone) {
        this.userName = userName;
        this.userSchool = userSchool;
        this.userNumberplate = userNumberplate;
        this.isPromotion = isPromotion;
        this.patriarchName = patriarchName;
        this.patriarchPhone = patriarchPhone;
    }

    @Override
    public String toString() {
        return "AddUserPatriarchInfoBean{" +
                "userName='" + userName + '\'' +
                ", userSchool='" + userSchool + '\'' +
                ", userNumberplate='" + userNumberplate + '\'' +
                ", isPromotion=" + isPromotion +
                ", patriarchName='" + patriarchName + '\'' +
                ", patriarchPhone='" + patriarchPhone + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSchool() {
        return userSchool;
    }

    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }

    public String getUserNumberplate() {
        return userNumberplate;
    }

    public void setUserNumberplate(String userNumberplate) {
        this.userNumberplate = userNumberplate;
    }

    public int getIsPromotion() {
        return isPromotion;
    }

    public void setIsPromotion(int isPromotion) {
        this.isPromotion = isPromotion;
    }

    public String getPatriarchName() {
        return patriarchName;
    }

    public void setPatriarchName(String patriarchName) {
        this.patriarchName = patriarchName;
    }

    public String getPatriarchPhone() {
        return patriarchPhone;
    }

    public void setPatriarchPhone(String patriarchPhone) {
        this.patriarchPhone = patriarchPhone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userSchool);
        dest.writeString(this.userNumberplate);
        dest.writeInt(this.isPromotion);
        dest.writeString(this.patriarchName);
        dest.writeString(this.patriarchPhone);
    }

    protected AddUserPatriarchInfoBean(Parcel in) {
        this.userName = in.readString();
        this.userSchool = in.readString();
        this.userNumberplate = in.readString();
        this.isPromotion = in.readInt();
        this.patriarchName = in.readString();
        this.patriarchPhone = in.readString();
    }

    public static final Creator<AddUserPatriarchInfoBean> CREATOR = new Creator<AddUserPatriarchInfoBean>() {
        @Override
        public AddUserPatriarchInfoBean createFromParcel(Parcel source) {
            return new AddUserPatriarchInfoBean(source);
        }

        @Override
        public AddUserPatriarchInfoBean[] newArray(int size) {
            return new AddUserPatriarchInfoBean[size];
        }
    };
}
