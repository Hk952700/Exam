package com.huaxia.exam.activity.answer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huaxia.exam.R;
import com.huaxia.exam.adapter.MultiSelectedMultiOptionRecyclerViewAdapter;
import com.huaxia.exam.adapter.MultiSelectedMultiQuestionRecyclerViewAdapter;
import com.huaxia.exam.base.BaseActivity;
import com.huaxia.exam.bean.AnswerNineSelectedFiveOptionBean;
import com.huaxia.exam.bean.AnswerResultDataBean;
import com.huaxia.exam.bean.UploadGradeDataBean;
import com.huaxia.exam.utils.SharedPreUtils;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.huaxia.exam.utils.AnswerConstants.ANSWER_QUESTION_SUM;

public class TwelveSelectedSevenActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<AnswerNineSelectedFiveOptionBean> bufferArrayList = new ArrayList<>();
    private ArrayList<AnswerNineSelectedFiveOptionBean> optionArrayList = new ArrayList<>();
    private TextView mCountDownText;
    private RecyclerView mQuestionRecyclerview;
    private RecyclerView mOptionRecyclerview;
    private ImageView mQuestionRemoveImageview;


    private MultiSelectedMultiOptionRecyclerViewAdapter mOptionRecyclerViewAdapter;
    private MultiSelectedMultiQuestionRecyclerViewAdapter mQuestionRecyclerViewAdapter;
    private Button mConfirmButton;
    private String mOptions1;
    private String mAnswer;


    private AnswerResultDataBean answer;

    private long date;
    private long submitTime;

    private RelativeLayout mWebsocket_status;
    private SimpleDraweeView mTitle2Back;
    private AlertDialog alertDialog;
    private LayoutInflater factory;
    private View view;
    private TextView mTvUsername;
    private TextView mTvNumberplate;
    private TextView mTvAnswerNum;
    private int tp_senum;


    @Override
    public int setContentView() {
        return R.layout.activity_twelve_selected_seven;
    }

    @Override
    public Context setContext() {
        return TwelveSelectedSevenActivity.this;
    }


    @Override
    public void init() {
        //右上角WebSocket状态方框
        mWebsocket_status = (RelativeLayout) findViewById(R.id.twelve_selected_seven_websocket_status);


        //初始化控件

        //倒计时(textview)
        mCountDownText = (TextView) findViewById(R.id.twelve_selected_seven_count_down);
        //选手选中的答案的recyclerview
        mQuestionRecyclerview = (RecyclerView) findViewById(R.id.twelve_selected_seven_question_recyclerview);
        //选中的答案的删除按钮(imageview)
        mQuestionRemoveImageview = (ImageView) findViewById(R.id.twelve_selected_seven_question_remove_imageview);
        //选项的recyclerview
        mOptionRecyclerview = (RecyclerView) findViewById(R.id.twelve_selected_seven_option_recyclerview);


        mTvUsername = (TextView) findViewById(R.id.twelve_selected_seven_username);
        mTvNumberplate = (TextView) findViewById(R.id.twelve_selected_seven_numberplate);
        mTvAnswerNum = (TextView) findViewById(R.id.twelve_selected_seven_answer_num);


        mQuestionRemoveImageview.setOnClickListener(this);

        mConfirmButton = (Button) findViewById(R.id.twelve_selected_seven_confirm_button);//确认图片

        initData();

    }


    private void initData() {
        Intent intent01 = getIntent();
        answer = (AnswerResultDataBean) intent01.getParcelableExtra("answer");

        if (answer != null) {
            Log.i("jiao", "initData: " + "====" + answer.getTp_senum());
            tp_senum = answer.getTp_senum();
            mTvUsername.setText(SharedPreUtils.getString(this, "user_name"));
            mTvAnswerNum.setText(SharedPreUtils.getString(this, "user_numberplate") + "号");
            mTvNumberplate.setText(answer.getTp_senum() + "/" + ANSWER_QUESTION_SUM);

            mOptions1 = answer.getTp_subject();
            mAnswer = answer.getTp_answer();

            mOptions1 = answer.getTp_subject();
            Log.i("jtest", "initData: mOptions为:" + mOptions1);
            Log.i("jtest", "initData: mAnswer为:" + mAnswer);
        }

        if (!TextUtils.isEmpty(mOptions1)) {
            char[] chars = mOptions1.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                AnswerNineSelectedFiveOptionBean answerNineSelectedFiveOptionBean = new AnswerNineSelectedFiveOptionBean();
                answerNineSelectedFiveOptionBean.setValue(String.valueOf(chars[i]));
                answerNineSelectedFiveOptionBean.setIndex(i);
                optionArrayList.add(answerNineSelectedFiveOptionBean);
            }


        }
        initRecyclerView(optionArrayList, bufferArrayList);
        startCountDown(mCountDownText, 30);
        mConfirmButton.setOnClickListener(this);
    }


    private void initRecyclerView(final ArrayList<AnswerNineSelectedFiveOptionBean> optionArrayList, final ArrayList<AnswerNineSelectedFiveOptionBean> bufferArrayList) {

        mOptionRecyclerViewAdapter = new MultiSelectedMultiOptionRecyclerViewAdapter(TwelveSelectedSevenActivity.this, 7);
        mOptionRecyclerview.setLayoutManager(new GridLayoutManager(TwelveSelectedSevenActivity.this, 4));

        mQuestionRecyclerview.setLayoutManager(new LinearLayoutManager(TwelveSelectedSevenActivity.this, LinearLayoutManager.HORIZONTAL, false));
        mQuestionRecyclerViewAdapter = new MultiSelectedMultiQuestionRecyclerViewAdapter(TwelveSelectedSevenActivity.this, 7);
        mQuestionRecyclerview.setAdapter(mQuestionRecyclerViewAdapter);

        mOptionRecyclerview.setAdapter(mOptionRecyclerViewAdapter);
        mOptionRecyclerViewAdapter.setmList(optionArrayList);

        //选中回调
        mOptionRecyclerViewAdapter.setOnOptionItemselected(new MultiSelectedMultiOptionRecyclerViewAdapter.onOptionItemselected() {

            @Override
            public void optionItemselected(AnswerNineSelectedFiveOptionBean selectedItem) {
                bufferArrayList.add(selectedItem);
                mQuestionRecyclerViewAdapter.setmList(bufferArrayList);
                mOptionRecyclerViewAdapter.setSelectedItemCount(mOptionRecyclerViewAdapter.getSelectedItemCount() + 1);
            }
        });

    }


    @Override
    public void onCountDownFinish(long date, long submitTime) {
        super.onCountDownFinish(date, submitTime);
        this.submitTime = submitTime;
        this.date = date;
        UploadGradeDataBean uploadGradeDataBean = new UploadGradeDataBean();
        if (bufferArrayList.size() != 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < bufferArrayList.size(); i++) {
                stringBuffer.append(bufferArrayList.get(i).getValue());
            }
            String s = stringBuffer.toString();
            Log.i("jtest", "onCountDownFinish: 十二宫格学生答案為:" + s);
            Log.i("jtest", "onCountDownFinish: 正確答案答案為:" + mAnswer);
            if (s.equals(mAnswer)) {
                showDiaLog(0, mAnswer, s);
                //对错
                uploadGradeDataBean.setTrRight("0");

                //分数
                uploadGradeDataBean.setTrMark(answer.getTp_score() + "");
            } else {
                showDiaLog(1, mAnswer, s);
                //对错
                uploadGradeDataBean.setTrRight("1");
                //分数
                uploadGradeDataBean.setTrMark("0");
            }
            Log.i("jtest", "onCountDownFinish: 时间:" + date);

            //学生答案
            uploadGradeDataBean.setTrAnswer(s);
        } else {
            showDiaLog(1, mAnswer, "未作答");
            //学生答案
            uploadGradeDataBean.setTrAnswer("未作答");
            //分数
            uploadGradeDataBean.setTrMark("0");
            //对错
            uploadGradeDataBean.setTrRight("1");
        }


        if (answer != null) {
            uploadGradeDataBean.setTrQuestion(answer.getTp_subject());
            //班级
            uploadGradeDataBean.setTrClass(answer.getTp_class() + "");
            //耗时
            uploadGradeDataBean.setTrTime(date + "");
            //题号
            uploadGradeDataBean.setTrPapernum(answer.getTp_senum() + "");
            uploadGradeDataBean.setTrRightAnswer(mAnswer);
            uploadGradeDataBean.setTrType(answer.getTp_type() + "");
            UploadGrade(uploadGradeDataBean);


        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.twelve_selected_seven_question_remove_imageview://点击删除按钮(imageview)

                if (mOptionRecyclerViewAdapter.getSelectedItemCount() > 0 && bufferArrayList.size() > 0) {
                    for (int i = 0; i < optionArrayList.size(); i++) {
                        if (bufferArrayList.get(bufferArrayList.size() - 1).getIndex() == optionArrayList.get(i).getIndex()) {
                            optionArrayList.get(i).setChecked(false);
                            //下面选项的
                            mOptionRecyclerViewAdapter.setmList(optionArrayList);
                            mOptionRecyclerViewAdapter.setSelectedItemCount(mOptionRecyclerViewAdapter.getSelectedItemCount() - 1);
                            //循环遍历 移除  刷新两个适配器
                            bufferArrayList.remove(bufferArrayList.size() - 1);
                            //上面填空的
                            mQuestionRecyclerViewAdapter.setmList(bufferArrayList);
                            break;
                        }
                    }


                }

                break;
            //点击确认按钮(textview)
            case R.id.twelve_selected_seven_confirm_button:
                if (bufferArrayList.size() != 0) {
                    confirm();
                } else {
                    Toast.makeText(this, "请选择答案!", Toast.LENGTH_SHORT).show();
                }
                break;

         /*   case R.id.twelve_selected_seven_to_result_button:
                Log.i("jtest", "十二选七:onClick: " + "点击提交");

                //toResult(date, System.currentTimeMillis() - submitTime);
                getAnswerRecord();
                break;*/
            default:
                break;
        }
    }

    @Override
    public void websocketStatusChange(int color) {
        Log.i("jiao", "12nwebsocketStatusChange: 前=" + color);
        if (mWebsocket_status != null) {
            mWebsocket_status.setBackgroundResource(color);
            Log.i("jiao", "12nwebsocketStatusChange: 后=" + color);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (alertDialog != null) {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
            alertDialog = null;
        }
    }

    private void showDiaLog(int type, String trueAnwer, String UserAnswer) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(this, R.style.Translucent_NoTitle).create();
            alertDialog.setCancelable(false);
        }
        alertDialog.show();
        //获取对话框当前的参数值
        android.view.WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();
        //高度自适应
        p.height = (int) (WRAP_CONTENT);
        //宽度自适应
        p.width = (int) (WRAP_CONTENT);
        alertDialog.getWindow().setAttributes(p);

        TextView rightAnswer = null;
        TextView uswerAnswer = null;
        Button btnGetRecord = null;
        factory = LayoutInflater.from(this);
        switch (type) {
            case 0:

                view = factory.inflate(R.layout.alertdialog_amswer_results_yes, null);


                alertDialog.setContentView(view);
                rightAnswer = view.findViewById(R.id.dia_textview1_yes);
                uswerAnswer = view.findViewById(R.id.dia_textview2_yes);
                btnGetRecord = view.findViewById(R.id.amswer_results_yes_get_record);
                if (answer.getTp_senum() == ANSWER_QUESTION_SUM) {
                    //最后一道题显示最后跳转到获取成绩列表的按钮
                    btnGetRecord.setVisibility(View.VISIBLE);
                } else {
                    btnGetRecord.setVisibility(View.GONE);
                }
                btnGetRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getAnswerRecord();
                    }
                });
                rightAnswer.setText(trueAnwer);
                uswerAnswer.setText(UserAnswer);
                break;
            case 1:

                view = factory.inflate(R.layout.alertdialog_amswer_results_no, null);

                alertDialog.setContentView(view);
                rightAnswer = view.findViewById(R.id.dia_textview1_no);
                uswerAnswer = view.findViewById(R.id.dia_textview2_no);
                btnGetRecord = view.findViewById(R.id.amswer_results_no_get_record);
                if (answer.getTp_senum() == ANSWER_QUESTION_SUM) {
                    //最后一道题显示最后跳转到获取成绩列表的按钮
                    btnGetRecord.setVisibility(View.VISIBLE);
                } else {
                    btnGetRecord.setVisibility(View.GONE);
                }
                btnGetRecord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getAnswerRecord();
                    }
                });
                rightAnswer.setText(trueAnwer);
                uswerAnswer.setText(UserAnswer);
                break;

            default:
                break;
        }


    }
}
