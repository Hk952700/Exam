package com.huaxia.exam.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class UserRecoderBean implements Parcelable {


    private List<TestrecordesBean> testrecordes;

    public List<TestrecordesBean> getTestrecordes() {
        return testrecordes;
    }

    public void setTestrecordes(List<TestrecordesBean> testrecordes) {
        this.testrecordes = testrecordes;
    }

    public static class TestrecordesBean implements Parcelable {
        /**
         * trUsername : 何启航                           //姓名
         * trUsernum : 4                                 //桌牌号
         * trScene : 1                                //场次
         * trClass : 4                                         //班级
         * trSchool : 陵水道小学                          //学校
         * trPapernum : 1                                 //题号
         * trTime : 642
         * trAddtime : 2019-05-17T13:27:02.000+0000
         * trAnswer : A                                  //答案
         * trMark : 0
         * trRight : 1                                   //是否正确
         * marksum : null
         * timesum : null
         * trAdditionalNum : null
         * trAdditionalMark : 0
         * trId : 44
         * trQuestion : null                             //题 问题
         * trType: 1                                    //题类型
         * trNum : 20
         * trRightAnswer: AB                             //正确答案
         */

        private String trUsername;
        private int trUsernum;
        private int trScene;
        private int trClass;
        private String trSchool;
        private int trPapernum;
        private int trTime;
        private String trAddtime;
        private String trAnswer;
        private int trMark;
        private int trRight;
        private int marksum;
        private int timesum;
        private double trAdditionalNum;
        private int trAdditionalMark;
        private int trId;
        private String trQuestion;
        private int trType;
        private int trNum;
        private String trRightAnswer;


        @Override
        public String toString() {
            return "TestrecordesBean{" +
                    "trUsername='" + trUsername + '\'' +
                    ", trUsernum=" + trUsernum +
                    ", trScene=" + trScene +
                    ", trClass=" + trClass +
                    ", trSchool='" + trSchool + '\'' +
                    ", trPapernum=" + trPapernum +
                    ", trTime=" + trTime +
                    ", trAddtime='" + trAddtime + '\'' +
                    ", trAnswer='" + trAnswer + '\'' +
                    ", trMark=" + trMark +
                    ", trRight=" + trRight +
                    ", marksum=" + marksum +
                    ", timesum=" + timesum +
                    ", trAdditionalNum=" + trAdditionalNum +
                    ", trAdditionalMark=" + trAdditionalMark +
                    ", trId=" + trId +
                    ", trQuestion='" + trQuestion + '\'' +
                    ", trType=" + trType +
                    ", trNum=" + trNum +
                    ", trRightAnswer='" + trRightAnswer + '\'' +
                    '}';
        }

        public String getTrUsername() {
            return trUsername;
        }

        public void setTrUsername(String trUsername) {
            this.trUsername = trUsername;
        }

        public int getTrUsernum() {
            return trUsernum;
        }

        public void setTrUsernum(int trUsernum) {
            this.trUsernum = trUsernum;
        }

        public int getTrScene() {
            return trScene;
        }

        public void setTrScene(int trScene) {
            this.trScene = trScene;
        }

        public int getTrClass() {
            return trClass;
        }

        public void setTrClass(int trClass) {
            this.trClass = trClass;
        }

        public String getTrSchool() {
            return trSchool;
        }

        public void setTrSchool(String trSchool) {
            this.trSchool = trSchool;
        }

        public int getTrPapernum() {
            return trPapernum;
        }

        public void setTrPapernum(int trPapernum) {
            this.trPapernum = trPapernum;
        }

        public int getTrTime() {
            return trTime;
        }

        public void setTrTime(int trTime) {
            this.trTime = trTime;
        }

        public String getTrAddtime() {
            return trAddtime;
        }

        public void setTrAddtime(String trAddtime) {
            this.trAddtime = trAddtime;
        }

        public String getTrAnswer() {
            return trAnswer;
        }

        public void setTrAnswer(String trAnswer) {
            this.trAnswer = trAnswer;
        }

        public int getTrMark() {
            return trMark;
        }

        public void setTrMark(int trMark) {
            this.trMark = trMark;
        }

        public int getTrRight() {
            return trRight;
        }

        public void setTrRight(int trRight) {
            this.trRight = trRight;
        }

        public int getMarksum() {
            return marksum;
        }

        public void setMarksum(int marksum) {
            this.marksum = marksum;
        }

        public int getTimesum() {
            return timesum;
        }

        public void setTimesum(int timesum) {
            this.timesum = timesum;
        }

        public double getTrAdditionalNum() {
            return trAdditionalNum;
        }

        public void setTrAdditionalNum(double trAdditionalNum) {
            this.trAdditionalNum = trAdditionalNum;
        }

        public int getTrAdditionalMark() {
            return trAdditionalMark;
        }

        public void setTrAdditionalMark(int trAdditionalMark) {
            this.trAdditionalMark = trAdditionalMark;
        }

        public int getTrId() {
            return trId;
        }

        public void setTrId(int trId) {
            this.trId = trId;
        }

        public String getTrQuestion() {
            return trQuestion;
        }

        public void setTrQuestion(String trQuestion) {
            this.trQuestion = trQuestion;
        }

        public int getTrType() {
            return trType;
        }

        public void setTrType(int trType) {
            this.trType = trType;
        }

        public int getTrNum() {
            return trNum;
        }

        public void setTrNum(int trNum) {
            this.trNum = trNum;
        }

        public String getTrRightAnswer() {
            return trRightAnswer;
        }

        public void setTrRightAnswer(String trRightAnswer) {
            this.trRightAnswer = trRightAnswer;
        }

        public TestrecordesBean() {
        }

        public TestrecordesBean(String trUsername, int trUsernum, int trScene, int trClass, String trSchool, int trPapernum, int trTime, String trAddtime, String trAnswer, int trMark, int trRight, int marksum, int timesum, double trAdditionalNum, int trAdditionalMark, int trId, String trQuestion, int trType, int trNum, String trRightAnswer) {
            this.trUsername = trUsername;
            this.trUsernum = trUsernum;
            this.trScene = trScene;
            this.trClass = trClass;
            this.trSchool = trSchool;
            this.trPapernum = trPapernum;
            this.trTime = trTime;
            this.trAddtime = trAddtime;
            this.trAnswer = trAnswer;
            this.trMark = trMark;
            this.trRight = trRight;
            this.marksum = marksum;
            this.timesum = timesum;
            this.trAdditionalNum = trAdditionalNum;
            this.trAdditionalMark = trAdditionalMark;
            this.trId = trId;
            this.trQuestion = trQuestion;
            this.trType = trType;
            this.trNum = trNum;
            this.trRightAnswer = trRightAnswer;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.trUsername);
            dest.writeInt(this.trUsernum);
            dest.writeInt(this.trScene);
            dest.writeInt(this.trClass);
            dest.writeString(this.trSchool);
            dest.writeInt(this.trPapernum);
            dest.writeInt(this.trTime);
            dest.writeString(this.trAddtime);
            dest.writeString(this.trAnswer);
            dest.writeInt(this.trMark);
            dest.writeInt(this.trRight);
            dest.writeInt(this.marksum);
            dest.writeInt(this.timesum);
            dest.writeDouble(this.trAdditionalNum);
            dest.writeInt(this.trAdditionalMark);
            dest.writeInt(this.trId);
            dest.writeString(this.trQuestion);
            dest.writeInt(this.trType);
            dest.writeInt(this.trNum);
            dest.writeString(this.trRightAnswer);
        }

        protected TestrecordesBean(Parcel in) {
            this.trUsername = in.readString();
            this.trUsernum = in.readInt();
            this.trScene = in.readInt();
            this.trClass = in.readInt();
            this.trSchool = in.readString();
            this.trPapernum = in.readInt();
            this.trTime = in.readInt();
            this.trAddtime = in.readString();
            this.trAnswer = in.readString();
            this.trMark = in.readInt();
            this.trRight = in.readInt();
            this.marksum = in.readInt();
            this.timesum = in.readInt();
            this.trAdditionalNum = in.readDouble();
            this.trAdditionalMark = in.readInt();
            this.trId = in.readInt();
            this.trQuestion = in.readString();
            this.trType = in.readInt();
            this.trNum = in.readInt();
            this.trRightAnswer = in.readString();
        }

        public static final Creator<TestrecordesBean> CREATOR = new Creator<TestrecordesBean>() {
            @Override
            public TestrecordesBean createFromParcel(Parcel source) {
                return new TestrecordesBean(source);
            }

            @Override
            public TestrecordesBean[] newArray(int size) {
                return new TestrecordesBean[size];
            }
        };
    }

    @Override
    public String toString() {
        return "UserRecoderBean{" +
                "testrecordes=" + testrecordes +
                '}';
    }

    public UserRecoderBean(List<TestrecordesBean> testrecordes) {
        this.testrecordes = testrecordes;
    }

    public UserRecoderBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.testrecordes);
    }

    protected UserRecoderBean(Parcel in) {
        this.testrecordes = in.createTypedArrayList(TestrecordesBean.CREATOR);
    }

    public static final Creator<UserRecoderBean> CREATOR = new Creator<UserRecoderBean>() {
        @Override
        public UserRecoderBean createFromParcel(Parcel source) {
            return new UserRecoderBean(source);
        }

        @Override
        public UserRecoderBean[] newArray(int size) {
            return new UserRecoderBean[size];
        }
    };
}
