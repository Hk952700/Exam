package com.huaxia.exam.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AnswerResultDataBean implements Parcelable {


    /**
     * tp_id : 1
     * tp_subject : “班门弄斧”中的“班”指的是谁？
     * tp_options : A班固/B鲁班/C班超/D阙班
     * tp_type : 1
     * tp_answer : B
     * tp_difficulty : 1
     * tp_score : 5
     * tp_class : 3
     * tp_senum : 1
     * tp_num : 1
     * tp_uptime : 1555372800000
     */

    private int tp_id;
    private String tp_subject;
    private String tp_options;
    private int tp_type;
    private String tp_answer;
    private int tp_difficulty;
    private int tp_score;
    private int tp_class;
    private int tp_senum;
    private int tp_num;
    private long tp_uptime;

    public int getTp_id() {
        return tp_id;
    }

    public void setTp_id(int tp_id) {
        this.tp_id = tp_id;
    }

    public String getTp_subject() {
        return tp_subject;
    }

    public void setTp_subject(String tp_subject) {
        this.tp_subject = tp_subject;
    }

    public String getTp_options() {
        return tp_options;
    }

    public void setTp_options(String tp_options) {
        this.tp_options = tp_options;
    }

    public int getTp_type() {
        return tp_type;
    }

    public void setTp_type(int tp_type) {
        this.tp_type = tp_type;
    }

    public String getTp_answer() {
        return tp_answer;
    }

    public void setTp_answer(String tp_answer) {
        this.tp_answer = tp_answer;
    }

    public int getTp_difficulty() {
        return tp_difficulty;
    }

    public void setTp_difficulty(int tp_difficulty) {
        this.tp_difficulty = tp_difficulty;
    }

    public int getTp_score() {
        return tp_score;
    }

    public void setTp_score(int tp_score) {
        this.tp_score = tp_score;
    }

    public int getTp_class() {
        return tp_class;
    }

    public void setTp_class(int tp_class) {
        this.tp_class = tp_class;
    }

    public int getTp_senum() {
        return tp_senum;
    }

    public void setTp_senum(int tp_senum) {
        this.tp_senum = tp_senum;
    }

    public int getTp_num() {
        return tp_num;
    }

    public void setTp_num(int tp_num) {
        this.tp_num = tp_num;
    }

    public long getTp_uptime() {
        return tp_uptime;
    }

    public void setTp_uptime(long tp_uptime) {
        this.tp_uptime = tp_uptime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.tp_id);
        dest.writeString(this.tp_subject);
        dest.writeString(this.tp_options);
        dest.writeInt(this.tp_type);
        dest.writeString(this.tp_answer);
        dest.writeInt(this.tp_difficulty);
        dest.writeInt(this.tp_score);
        dest.writeInt(this.tp_class);
        dest.writeInt(this.tp_senum);
        dest.writeInt(this.tp_num);
        dest.writeLong(this.tp_uptime);
    }

    public AnswerResultDataBean() {
    }

    protected AnswerResultDataBean(Parcel in) {
        this.tp_id = in.readInt();
        this.tp_subject = in.readString();
        this.tp_options = in.readString();
        this.tp_type = in.readInt();
        this.tp_answer = in.readString();
        this.tp_difficulty = in.readInt();
        this.tp_score = in.readInt();
        this.tp_class = in.readInt();
        this.tp_senum = in.readInt();
        this.tp_num = in.readInt();
        this.tp_uptime = in.readLong();
    }

    public static final Creator<AnswerResultDataBean> CREATOR = new Creator<AnswerResultDataBean>() {
        @Override
        public AnswerResultDataBean createFromParcel(Parcel source) {
            return new AnswerResultDataBean(source);
        }

        @Override
        public AnswerResultDataBean[] newArray(int size) {
            return new AnswerResultDataBean[size];
        }
    };
}
