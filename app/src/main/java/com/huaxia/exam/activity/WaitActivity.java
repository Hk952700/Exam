package com.huaxia.exam.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huaxia.exam.R;
import com.huaxia.exam.base.BaseActivity;
import com.huaxia.exam.bean.HttpParametersBean;
import com.huaxia.exam.service.ExamHpptService;
import com.huaxia.exam.utils.AnswerConstants;
import com.huaxia.exam.utils.SharedPreUtils;

import java.util.HashMap;

public class WaitActivity extends BaseActivity {

    private RelativeLayout mWebsocket_status;
    private Button mWaitLogout;
    private SimpleDraweeView img;

    @Override
    public int setContentView() {
        return R.layout.activity_wait;
    }

    @Override
    public Context setContext() {
        return WaitActivity.this;
    }


    @Override
    public void init() {
        mWebsocket_status = (RelativeLayout) findViewById(R.id.wait_websocket_status);
        //mWaitText = (TextView) findViewById(R.id.wait_wait_text);

        //右上角WebSocket状态方框
        mWaitLogout = (Button) findViewById(R.id.wait_logout);


        mWaitLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        img = (SimpleDraweeView) findViewById(R.id.wait_img);
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(R.drawable.wait))
                .build();
        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        img.setController(draweeController);
    }


    private void logout() {
        //准备清空Activity栈
        Intent intent = new Intent(WaitActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //发送广播到服务 通知服务 关闭WebSocket连接
        Message obtain = Message.obtain();
        obtain.what = 9;
        sendMssage(obtain);

        String ip = SharedPreUtils.getString(WaitActivity.this, "ip");

        //修改用户登录状态
        setUserLandingState(false);
        //清空当前用户的SP存值
        SharedPreUtils.cleardata(WaitActivity.this);
        SharedPreUtils.put(WaitActivity.this, "ip", ip);

        //跳转Activity并清空清空Activity栈栈
        startActivity(intent);
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
        httpParametersBean.setName("WaitActivity_SetUserStatus");
        //obtain.obj = httpParametersBean;
        intent.putExtra("http_msg", httpParametersBean);
        startService(intent);

    }


    @Override
    public void websocketStatusChange(int color) {
        Log.i("jiao", "witiwebsocketStatusChange: 前=" + color);
        if (mWebsocket_status != null) {
            mWebsocket_status.setBackgroundResource(color);
            Log.i("jiao", "witiwebsocketStatusChange: hou=" + color);
        }
    }

    @Override
    public void onHttpServiceResultListener(HttpParametersBean httpParametersBean) {
        if (httpParametersBean != null && httpParametersBean.getName().equals("WaitActivity_SetUserStatus")) {
            if (httpParametersBean.getStatus() == 0) {
                Log.i("jtest", "ConfirmActivity:success: 用户状态更更改为已登录");

            } else if (httpParametersBean.getStatus() == 1) {
                Log.i("jtest", "ConfirmActivity:success: 用户登录状态更改失败");
            }
        }

    }
}