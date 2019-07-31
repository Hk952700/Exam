package com.huaxia.exam.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class UserStandingListBean implements Parcelable {


    private List<RankingListBean> rankingList;

    public List<RankingListBean> getRankingList() {
        return rankingList;
    }

    public void setRankingList(List<RankingListBean> rankingList) {
        this.rankingList = rankingList;
    }

    public static class RankingListBean {
        /**
         * trUsername : zhangsan  //姓名
         * trUsernum : 3          //桌牌号
         * trSchool : 十四中          //学校
         * marksum : 19         //总成绩
         * timesum : 6000       //总耗时
         * ranking : 1          //排名
         * promotion : 1        //是否晋级
         */

        private String trUsername;
        private int trUsernum;
        private String trSchool;
        private int marksum;
        private int timesum;
        private int ranking;
        private int promotion;

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

        public String getTrSchool() {
            return trSchool;
        }

        public void setTrSchool(String trSchool) {
            this.trSchool = trSchool;
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

        public int getRanking() {
            return ranking;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public int getPromotion() {
            return promotion;
        }

        public void setPromotion(int promotion) {
            this.promotion = promotion;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.rankingList);
    }

    public UserStandingListBean() {
    }

    protected UserStandingListBean(Parcel in) {
        this.rankingList = new ArrayList<RankingListBean>();
        in.readList(this.rankingList, RankingListBean.class.getClassLoader());
    }

    public static final Creator<UserStandingListBean> CREATOR = new Creator<UserStandingListBean>() {
        @Override
        public UserStandingListBean createFromParcel(Parcel source) {
            return new UserStandingListBean(source);
        }

        @Override
        public UserStandingListBean[] newArray(int size) {
            return new UserStandingListBean[size];
        }
    };
}
