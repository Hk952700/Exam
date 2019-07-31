package com.huaxia.exam.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huaxia.exam.R;

public class AnswerRecordViewHolder3 extends RecyclerView.ViewHolder {


    public final TextView questionNum;
    public final TextView userAnswer;
    public final TextView rightAnswer;
    public final TextView tvResult;
    public final ImageView ivResult;

    public AnswerRecordViewHolder3(@NonNull View itemView) {
        super(itemView);
        questionNum = (TextView) itemView.findViewById(R.id.answer_record_3_question_num1);
        userAnswer = (TextView) itemView.findViewById(R.id.answer_record_3_user_answer1);
        rightAnswer = (TextView) itemView.findViewById(R.id.answer_record_3_right_answer1);
        tvResult = (TextView) itemView.findViewById(R.id.tv_answer_record_3_result1);
        ivResult = (ImageView) itemView.findViewById(R.id.iv_answer_record_3_result1);
    }
}
