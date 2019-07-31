package com.huaxia.exam.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.huaxia.exam.R;
import com.huaxia.exam.adapter.viewholder.AnswerRecordViewHolder3;
import com.huaxia.exam.bean.UserRecoderBean;

import java.util.ArrayList;

public class AnswerRecodeAdapter3 extends RecyclerView.Adapter<AnswerRecordViewHolder3> {

    private Context mContext;
    private ArrayList<UserRecoderBean.TestrecordesBean> mList = new ArrayList<>();
    private int BaseNum = 16;

    public AnswerRecodeAdapter3(Context mContext) {
        this.mContext = mContext;
    }

    public void setmList(ArrayList<UserRecoderBean.TestrecordesBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnswerRecordViewHolder3 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AnswerRecordViewHolder3(View.inflate(mContext, R.layout.answer_record_layout3, null));
    }

    // private int index = 0;

    @Override
    public void onBindViewHolder(@NonNull AnswerRecordViewHolder3 answerRecordViewHolder3, int i) {
        if (i == 0) {//第一条
            answerRecordViewHolder3.questionNum.setText("题号");
            answerRecordViewHolder3.userAnswer.setText("我的答案");
            answerRecordViewHolder3.rightAnswer.setText("正确答案");
            answerRecordViewHolder3.tvResult.setText("结果");

        } else {//非第一条

            if (mList.size() > i || mList.size() == i) {
                Log.i("hhhhh", "onBindViewHolder1: getTrPapernum" + mList.get(i - 1).getTrPapernum());
                /*if (mList.get(i - 1).getTrPapernum() == i + BaseNum) {*/
                answerRecordViewHolder3.questionNum.setText(String.valueOf(mList.get(i - 1).getTrPapernum()));
                answerRecordViewHolder3.userAnswer.setText(mList.get(i - 1).getTrAnswer());
                answerRecordViewHolder3.rightAnswer.setText(mList.get(i - 1).getTrRightAnswer());

                if (mList.get(i - 1).getTrRight() == 0) {
                    answerRecordViewHolder3.ivResult.setImageResource(R.drawable.answer_record_yes);
                } else if (mList.get(i - 1).getTrRight() == 1) {
                    answerRecordViewHolder3.ivResult.setImageResource(R.drawable.answer_record_no);
                }

                /*}*/
            }
            /* if (mList.size() > i) {
             *//* if (mList.get(i).getTrPapernum() == i + BaseNum) {*//*


                answerRecordViewHolder3.questionNum.setText(String.valueOf(mList.get(i).getTrPapernum()));
                answerRecordViewHolder3.userAnswer.setText(mList.get(i).getTrAnswer());
                answerRecordViewHolder3.rightAnswer.setText(mList.get(i).getTrRightAnswer());
                if (mList.get(i).getTrRight() == 0) {
                    answerRecordViewHolder3.ivResult.setImageResource(R.drawable.answer_record_yes);
                } else if (mList.get(i).getTrRight() == 1) {
                    answerRecordViewHolder3.ivResult.setImageResource(R.drawable.answer_record_no);
                }
            }*/
        }

    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
