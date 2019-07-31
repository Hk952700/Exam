package com.huaxia.exam.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.huaxia.exam.R;
import com.huaxia.exam.adapter.viewholder.MultiSelectedMultiOptionViewHolder;
import com.huaxia.exam.bean.AnswerNineSelectedFiveOptionBean;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 2019年4月15日 10:34:43
 * jiao hao kang
 * 十二选七(十二宫格)/九选五(九宫格) 选项的adapter
 */

public class MultiSelectedMultiOptionRecyclerViewAdapter extends RecyclerView.Adapter<MultiSelectedMultiOptionViewHolder> {

    private Context mContext;
    private ArrayList<AnswerNineSelectedFiveOptionBean> mList = new ArrayList<>();
    private int mType = 0;

    public MultiSelectedMultiOptionRecyclerViewAdapter(Context mContext, int type) {
        this.mContext = mContext;
        this.mType = type;
    }

    public void setmList(ArrayList<AnswerNineSelectedFiveOptionBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }


    private int selectedItemCount = 0;

    public int getSelectedItemCount() {
        return selectedItemCount;
    }

    public void setSelectedItemCount(int selectedItemCount) {
        this.selectedItemCount = selectedItemCount;
    }

    @NonNull
    @Override
    public MultiSelectedMultiOptionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(mContext, R.layout.answer_nine_selected_five_item_layout2, null);
        return new MultiSelectedMultiOptionViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiSelectedMultiOptionViewHolder answerNineSelectedFiveOptionViewHolder, final int i) {

        if (mList.get(i).isChecked()) {
            Log.i("jtest1", "onBindViewHolder: " + mList.get(i).isChecked());
            answerNineSelectedFiveOptionViewHolder.itemView.setAlpha((float) 0.5);
           /* answerNineSelectedFiveOptionViewHolder.mAnswer_nine_selected_five_item2_textview.setAlpha(1);
            answerNineSelectedFiveOptionViewHolder.mAnswer_nine_selected_five_item2_rel.setAlpha(1);*/
        } else {
            Log.i("jtest1", "onBindViewHolder: " + mList.get(i).isChecked());
            answerNineSelectedFiveOptionViewHolder.itemView.setAlpha(1);
           /* answerNineSelectedFiveOptionViewHolder.mAnswer_nine_selected_five_item2_textview.setAlpha(255);
            answerNineSelectedFiveOptionViewHolder.mAnswer_nine_selected_five_item2_rel.setAlpha(255);*/
        }
        //设置选项的显示背景图片的透明度
        answerNineSelectedFiveOptionViewHolder.mAnswer_nine_selected_five_item2_textview.setText(mList.get(i).getValue());


        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) answerNineSelectedFiveOptionViewHolder.mAnswer_nine_selected_five_item2_rel.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) new RelativeLayout.LayoutParams(layoutParams1.width, layoutParams1.height);


        if (mType == 5) {
            switch ((i + 1)) {
                case 1:
                    layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.dp_42);
                    layoutParams.rightMargin = 254;
                    layoutParams.leftMargin = 0;
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    break;
                case 2:
                    layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.dp_42);
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    layoutParams.leftMargin = 0;
                    break;
                case 3:
                    layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.dp_42);
                    layoutParams.rightMargin = 0;
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    layoutParams.leftMargin = 0;
                    break;
                case 4:
                    layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.dp_42);
                    layoutParams.rightMargin = 254;
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    layoutParams.leftMargin = 0;
                    break;
                case 5:
                    layoutParams.leftMargin = 0;
                    layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.dp_42);
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    layoutParams.rightMargin = 254;
                    layoutParams.leftMargin = 0;
                    break;
                case 6:
                    layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.dp_42);
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                    layoutParams.rightMargin = 0;
                    layoutParams.leftMargin = 0;
                    break;
                case 7:
                    layoutParams.rightMargin = 254;
                    layoutParams.bottomMargin = 0;
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    layoutParams.leftMargin = 0;
                    break;
                case 8:
                    layoutParams.leftMargin = 0;
                    layoutParams.rightMargin = 254;
                    layoutParams.bottomMargin = 0;
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

                    break;
                case 9:
                    layoutParams.leftMargin = 0;
                    layoutParams.bottomMargin = 0;
                    layoutParams.rightMargin = 0;
                    layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                    break;
                default:
                    break;

            }
            answerNineSelectedFiveOptionViewHolder.mAnswer_nine_selected_five_item2_rel.setLayoutParams(layoutParams);
        } else if (mType == 7) {
            switch ((i + 1)) {
                case 1:
                    layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.dp_43);
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    break;
                case 2:
                    layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.dp_43);
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    break;
                case 3:
                    layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.dp_43);
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    break;
                case 4:
                    layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.dp_43);
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    break;
                case 5:
                    layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.dp_43);
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    break;
                case 6:
                    layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.dp_43);
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    break;
                case 7:
                    layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.dp_43);
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    break;
                case 8:
                    layoutParams.bottomMargin = (int) mContext.getResources().getDimension(R.dimen.dp_43);
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    break;
                case 9:
                    layoutParams.bottomMargin = 0;
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    break;
                case 10:
                    layoutParams.bottomMargin = 0;
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    break;
                case 11:
                    layoutParams.bottomMargin = 0;
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    break;
                case 12:
                    layoutParams.bottomMargin = 0;
                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    break;
                default:
                    break;
            }
            answerNineSelectedFiveOptionViewHolder.mAnswer_nine_selected_five_item2_rel.setLayoutParams(layoutParams);


        }

        answerNineSelectedFiveOptionViewHolder.mAnswer_nine_selected_five_item2_rel.setOnClickListener(new View.OnClickListener() {//选项的图片的点击事件
            @Override
            public void onClick(View v) {
                //判断选中的数量不能超过  预设值
                if (selectedItemCount < mType) {
                    //未选中状态 进行回调传值
                    if (!mList.get(i).isChecked()) {
                        mList.get(i).setChecked(true);
                        notifyItemChanged(i);
                        onOptionItemselected.optionItemselected(mList.get(i));
                        notifyDataSetChanged();
                    }
                }

            }
        });



    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    private onOptionItemselected onOptionItemselected;

    public void setOnOptionItemselected(onOptionItemselected onOptionItemselected) {
        this.onOptionItemselected = onOptionItemselected;
    }

    //接口回调 当选项被选中的时候进行回调到主类
    public interface onOptionItemselected {
        /**
         * @param selectedItem 选中的对象
         */
        void optionItemselected(AnswerNineSelectedFiveOptionBean selectedItem);
    }


}