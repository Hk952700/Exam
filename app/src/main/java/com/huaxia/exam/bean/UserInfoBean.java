package com.huaxia.exam.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfoBean implements Parcelable {


    /**
     * name : 潘亿成   //姓名
     * school : 14中   //学校
     * numberplate : 1  //桌牌号
     * personSn : 12000599  //考号
     * grade : 6  //年级
     * sceneid : 1   //场次
     * landingState: 0 //登录状态
     */

    private String name;
    private String school;
    private String numberplate;
    private String personSn;
    private String grade;
    private String sceneid;
    private String landingState;


    public UserInfoBean(String name, String school, String numberplate, String personSn, String grade, String sceneid, String landingState) {
        this.name = name;
        this.school = school;
        this.numberplate = numberplate;
        this.personSn = personSn;
        this.grade = grade;
        this.sceneid = sceneid;
        this.landingState = landingState;
    }

    public UserInfoBean() {
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "name='" + name + '\'' +
                ", school='" + school + '\'' +
                ", numberplate='" + numberplate + '\'' +
                ", personSn='" + personSn + '\'' +
                ", grade='" + grade + '\'' +
                ", sceneid='" + sceneid + '\'' +
                ", landingState='" + landingState + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getNumberplate() {
        return numberplate;
    }

    public void setNumberplate(String numberplate) {
        this.numberplate = numberplate;
    }

    public String getPersonSn() {
        return personSn;
    }

    public void setPersonSn(String personSn) {
        this.personSn = personSn;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSceneid() {
        return sceneid;
    }

    public void setSceneid(String sceneid) {
        this.sceneid = sceneid;
    }

    public String getLandingState() {
        return landingState;
    }

    public void setLandingState(String landingState) {
        this.landingState = landingState;
    }

    public static Creator<UserInfoBean> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.school);
        dest.writeString(this.numberplate);
        dest.writeString(this.personSn);
        dest.writeString(this.grade);
        dest.writeString(this.sceneid);
        dest.writeString(this.landingState);
    }

    protected UserInfoBean(Parcel in) {
        this.name = in.readString();
        this.school = in.readString();
        this.numberplate = in.readString();
        this.personSn = in.readString();
        this.grade = in.readString();
        this.sceneid = in.readString();
        this.landingState = in.readString();
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel source) {
            return new UserInfoBean(source);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };
}
