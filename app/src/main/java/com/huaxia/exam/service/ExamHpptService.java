package com.huaxia.exam.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.huaxia.exam.bean.HttpParametersBean;
import com.huaxia.exam.utils.AnswerConstants;
import com.huaxia.exam.utils.httputils.HttpHelper;

import java.util.HashMap;


/**
 * 进行Http请求的Service
 * jiao hao kang
 * 2019年5月28日 14:11:44
 */
public class ExamHpptService extends Service {

    private LocalBroadcastManager localBroadcastManager;

    @Override
    public void onCreate() {
        super.onCreate();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //接收到后进行操作
        if (intent != null) {
            final HttpParametersBean httpParametersBean = (HttpParametersBean) intent.getParcelableExtra("http_msg");
            if (httpParametersBean != null) {
                /* handler.sendMessage(msg);*/
                final String url = httpParametersBean.getUrl();
                final HashMap<String, String> map = httpParametersBean.getMap();
                int type = httpParametersBean.getType();

                switch (type) {
                    //根据不同的what进行不同的网络请求
                    case 0:

                        new HttpHelper(this, url).get(AnswerConstants.GET_USER_INFO, map).result(new HttpHelper.HttpListener() {
                            @Override
                            public void success(String data) {
                            /*Message obtain = Message.obtain();
                            obtain.what = 0;
                            obtain.obj = data;*/
                                httpParametersBean.setStatus(0);
                                httpParametersBean.setResultValue(data);
                                sendMssage(httpParametersBean);

                            }

                            @Override
                            public void fail(String data) {
                           /* Message obtain = Message.obtain();
                            obtain.what = 1;
                            obtain.obj = data;
                            sendMssage(obtain);*/
                                httpParametersBean.setResultValue(data);
                                httpParametersBean.setStatus(1);
                                sendMssage(httpParametersBean);

                            }
                        });
                        break;
                    case 1:
                        new HttpHelper(this).get(url, map).result(new HttpHelper.HttpListener() {
                            @Override
                            public void success(String data) {
                            /*Message obtain = Message.obtain();
                            obtain.what = 0;
                            obtain.obj = data;*/
                                httpParametersBean.setStatus(0);
                                httpParametersBean.setResultValue(data);
                                sendMssage(httpParametersBean);

                            }

                            @Override
                            public void fail(String data) {
                           /* Message obtain = Message.obtain();
                            obtain.what = 1;
                            obtain.obj = data;
                            sendMssage(obtain);*/
                                httpParametersBean.setResultValue(data);
                                httpParametersBean.setStatus(1);
                                sendMssage(httpParametersBean);

                            }
                        });
                        break;
                    case 2:
                        new HttpHelper(this).post(url, map).result(new HttpHelper.HttpListener() {
                            @Override
                            public void success(String data) {
                            /*Message obtain = Message.obtain();
                            obtain.what = 0;
                            obtain.obj = data;*/
                                httpParametersBean.setStatus(0);
                                httpParametersBean.setResultValue(data);
                                sendMssage(httpParametersBean);

                            }

                            @Override
                            public void fail(String data) {
                           /* Message obtain = Message.obtain();
                            obtain.what = 1;
                            obtain.obj = data;
                            sendMssage(obtain);*/
                                httpParametersBean.setResultValue(data);
                                httpParametersBean.setStatus(1);
                                sendMssage(httpParametersBean);

                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 发送广播到Activity
     *
     * @param msg
     */
    public void sendMssage(HttpParametersBean msg) {
        //TODO 后期根据后台数据进行更改
        Intent intent = new Intent("com.huaxian.Activity.HttpBroadcastReceiver");
        intent.putExtra("http_result_msg", msg);
        localBroadcastManager.sendBroadcast(intent);
    }


}
