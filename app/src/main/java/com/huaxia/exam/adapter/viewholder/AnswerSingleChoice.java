package com.huaxia.exam.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huaxia.exam.R;

/**
 * 2019年3月26日 17:11:22
 * jiao hao kang
 * 单选题
 */
public class AnswerSingleChoice extends RecyclerView.ViewHolder {

    //public final TextView mSingle_choice_option_text;
    public final TextView mSingle_choice_context_text;
    public final ImageView mSingle_choice_image;
    public final RelativeLayout mRlv_single_item_option_context;

    public AnswerSingleChoice(@NonNull View itemView) {
        super(itemView);
        mRlv_single_item_option_context = (RelativeLayout) itemView.findViewById(R.id.rlv_single_item_option_context);
        //mSingle_choice_option_text = (TextView) itemView.findViewById(R.id.single_choice_option_text);
        mSingle_choice_context_text = (TextView) itemView.findViewById(R.id.single_choice_context_text);
        mSingle_choice_image = (ImageView) itemView.findViewById(R.id.single_item_option_image);
    }

}
