package com.huaxia.exam.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huaxia.exam.R;

/**
 * 2019年4月1日 10:33:34
 * jiao hao kang
 * 九选五(九宫格)  选项viewholder
 */
public class MultiSelectedMultiOptionViewHolder extends RecyclerView.ViewHolder {

    public final ImageView mAnswer_nine_selected_five_item2_imageview;//选项 背景图片
    public final TextView mAnswer_nine_selected_five_item2_textview;//选项 内容文字
    public final RelativeLayout mAnswer_nine_selected_five_item2_rel;

    public MultiSelectedMultiOptionViewHolder(@NonNull View itemView) {
        super(itemView);
        mAnswer_nine_selected_five_item2_imageview = (ImageView) itemView.findViewById(R.id.answer_nine_selected_five_item2_imageview);
        mAnswer_nine_selected_five_item2_textview = (TextView) itemView.findViewById(R.id.answer_nine_selected_five_item2_textview);
        mAnswer_nine_selected_five_item2_rel = (RelativeLayout) itemView.findViewById(R.id.answer_nine_selected_five_item2_rel);

    }
}
