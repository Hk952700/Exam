package com.huaxia.exam.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.huaxia.exam.R;
import com.huaxia.exam.adapter.viewholder.MultiSelectedMultiQuestionViewHolder;
import com.huaxia.exam.bean.AnswerNineSelectedFiveOptionBean;

import java.util.ArrayList;

/**
 * 2019年4月15日 10:34:43
 * jiao hao kang
 * 十二选七(十二宫格)/九选五(九宫格) 答案的adapter
 */
public class MultiSelectedMultiQuestionRecyclerViewAdapter extends RecyclerView.Adapter<MultiSelectedMultiQuestionViewHolder> {
    private Context mContext;
    private ArrayList<AnswerNineSelectedFiveOptionBean> mList = new ArrayList<>();
    private int mCount;

    public MultiSelectedMultiQuestionRecyclerViewAdapter(Context mContext, int count) {
        this.mContext = mContext;
        this.mCount = count;
    }


    public void setmList(ArrayList<AnswerNineSelectedFiveOptionBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }


    @Override
    public MultiSelectedMultiQuestionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View inflate = View.inflate(mContext, R.layout.answer_nine_selected_five_item_layout1, null);
        return new MultiSelectedMultiQuestionViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiSelectedMultiQuestionViewHolder answerNineSelectedFiveQuestion, int i) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_rel.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(layoutParams.width, layoutParams.height);
        if (mCount == 5) {
            switch ((i + 1)) {
                case 1:
                    layoutParams1.leftMargin = 0;
                    layoutParams1.rightMargin = (int) mContext.getResources().getDimension(R.dimen.dp_73);
                    answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_rel.setLayoutParams(layoutParams1);
                    break;
                case 2:
                    layoutParams1.leftMargin = 0;
                    layoutParams1.rightMargin = (int) mContext.getResources().getDimension(R.dimen.dp_73);
                    answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_rel.setLayoutParams(layoutParams1);
                    break;
                case 3:
                    layoutParams1.leftMargin = 0;
                    layoutParams1.rightMargin = (int) mContext.getResources().getDimension(R.dimen.dp_73);
                    answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_rel.setLayoutParams(layoutParams1);
                    break;
                case 4:
                    layoutParams1.leftMargin = 0;
                    layoutParams1.rightMargin = (int) mContext.getResources().getDimension(R.dimen.dp_73);
                    answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_rel.setLayoutParams(layoutParams1);
                    break;
                case 5:
                    layoutParams1.rightMargin = 0;
                    answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_rel.setLayoutParams(layoutParams1);
                    break;
            }
        } else if (mCount == 7) {
            switch ((i + 1)) {
                case 1:
                    layoutParams1.leftMargin = 0;
                    layoutParams1.rightMargin = (int) mContext.getResources().getDimension(R.dimen.dp_33);
                    answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_rel.setLayoutParams(layoutParams1);
                    break;
                case 2:
                    layoutParams1.leftMargin = 0;
                    layoutParams1.rightMargin = (int) mContext.getResources().getDimension(R.dimen.dp_33);
                    answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_rel.setLayoutParams(layoutParams1);
                    break;
                case 3:
                    layoutParams1.leftMargin = 0;
                    layoutParams1.rightMargin = (int) mContext.getResources().getDimension(R.dimen.dp_33);
                    answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_rel.setLayoutParams(layoutParams1);
                    break;
                case 4:
                    layoutParams1.leftMargin = 0;
                    layoutParams1.rightMargin = (int) mContext.getResources().getDimension(R.dimen.dp_33);
                    answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_rel.setLayoutParams(layoutParams1);
                    break;
                case 5:
                    layoutParams1.leftMargin = 0;
                    layoutParams1.rightMargin = (int) mContext.getResources().getDimension(R.dimen.dp_33);
                    answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_rel.setLayoutParams(layoutParams1);
                    break;
                case 6:
                    layoutParams1.leftMargin = 0;
                    layoutParams1.rightMargin = (int) mContext.getResources().getDimension(R.dimen.dp_33);
                    answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_rel.setLayoutParams(layoutParams1);
                    break;
                case 7:
                    layoutParams1.rightMargin = 0;
                    answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_rel.setLayoutParams(layoutParams1);
                    break;
            }
        }


        if (mList.size() >= i + 1) {
            answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_textview.setText(mList.get(i).getValue());
        } else {
            answerNineSelectedFiveQuestion.mAnswer_nine_selected_five_item1_textview.setText("");
        }

    }

    @Override
    public int getItemCount() {
        return mCount;
    }
}
