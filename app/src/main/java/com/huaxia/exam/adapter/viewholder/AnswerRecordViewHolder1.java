package com.huaxia.exam.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huaxia.exam.R;

public class AnswerRecordViewHolder1 extends RecyclerView.ViewHolder {


    public final TextView questionNum;
    public final TextView userAnswer;
    public final TextView rightAnswer;
    public final TextView tvResult;
    public final ImageView ivResult;

    public AnswerRecordViewHolder1(@NonNull View itemView) {
        super(itemView);
        questionNum = (TextView) itemView.findViewById(R.id.answer_record_1_question_num);
        userAnswer = (TextView) itemView.findViewById(R.id.answer_record_1_user_answer);
        rightAnswer = (TextView) itemView.findViewById(R.id.answer_record_1_right_answer);
        tvResult = (TextView) itemView.findViewById(R.id.tv_answer_record_1_result);
        ivResult = (ImageView) itemView.findViewById(R.id.iv_answer_record_1_result);
    }


}
