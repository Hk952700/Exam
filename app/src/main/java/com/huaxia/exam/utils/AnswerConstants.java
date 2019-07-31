package com.huaxia.exam.utils;

public class AnswerConstants {
    //考题类型九宫格
    public static final int ANSWER_QUESTION_TYPE_NINE_SELECTED_FIVE = 95;
    //考题类型单选题
    public static final int ANSWER_QUESTION_TYPE_SINGLE_CHOICE = 1;

    /**
     * 关闭连接
     */
    public static final int MESSAGE_TYPE_CLOSE_CONNECTION = -1;


    public static final String GET_USER_INFO = "/studentInfo/findByNumberplate";
    public static final String ADD_PATRIARCH_INFO = "/message/addMessage";
    public static final String GET_USER_STANDING_LIST = "/testRecorde/getAllRanking";
    public static final String UP_LOAD_GRADE = "/testRecorde/addTestRecorde";
    public static final String UPDATE_STATE = "/studentInfo/updatestate";
    public static final String GET_RECORDE = "/testRecorde/getTestRecordeByUsernum";
    //public static final String IP = "192.168.1.187";
    public static final String PORT = "8089";
    public static final int ANSWER_QUESTION_SUM = 20;
}
