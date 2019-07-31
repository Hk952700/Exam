package com.huaxia.exam.bean;

import android.os.Parcel;
import android.os.Parcelable;


public class UploadGradeDataBean implements Parcelable {
    /**
     * trUsername : zhangsan   用户姓名
     * trUsernum : 3              胸牌号
     * trScene : 3                 场次
     * trClass : 5              班级
     * trSchool : 十四中         学校
     * trPapernum : 1           题号
     * trTime : 1000               耗时
     * trAnswer : B           选手答案
     * trMark : 3                   分数
     * trRight : 1                  是否对错
     * trRightAnswer: A       正确答案
     * trType:4           题的类型
     * trQuestion:答案      问题
     */

    private String trUsername;
    private String trUsernum;
    private String trScene;
    private String trClass;
    private String trSchool;
    private String trPapernum;
    private String trTime;
    private String trAnswer;
    private String trMark;
    private String trRight;
    private String trRightAnswer;
    private String trType;
    private String trQuestion;



    @Override
    public String toString() {
        return "UploadGradeDataBean{" +
                "trUsername='" + trUsername + '\'' +
                ", trUsernum='" + trUsernum + '\'' +
                ", trScene='" + trScene + '\'' +
                ", trClass='" + trClass + '\'' +
                ", trSchool='" + trSchool + '\'' +
                ", trPapernum='" + trPapernum + '\'' +
                ", trTime='" + trTime + '\'' +
                ", trAnswer='" + trAnswer + '\'' +
                ", trMark='" + trMark + '\'' +
                ", trRight='" + trRight + '\'' +
                ", trRightAnswer='" + trRightAnswer + '\'' +
                ", trType='" + trType + '\'' +
                ", trQestion='" + trQuestion + '\'' +
                '}';
    }

    public String getTrUsername() {
        return trUsername;
    }

    public void setTrUsername(String trUsername) {
        this.trUsername = trUsername;
    }

    public String getTrUsernum() {
        return trUsernum;
    }

    public void setTrUsernum(String trUsernum) {
        this.trUsernum = trUsernum;
    }

    public String getTrScene() {
        return trScene;
    }

    public void setTrScene(String trScene) {
        this.trScene = trScene;
    }

    public String getTrClass() {
        return trClass;
    }

    public void setTrClass(String trClass) {
        this.trClass = trClass;
    }

    public String getTrSchool() {
        return trSchool;
    }

    public void setTrSchool(String trSchool) {
        this.trSchool = trSchool;
    }

    public String getTrPapernum() {
        return trPapernum;
    }

    public void setTrPapernum(String trPapernum) {
        this.trPapernum = trPapernum;
    }

    public String getTrTime() {
        return trTime;
    }

    public void setTrTime(String trTime) {
        this.trTime = trTime;
    }

    public String getTrAnswer() {
        return trAnswer;
    }

    public void setTrAnswer(String trAnswer) {
        this.trAnswer = trAnswer;
    }

    public String getTrMark() {
        return trMark;
    }

    public void setTrMark(String trMark) {
        this.trMark = trMark;
    }

    public String getTrRight() {
        return trRight;
    }

    public void setTrRight(String trRight) {
        this.trRight = trRight;
    }

    public String getTrRightAnswer() {
        return trRightAnswer;
    }

    public void setTrRightAnswer(String trRightAnswer) {
        this.trRightAnswer = trRightAnswer;
    }

    public String getTrType() {
        return trType;
    }

    public void setTrType(String trType) {
        this.trType = trType;
    }

    public String getTrQuestion() {
        return trQuestion;
    }

    public void setTrQuestion(String trQestion) {
        this.trQuestion = trQestion;
    }

    public UploadGradeDataBean() {
    }

    public UploadGradeDataBean(String trUsername, String trUsernum, String trScene, String trClass, String trSchool, String trPapernum, String trTime, String trAnswer, String trMark, String trRight, String trRightAnswer, String trType, String trQestion) {
        this.trUsername = trUsername;
        this.trUsernum = trUsernum;
        this.trScene = trScene;
        this.trClass = trClass;
        this.trSchool = trSchool;
        this.trPapernum = trPapernum;
        this.trTime = trTime;
        this.trAnswer = trAnswer;
        this.trMark = trMark;
        this.trRight = trRight;
        this.trRightAnswer = trRightAnswer;
        this.trType = trType;
        this.trQuestion = trQestion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.trUsername);
        dest.writeString(this.trUsernum);
        dest.writeString(this.trScene);
        dest.writeString(this.trClass);
        dest.writeString(this.trSchool);
        dest.writeString(this.trPapernum);
        dest.writeString(this.trTime);
        dest.writeString(this.trAnswer);
        dest.writeString(this.trMark);
        dest.writeString(this.trRight);
        dest.writeString(this.trRightAnswer);
        dest.writeString(this.trType);
        dest.writeString(this.trQuestion);
    }

    protected UploadGradeDataBean(Parcel in) {
        this.trUsername = in.readString();
        this.trUsernum = in.readString();
        this.trScene = in.readString();
        this.trClass = in.readString();
        this.trSchool = in.readString();
        this.trPapernum = in.readString();
        this.trTime = in.readString();
        this.trAnswer = in.readString();
        this.trMark = in.readString();
        this.trRight = in.readString();
        this.trRightAnswer = in.readString();
        this.trType = in.readString();
        this.trQuestion = in.readString();
    }

    public static final Creator<UploadGradeDataBean> CREATOR = new Creator<UploadGradeDataBean>() {
        @Override
        public UploadGradeDataBean createFromParcel(Parcel source) {
            return new UploadGradeDataBean(source);
        }

        @Override
        public UploadGradeDataBean[] newArray(int size) {
            return new UploadGradeDataBean[size];
        }
    };


}
