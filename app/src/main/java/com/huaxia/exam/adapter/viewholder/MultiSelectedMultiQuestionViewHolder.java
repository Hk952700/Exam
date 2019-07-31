package com.huaxia.exam.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huaxia.exam.R;

/**
 * 2019年4月1日 09:52:20
 * jiao hao kang
 * 九选五 (九宫格)  问题  viewholder
 */
public class MultiSelectedMultiQuestionViewHolder extends RecyclerView.ViewHolder {


    public RelativeLayout mAnswer_nine_selected_five_item1_rel;
    public ImageView mAnswer_nine_selected_five_item1_imageview;//背景图片
    public TextView mAnswer_nine_selected_five_item1_textview;//内容文字

    public MultiSelectedMultiQuestionViewHolder(@NonNull View itemView) {
        super(itemView);
        mAnswer_nine_selected_five_item1_imageview = (ImageView) itemView.findViewById(R.id.answer_nine_selected_five_item1_imageview);
        mAnswer_nine_selected_five_item1_textview = (TextView) itemView.findViewById(R.id.answer_nine_selected_five_item1_textview);
        mAnswer_nine_selected_five_item1_rel = (RelativeLayout) itemView.findViewById(R.id.answer_nine_selected_five_item1_rel);
    }
}
