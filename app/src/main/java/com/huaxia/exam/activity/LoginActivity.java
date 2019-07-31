package com.huaxia.exam.activity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huaxia.exam.R;
import com.huaxia.exam.base.BaseActivity;
import com.huaxia.exam.bean.HttpParametersBean;
import com.huaxia.exam.bean.UserInfoBean;
import com.huaxia.exam.service.ExamHpptService;
import com.huaxia.exam.service.ExamMainSrevice;
import com.huaxia.exam.utils.AnswerConstants;
import com.huaxia.exam.utils.SharedPreUtils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 2019年3月25日 13:48:02
 * jiao hao kang
 * 登录页面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mCandidateNumber;//考号EditText
    private EditText mIpNumber;//ipEditText
    private Button mLoginButton;//登录按钮
    private String mIpNumberString;
    private String mCandidateNumberString;
    private Button mBtnScreenCapture;
    private RelativeLayout mWebsocket_status;
    private String ip;
    private TextView mVersionCodeAndName;


    @Override
    public int setContentView() {
        return R.layout.activity_login;
    }

    @Override
    public Context setContext() {
        return LoginActivity.this;
    }

    @Override
    public void init() {
        //应用启动 判断服务是否开启如果开启关闭再开启  如果关闭 直接开启
        boolean serviceRunning = isServiceRunning(ExamMainSrevice.class.getPackage().getName() + ".ExamMainSrevice");
        Log.i("jtest", "onCreate: 1服务开启状态：=" + serviceRunning);

        if (serviceRunning) {
            stopService(new Intent(LoginActivity.this, ExamMainSrevice.class));
            boolean serviceRunning1 = isServiceRunning(ExamMainSrevice.class.getPackage().getName() + ".ExamMainSrevice");
            Log.i("jtest", "onCreate: 2服务开启状态：=" + serviceRunning1);

        }
        startService(new Intent(LoginActivity.this, ExamMainSrevice.class));
        ip = SharedPreUtils.getString(this, "ip");
        //清空当前用户的SP存值
        SharedPreUtils.cleardata(LoginActivity.this);
        SharedPreUtils.put(this, "ip", ip);

        SharedPreUtils.put(LoginActivity.this, "socket_connect_count", 0);

        //右上角WebSocket状态方框
        mWebsocket_status = (RelativeLayout) findViewById(R.id.login_websocket_status);

        mVersionCodeAndName = (TextView) findViewById(R.id.login_version_code_and_name);
        mVersionCodeAndName.setText("V" + getVersionName(this));


        //考号EditText
        mCandidateNumber = (EditText) findViewById(R.id.login_candidate_number);
        //ipEditText
        mIpNumber = (EditText) findViewById(R.id.login_ip_number);
        //登录按钮(文字)
        mLoginButton = (Button) findViewById(R.id.login_login_button);
        mLoginButton.setOnClickListener(LoginActivity.this);


        ip = SharedPreUtils.getString(this, "ip");
        if (!TextUtils.isEmpty(ip)) {
            mIpNumber.setText(ip);
        }
        String candidate_number_val = SharedPreUtils.getString(this, "candidate_number");
        if (!TextUtils.isEmpty(ip)) {
            mCandidateNumber.setText(candidate_number_val);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login_button:

                mCandidateNumberString = mCandidateNumber.getText().toString().trim();//考号
                mIpNumberString = mIpNumber.getText().toString().trim();//ip


                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(mCandidateNumberString);

                if (!TextUtils.isEmpty(mCandidateNumberString) && !TextUtils.isEmpty(mIpNumberString)) {
                    if (m.matches()) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("Numberplate", mCandidateNumberString);
                        login(mIpNumberString, AnswerConstants.GET_USER_INFO, map);
                        showProgressDialog(this, "登录中...");
                    } else {
                        Toast.makeText(this, "请检查桌牌号", Toast.LENGTH_SHORT).show();
                    }                 //TODO 后期更改

                } else {
                    Toast.makeText(this, "请检查桌牌号或者ip", Toast.LENGTH_SHORT).show();
                }
                break;


            default:
                break;
        }
    }


    private void login(String baseUrl, String url, HashMap<String, String> map) {
        Intent intent = new Intent(LoginActivity.this, ExamHpptService.class);
        HttpParametersBean httpParametersBean = new HttpParametersBean();
        httpParametersBean.setUrl("http://" + baseUrl + ":" + AnswerConstants.PORT + "/".trim());
        httpParametersBean.setMap(map);
        httpParametersBean.setType(0);
        httpParametersBean.setName("Login");
        intent.putExtra("http_msg", httpParametersBean);
        startService(intent);


    }


    private boolean isServiceRunning(String ServicePackageName) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (ServicePackageName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void websocketStatusChange(int img) {
        Log.i("jiao", "loginwebsocketStatusChange: 前=" + img);
        if (mWebsocket_status != null) {
            mWebsocket_status.setBackgroundResource(img);
            Log.i("jiao", "loginwebsocketStatusChange: hou=" + img);
        }
    }

    @Override
    public void onHttpServiceResultListener(HttpParametersBean httpParametersBean) {
        if (httpParametersBean != null && httpParametersBean.getName().equals("Login")) {
            if (httpParametersBean.getStatus() == 0) {
                //TODO  返回的数据跳转到确认信息页面
                Log.i("jtest", "LoginActivity:login:success: " + httpParametersBean.getResultValue());
                dismissProgressDialog();
                if (!TextUtils.isEmpty(httpParametersBean.getResultValue())) {
                    UserInfoBean userInfoBean = new Gson().fromJson(httpParametersBean.getResultValue(), UserInfoBean.class);
                    if (userInfoBean != null && userInfoBean.getLandingState().equals("0")) {
                        Intent intent = new Intent(LoginActivity.this, ConfirmActivity.class);
                        intent.putExtra("user_info", httpParametersBean.getResultValue());
                        SharedPreUtils.put(LoginActivity.this, "ip", mIpNumberString);
                        SharedPreUtils.put(LoginActivity.this, "candidate_number", mCandidateNumberString);
                        startActivity(intent);
                        finish();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).setTitle("重复登录!")
                                .setMessage("帐号已经在另一台电脑上登录请联系管理员或重试!")
                                .setCancelable(false)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }).create();
                        alertDialog.show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "请重试或者联系管理员", Toast.LENGTH_SHORT).show();
                }


            } else if (httpParametersBean.getStatus() == 1) {
                dismissProgressDialog();
                Log.i("jtest", "LoginActivity:login:fail: " + httpParametersBean.getResultValue());
                Toast.makeText(LoginActivity.this, "请重试或者联系管理员", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //获取版本名
    public String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //获取版本号
    public int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }


    //通过PackageInfo得到的想要启动的应用的包名
    private PackageInfo getPackageInfo(Context context) {
        PackageInfo pInfo = null;

        try {
            //通过PackageManager可以得到PackageInfo
            PackageManager pManager = context.getPackageManager();
            pInfo = pManager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pInfo;

    }
}