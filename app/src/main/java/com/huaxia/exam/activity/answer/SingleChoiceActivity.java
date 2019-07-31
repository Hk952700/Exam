package com.huaxia.exam.activity.answer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huaxia.exam.R;
import com.huaxia.exam.adapter.SingleChoiceRecyclerViewAdapter;
import com.huaxia.exam.base.BaseActivity;
import com.huaxia.exam.bean.AnswerResultDataBean;
import com.huaxia.exam.bean.SingleChoiceItemBean;
import com.huaxia.exam.bean.UploadGradeDataBean;
import com.huaxia.exam.utils.SharedPreUtils;

import java.util.ArrayList;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.huaxia.exam.utils.AnswerConstants.ANSWER_QUESTION_SUM;

/**
 * 2019年4月12日 10:22:14
 * jiao  hao kang
 * 单选题 activity
 */
public class SingleChoiceActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mOptionRecyclerview;
    private TextView mQuestion;
    private TextView mCountDownText;
    private Button mConfirmButton;
    private SingleChoiceItemBean mUserAnswer;

    private AnswerResultDataBean data;

    private long date;
    private long submitTime;
    private RelativeLayout mWebsocket_status;
    private AlertDialog alertDialog;
    private LayoutInflater factory;
    private View view;
    private String[] split;
    private TextView mTvUsername;
    private TextView mTvNumberplate;
    private TextView mTvAnswerNum;

    @Override
    public int setContentView() {
        return R.layout.activity_single_choice;
    }

    @Override
    public Context setContext() {
        return SingleChoiceActivity.this;
    }


    @Override
    public void init() {
        //右上角WebSocket状态方框
        mWebsocket_status = (RelativeLayout) findViewById(R.id.single_choice_websocket_status);

        mCountDownText = (TextView) findViewById(R.id.single_choice_count_down);
        mQuestion = (TextView) findViewById(R.id.single_choice_question);
        mOptionRecyclerview = (RecyclerView) findViewById(R.id.single_choice_option_recyclerview);

        //确认图片
        mConfirmButton = (Button) findViewById(R.id.single_choice_confirm_button);

        mTvUsername = (TextView) findViewById(R.id.single_choice_username);
        mTvNumberplate = (TextView) findViewById(R.id.single_choice_numberplate);
        mTvAnswerNum = (TextView) findViewById(R.id.single_choice_answer_num);

        initDataAndRecycler();


    }


    private void initDataAndRecycler() {
        Intent intent = getIntent();
        data = (AnswerResultDataBean) intent.getParcelableExtra("answer");

        Log.i("jtest", "initDataAndRecycler: " + data);
        if (data != null) {


            mTvUsername.setText(SharedPreUtils.getString(this, "user_name"));
            mTvAnswerNum.setText(data.getTp_senum() + "/" + ANSWER_QUESTION_SUM);
            mTvNumberplate.setText(SharedPreUtils.getString(this, "user_numberplate") + "号");


            if (data.getTp_subject().length() <= 13) {
                mQuestion.setTextSize(this.getResources().getDimension(R.dimen.sp_36));
            } else if (data.getTp_subject().length() <= 18 && data.getTp_subject().length() > 13) {
                mQuestion.setTextSize(this.getResources().getDimension(R.dimen.sp_28));
            } else {
                mQuestion.setTextSize(this.getResources().getDimension(R.dimen.sp_18));
            }


            mQuestion.setText(data.getTp_subject().trim());

            ArrayList<SingleChoiceItemBean> singleChoiceItemBeans = new ArrayList<>();
            split = data.getTp_options().split("/");
            for (int i = 0; i < split.length; i++) {
                char[] chars = split[i].toCharArray();
                StringBuffer stringBuffer = new StringBuffer();
                for (int j = 0; j < chars.length; j++) {
                    if (j > 0) {
                        stringBuffer.append(chars[j]);
                    }
                }

                singleChoiceItemBeans.add(new SingleChoiceItemBean(String.valueOf(chars[0]), stringBuffer.toString(), false, false));
            }
            singleChoice(singleChoiceItemBeans);
        }

        startCountDown(mCountDownText, 20);
        mConfirmButton.setOnClickListener(this);
    }


    @Override
    public void onCountDownFinish(long date, long submitTime) {
        super.onCountDownFinish(date, submitTime);
        this.date = date;
        this.submitTime = submitTime;
        UploadGradeDataBean uploadGradeDataBean = new UploadGradeDataBean();


        if (mUserAnswer != null) {


            if (data.getTp_answer().equals(mUserAnswer.getOption())) {
                uploadGradeDataBean.setTrRight("0");//对错

                Log.i("jtest", "onCountDownFinish:分数=" + data.getTp_score());
                //分数
                uploadGradeDataBean.setTrMark(data.getTp_score() + "");
                StringBuffer stringBuffer = new StringBuffer();
                if (data != null) {

                    String tp_answer = data.getTp_answer();


                    for (int i = 0; i < split.length; i++) {
                        char[] chars = split[i].toCharArray();
                        if (String.valueOf(chars[0]).trim().equals(tp_answer.trim())) {
                            stringBuffer.append(split[i].substring(1, split[i].length()).trim());
                        }
                    }
                }
                showDiaLog(0, stringBuffer.toString(), mUserAnswer.getContext());
            } else {

                StringBuffer stringBuffer = new StringBuffer();
                if (data != null) {
                    String tp_answer = data.getTp_answer();


                    for (int i = 0; i < split.length; i++) {
                        char[] chars = split[i].toCharArray();
                        if (String.valueOf(chars[0]).trim().equals(tp_answer.trim())) {
                            stringBuffer.append(split[i].substring(1, split[i].length()).trim());
                        }
                    }
                }

                //对错
                uploadGradeDataBean.setTrRight("1");
                //分数
                uploadGradeDataBean.setTrMark("0");
                showDiaLog(1, stringBuffer.toString(), mUserAnswer.getContext());
            }
            //学生答案
            uploadGradeDataBean.setTrAnswer(mUserAnswer.getOption());


        } else {
            StringBuffer stringBuffer = new StringBuffer();
            if (data != null) {
                String tp_answer = data.getTp_answer();


                for (int i = 0; i < split.length; i++) {
                    char[] chars = split[i].toCharArray();
                    if (String.valueOf(chars[0]).trim().equals(tp_answer.trim())) {
                        stringBuffer.append(split[i].substring(1, split[i].length()).trim());
                    }
                }
            }

            showDiaLog(1, stringBuffer.toString(), "未作答");

            //学生答案
            uploadGradeDataBean.setTrAnswer("未作答");
            //分数
            uploadGradeDataBean.setTrMark("0");
            //对错
            uploadGradeDataBean.setTrRight("1");
        }
        if (data != null) {
            //班级
            uploadGradeDataBean.setTrClass(data.getTp_class() + "");
            //耗时
            uploadGradeDataBean.setTrTime(date + "");
            uploadGradeDataBean.setTrQuestion(data.getTp_subject());
            //题号
            uploadGradeDataBean.setTrPapernum(data.getTp_senum() + "");

            uploadGradeDataBean.setTrRightAnswer(data.getTp_answer());
            uploadGradeDataBean.setTrType(data.getTp_type() + "");
            UploadGrade(uploadGradeDataBean);
            if (data.getTp_senum() == ANSWER_QUESTION_SUM) {
                // mToResult.setVisibility(View.VISIBLE);
            }
        }
        mConfirmButton.setVisibility(View.GONE);


    }


    /**
     * 2019年4月1日 11:41:27
     * jiao hao kang
     * 单选题
     */
    private void singleChoice(ArrayList<SingleChoiceItemBean> singleDatas) {

        mOptionRecyclerview.setLayoutManager(new LinearLayoutManager(this, VERTICAL, false));
        SingleChoiceRecyclerViewAdapter answerSingleChoiceRecyclerViewAdapter = new SingleChoiceRecyclerViewAdapter(this);
        mOptionRecyclerview.setAdapter(answerSingleChoiceRecyclerViewAdapter);
        answerSingleChoiceRecyclerViewAdapter.setmList(singleDatas);
        answerSingleChoiceRecyclerViewAdapter.setOnItemClickListener(new SingleChoiceRecyclerViewAdapter.onItemClickListener() {
            @Override
            public void onItemClick(SingleChoiceItemBean item) {
                mUserAnswer = item;
            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.single_choice_confirm_button:
                if (mUserAnswer != null && !TextUtils.isEmpty(mUserAnswer.getOption())) {
                    confirm();
                } else {
                    Toast.makeText(this, "请选择答案!", Toast.LENGTH_SHORT).show();
                }


                break;

        /*    case R.id.single_choice_to_result_button:
                //toResult(date, System.currentTimeMillis() - submitTime);
                getAnswerRecord();
                break;*/
            default:
                break;
        }
    }

    @Override
    public void websocketStatusChange(int color) {
        Log.i("jiao", "單nwebsocketStatusChange: 前=" + color);
        if (mWebsocket_status != null) {
            mWebsocket_status.setBackgroundResource(color);
            Log.i("jiao", "單nwebsocketStatusChange: 后=" + color);
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
                if (data.getTp_senum() == ANSWER_QUESTION_SUM) {
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
                if (data.getTp_senum() == ANSWER_QUESTION_SUM) {
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
