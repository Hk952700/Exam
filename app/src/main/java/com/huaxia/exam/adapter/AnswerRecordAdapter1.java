package com.huaxia.exam.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.huaxia.exam.R;
import com.huaxia.exam.adapter.viewholder.AnswerRecordViewHolder1;
import com.huaxia.exam.bean.UserRecoderBean;

import java.util.ArrayList;

public class AnswerRecordAdapter1 extends RecyclerView.Adapter<AnswerRecordViewHolder1> {
    private Context mContext;
    private ArrayList<UserRecoderBean.TestrecordesBean> mList = new ArrayList<>();
    private int BaseNum = 0;

    public AnswerRecordAdapter1(Context mContext) {
        this.mContext = mContext;
    }

    public void setmList(ArrayList<UserRecoderBean.TestrecordesBean> mList) {
        this.mList = mList;
        Log.i("hhhhh", "setmList1: mlst" + mList.toString());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnswerRecordViewHolder1 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AnswerRecordViewHolder1(View.inflate(mContext, R.layout.answer_record_layout1, null));
    }

    //private int index = 0;

    @Override
    public void onBindViewHolder(@NonNull AnswerRecordViewHolder1 answerRecordViewHolder1, int i) {
        // int j = index;

        if (i == 0) {//第一条
            answerRecordViewHolder1.questionNum.setText("题号");
            answerRecordViewHolder1.userAnswer.setText("我的答案");
            answerRecordViewHolder1.rightAnswer.setText("正确答案");
            answerRecordViewHolder1.tvResult.setText("结果");

        } else {//非第一条
            if (mList.size() > i||mList.size() == i) {
                Log.i("hhhhh", "onBindViewHolder1: getTrPapernum" + mList.get(i - 1).getTrPapernum());
                /*if (mList.get(i - 1).getTrPapernum() == i + BaseNum) {*/
                    answerRecordViewHolder1.questionNum.setText(String.valueOf(mList.get(i - 1).getTrPapernum()));
                    answerRecordViewHolder1.userAnswer.setText(mList.get(i - 1).getTrAnswer());
                    answerRecordViewHolder1.rightAnswer.setText(mList.get(i - 1).getTrRightAnswer());

                    if (mList.get(i - 1).getTrRight() == 0) {
                        answerRecordViewHolder1.ivResult.setImageResource(R.drawable.answer_record_yes);
                    } else if (mList.get(i - 1).getTrRight() == 1) {
                        answerRecordViewHolder1.ivResult.setImageResource(R.drawable.answer_record_no);
                    }

                /*}*/
            }


        }

    }


    @Override
    public int getItemCount() {
        return 9;
    }
}
