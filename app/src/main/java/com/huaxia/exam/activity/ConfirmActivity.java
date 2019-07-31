package com.huaxia.exam.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huaxia.exam.R;
import com.huaxia.exam.base.BaseActivity;
import com.huaxia.exam.bean.HttpParametersBean;
import com.huaxia.exam.bean.UserInfoBean;
import com.huaxia.exam.service.ExamHpptService;
import com.huaxia.exam.utils.AnswerConstants;
import com.huaxia.exam.utils.SharedPreUtils;

import java.util.HashMap;


/**
 * 2019年3月25日 17:38:33
 * jiao hao kang
 * 学生登录后 确认信息  页面
 */
public class ConfirmActivity extends BaseActivity implements View.OnClickListener {

    private TextView mConfirmButton;
    private TextView mName;
    private Button mLoginAgainButton;

    private TextView mSchool;
    private TextView mCandidateNumber;
    private TextView mGrade;
    private RelativeLayout mWebsocket_status;
    //判断用户信息是否为空 从而决定用户是否可以创建  WebSocket 连接
    private boolean canCreateWebSocketConnect = false;

    @Override
    public int setContentView() {
        return R.layout.activity_confirm;
    }

    @Override
    public Context setContext() {
        return ConfirmActivity.this;
    }

    @Override
    public void init() {
        //右上角WebSocket状态方框
        mWebsocket_status = (RelativeLayout) findViewById(R.id.confirm_websocket_status);


        /*//地区
        mDistrict = (TextView) findViewById(R.id.tv_confirm_district);*/
        //学校
        mSchool = (TextView) findViewById(R.id.tv_confirm_school);

        //年级
        mGrade = (TextView) findViewById(R.id.tv_confirm_grade);

        //桌号
        mCandidateNumber = (TextView) findViewById(R.id.tv_confirm_candidate_number);
        //姓名
        mName = (TextView) findViewById(R.id.tv_confirm_name);

        //重登
        mLoginAgainButton = (Button) findViewById(R.id.btn_confirm_login_again);
        //确认文字
        mConfirmButton = (Button) findViewById(R.id.btn_confirm_confirm);

        mConfirmButton.setOnClickListener(this);//确认信息点击事件
        mLoginAgainButton.setOnClickListener(this);//重新登录点击事件


        getUserInfo();
    }

    private void getUserInfo() {
        Intent intent = getIntent();
        String user_info = intent.getStringExtra("user_info");
        if (!TextUtils.isEmpty(user_info)) {
            UserInfoBean userInfoBean = new Gson().fromJson(user_info, UserInfoBean.class);

            if (userInfoBean != null) {
                canCreateWebSocketConnect = true;
                SharedPreUtils.put(setContext(), "user_name", userInfoBean.getName()); //姓名
                SharedPreUtils.put(setContext(), "user_school", userInfoBean.getSchool() + "");//学校
                SharedPreUtils.put(setContext(), "user_numberplate", userInfoBean.getNumberplate());//桌号
                SharedPreUtils.put(setContext(), "user_personsn", userInfoBean.getPersonSn());//
                SharedPreUtils.put(setContext(), "user_grade", userInfoBean.getGrade());//班级
                SharedPreUtils.put(setContext(), "user_sceneid", userInfoBean.getSceneid());
                mSchool.setText("　学校　:　" + userInfoBean.getSchool());
                mCandidateNumber.setText("　桌号　:　" + userInfoBean.getNumberplate());
                mName.setText("　姓名　:　" + userInfoBean.getName());
                mGrade.setText("　年级　:　" + userInfoBean.getGrade());
            } else {
                canCreateWebSocketConnect = false;
            }

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm_confirm:
                //TODO 后期改
                //考生确认信息完毕 点击按钮 WebSocketClient 建立连接


                if (canCreateWebSocketConnect) {
                    String ip = SharedPreUtils.getString(ConfirmActivity.this, "ip");
                    createClient(ip + ":" + AnswerConstants.PORT);
                    setUserLandingState(true);
                } else {
                    Toast.makeText(this, "请重新登录!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_confirm_login_again://点击重登按钮(textview)
                loginAgain();
                break;
            default:
                break;
        }

    }


    /**
     * 2019年5月17日 10:05:38
     * jiao hao kang
     *
     * @param flag true 改为已登录  false 改为未登录
     */
    private void setUserLandingState(boolean flag) {

        HashMap<String, String> map = new HashMap<>();


        if (!flag) {
            map.put("landingState", "0");
        } else {
            map.put("landingState", "1");
        }
        map.put("personSn", SharedPreUtils.getString(this, "user_personsn"));


        Intent intent = new Intent(this, ExamHpptService.class);
        HttpParametersBean httpParametersBean = new HttpParametersBean();
        httpParametersBean.setUrl(AnswerConstants.UPDATE_STATE);
        httpParametersBean.setMap(map);
        httpParametersBean.setType(1);
        httpParametersBean.setName("SetUserStatus");
        //obtain.obj = httpParametersBean;
        intent.putExtra("http_msg", httpParametersBean);
        startService(intent);


    }

    private void createClient(String ipAndPort) {
        Message obtain = Message.obtain();
        obtain.what = 1;
        obtain.obj = "ws://" + ipAndPort + "/websocket".trim();
        sendMssage(obtain);
        showProgressDialog(this, "正在建立连接...");
    }

    private void loginAgain() {
        //跳转到登录页面
        startActivity(new Intent(ConfirmActivity.this, LoginActivity.class));
        finish();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void websocketStatusChange(int color) {
        if (mWebsocket_status != null) {
            mWebsocket_status.setBackgroundResource(color);
        }
    }

    @Override
    public void onHttpServiceResultListener(HttpParametersBean httpParametersBean) {
        if (httpParametersBean != null && httpParametersBean.getName().equals("SetUserStatus")) {
            if (httpParametersBean.getStatus() == 0) {
                Log.i("jtest", "ConfirmActivity:success: 用户状态更更改为已登录");

            } else if (httpParametersBean.getStatus() == 1) {
                Log.i("jtest", "ConfirmActivity:success: 用户登录状态更改失败");
            }
        }
    }

}