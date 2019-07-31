package com.huaxia.exam.base;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.huaxia.exam.R;
import com.huaxia.exam.activity.AnswerRecordActivity;
import com.huaxia.exam.activity.WaitActivity;
import com.huaxia.exam.activity.answer.MultipleChoiceActivity;
import com.huaxia.exam.activity.answer.NineSelectedFiveActivity;
import com.huaxia.exam.activity.answer.SingleChoiceActivity;
import com.huaxia.exam.activity.answer.TwelveSelectedSevenActivity;
import com.huaxia.exam.bean.AnswerResultDataBean;
import com.huaxia.exam.bean.HttpParametersBean;
import com.huaxia.exam.bean.UploadGradeDataBean;
import com.huaxia.exam.service.ExamHpptService;
import com.huaxia.exam.utils.AnswerConstants;
import com.huaxia.exam.utils.CountDownTimer;
import com.huaxia.exam.utils.SharedPreUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public abstract class BaseActivity extends Activity {

    private LocalBroadcastManager localBroadcastManager;
    private LocalReceiver localReceiver;
    private boolean flag = false;
    private long timeStamp; //答題倒计时用的时间
    private BaseActivity.MyHandler handler = new BaseActivity.MyHandler(BaseActivity.this);
    private Context mContext;
    private TextView mCountDownText;
    private InputMethodManager imm;
    private AnimatorSet animatorSet1;

    private int[] arr;
    private ImageView mAnswerImageL;
    private ImageView mAnswerImageR;
    private RelativeLayout mAnswerRLV_ll;
    private AnimatorSet animatorSet;
    private LocalHttpReceiver localHttpReceiver;
    private SimpleDateFormat format = new java.text.SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(setContentView());
        this.mContext = setContext();
        registerLocalReceiver();//注册广播接收者
        init();
        //点击输入框外部软键盘消失相关
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //取消跳转动画
        overridePendingTransition(0, 0);
        NavigationBarStatusBar(BaseActivity.this, true);
    }

/*    public void OpenAnimation(final ImageView answerImageL, final ImageView answerImageR, final RelativeLayout answerRLV_LL) {
        this.mAnswerImageL = answerImageL;
        this.mAnswerImageR = answerImageR;
        mAnswerRLV_ll = answerRLV_LL;
        answerImageR.post(new Runnable() {
            @Override
            public void run() {
                Message obtain = Message.obtain();
                obtain.what = 3;
                int width = mAnswerImageR.getWidth();
                int left = mAnswerImageR.getLeft();
                arr = new int[]{width, left};
                ArrayList<View> views = new ArrayList<>();
                views.add(mAnswerImageL);
                views.add(mAnswerImageR);
                views.add(mAnswerRLV_ll);
                obtain.obj = views;
                handler.sendMessage(obtain);
            }
        });

    }*/
/*
    public void CloseAnimation(final ImageView answerImageL, final ImageView answerImageR, final RelativeLayout answerRLV_LL) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            animatorSet1.reverse();
        } else {
            ObjectAnimator translationX1 = new ObjectAnimator().ofFloat(answerImageL, "translationX", -(getWidth() - (arr[0] * 2)), *//*getWidth() - arr[0] * 2*//*0);
            ObjectAnimator translationX2 = new ObjectAnimator().ofFloat(answerRLV_LL, "translationX", -getWidth(), -arr[0] * 2);
            animatorSet = new AnimatorSet();
            animatorSet.playTogether(translationX1, translationX2);
            animatorSet.setDuration(1 * 1000);
            animatorSet.start();
          *//*  animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });*//*
        }


    }*/

    public abstract int setContentView();

    public abstract Context setContext();

    public abstract void init();

    private void onReceiveLocalReceiverListener1(String message) {
        if (!TextUtils.isEmpty(message)) {

            AnswerResultDataBean answerResultDataBean = new Gson().fromJson(message.trim(), AnswerResultDataBean.class);

            //TODO 根据返回的类型进行跳转页面   和选字题
            if (answerResultDataBean.getTp_type() == 1 || answerResultDataBean.getTp_type() == 4 || answerResultDataBean.getTp_type() == 3) {
                //单选题
                Intent intent1 = new Intent(this, SingleChoiceActivity.class);
                intent1.putExtra("answer", answerResultDataBean);
                startActivity(intent1);
                finish();
            } else if (answerResultDataBean.getTp_type() == 2) {
                //多选题
                Intent intent2 = new Intent(this, MultipleChoiceActivity.class);
                intent2.putExtra("answer", answerResultDataBean);
                startActivity(intent2);
                finish();
            } else if (answerResultDataBean.getTp_type() == 5) {
                //九宫格9选5题
                Intent intent5 = new Intent(this, NineSelectedFiveActivity.class);
                intent5.putExtra("answer", answerResultDataBean);
                Log.i("jtest", "onReceiveLocalReceiver: base九宫格" + intent5.getParcelableExtra("answer"));
                startActivity(intent5);
                finish();
            } else if (answerResultDataBean.getTp_type() == 6) {
                //十二宫格12选7题
                Intent intent6 = new Intent(this, TwelveSelectedSevenActivity.class);
                intent6.putExtra("answer", answerResultDataBean);
                startActivity(intent6);
                finish();
            }
        }


    }


    /**
     * 将时间戳转换为时间
     *
     * @param timeMillis
     * @return
     */
    public String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm分ss秒SSS");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    /**
     * 发送广播到Service之后传递给后台服务器
     *
     * @param msg
     */
    public void sendMssage(Message msg) {
        //TODO 后期根据后台数据进行更改
        Intent intent = new Intent("com.huaxian.ActivityBroadcastReceiver");
        intent.putExtra("msg", msg);
        localBroadcastManager.sendBroadcast(intent);
    }

    /**
     * 点击确认按钮
     */
    public void confirm() {
        //取消倒计时
        if (!flag) {
            if (mCountDownTimer != null) {
                mCountDownTimer.onFinish();
            }

            //     handler.sendEmptyMessage(1);
        } else {
            //Toast.makeText(mContext, "您已经提交过了", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //点击输入框外部软键盘消失相关
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null) {
                if (getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(BaseActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }


    private CountDownTimer mCountDownTimer;
    private int countDownShowTime;//倒计时显示的内容

    /**
     * 启动倒计时
     *
     * @param mCountDownText 需要传入一个页面倒计时的 TextView
     */
    public void startCountDown(final TextView mCountDownText, int countDownShowTime1) {
        this.countDownShowTime = countDownShowTime1;
        this.mCountDownText = mCountDownText;
        mCountDownText.setVisibility(View.VISIBLE);
        mCountDownText.setText(countDownShowTime + "");
        timeStamp = 0;

        if (mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer((long) ((countDownShowTime1 - 0.1) * 1000), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if (!isFinishing()) {
                        countDownShowTime--;
                        mCountDownText.setText(countDownShowTime + "");
                    }

                }

                @Override
                public void onFinish() {
                    countDownShowTime = 0;
                    handler.sendEmptyMessage(1);


                }
            };
        }

        handler.sendEmptyMessage(0);
    }


    /**
     * Handler 内部类(防内存泄露)
     */
    private class MyHandler extends Handler {
        // 弱引用 ，防止内存泄露
        private WeakReference<BaseActivity> weakReference;


        public MyHandler(BaseActivity handlerMemoryActivity) {
            weakReference = new WeakReference<BaseActivity>(handlerMemoryActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 通过  软引用  看能否得到activity示例
            BaseActivity handlerMemoryActivity = weakReference.get();
            // 防止内存泄露
            if (handlerMemoryActivity != null) {
                // 如果当前Activity，进行UI的更新
                switch (msg.what) {
                    case 0://开始倒计时  开始时记录系统 的时间
                        mCountDownTimer.start();
                        timeStamp = System.currentTimeMillis();

                        break;

                    case 1://正常结束   或者  选手提交结束
                        Log.i("jtest", "BaseActivity:handleMessage: what=1");
                        if (mCountDownTimer != null) {
                            if (!flag) {
                                flag = true;
                                long timeStamp1 = System.currentTimeMillis();
                                long a = timeStamp1 - timeStamp;
                                String s = stampToDate(a);
                                mCountDownText.setText(countDownShowTime + "");
                                /*Toast.makeText(mContext, "计时结束  时间" + s, Toast.LENGTH_SHORT).show();*/
                                mCountDownTimer.cancel();
                                onCountDownFinish(a, timeStamp1);
                                //CloseAnimation(mAnswerImageL, mAnswerImageR, mAnswerRLV_ll);
                            }

                        }
                        break;
    /*                case 3:
                        ArrayList<View> arrayList = (ArrayList<View>) msg.obj;
                        Log.i("jtest", "BaseActivity:handleMessage: " + arr[0] + "  " + arr[1]);
                        ObjectAnimator translationX1 = new ObjectAnimator().ofFloat((ImageView) arrayList.get(0), "translationX", 0, -(getWidth() - (arr[0] * 2)));
                        ObjectAnimator translationX2 = new ObjectAnimator().ofFloat((RelativeLayout) arrayList.get(2), "translationX", -arr[0] * 2, -getWidth());
                        animatorSet1 = new AnimatorSet();
                        animatorSet1.playTogether(translationX1, translationX2);
                        animatorSet1.setDuration(1 * 1000);
                        animatorSet1.start();
                        animatorSet1.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                animationEnd();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });

                        break;*/

                    case 10:
                        String a = (String) msg.obj;
                        onReceiveLocalReceiverListener1(a);
                        break;

                    case 11:
                        int websocket_status = (int) msg.obj;
                        switch (websocket_status) {
                            case 10:
                                break;

                            case 1://onOpen
                                Log.i("jiao", "onReceive: 1");
                                websocketStatusChange(R.drawable.img_back_green);
                                //onWebsocketStatusChange.onChange(R.color.colorGreen);
                                break;
                            case 2://onMessage
                                Log.i("jiao", "onReceive: 2");
                                websocketStatusChange(R.drawable.img_back_bulu);
                                //onWebsocketStatusChange.onChange(R.color.colorDeepSkyBlue);
                                break;
                            case -1://onClose
                                Log.i("jiao", "onReceive: -1");
                                websocketStatusChange(R.drawable.img_back_red);
                                //onWebsocketStatusChange.onChange(R.color.colorred);
                                break;
                            case 0://onError
                                Log.i("jiao", "onReceive: 0");
                                websocketStatusChange(R.drawable.img_back_yello);
                                //onWebsocketStatusChange.onChange(R.color.colorYellow);
                                break;


                            default:

                                break;
                        }
                        break;
                    case 12:
                        HttpParametersBean message = (HttpParametersBean) msg.obj;
                        onHttpServiceResultListener(message);
                        break;
                    default:
                        break;
                }
            }
        }
    }

 /*   *//**
     * 子类重写实现
     *//*
    public void animationEnd() {

    }*/

    public abstract void websocketStatusChange(int img);

    public void onCountDownFinish(long date, long submitTime) {
        //隐藏TextView
        mCountDownText.setVisibility(View.GONE);

    }

    public void getAnswerRecord() {
        Intent intent = new Intent(this, AnswerRecordActivity.class);
        startActivity(intent);
        finish();
    }

    public int getWidth() {
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        //int height = outMetrics.heightPixels;
        return width;
    }

    /**
     * 注册广播接收者
     */
    private void registerLocalReceiver() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.huaxian.ServiceBroadcastReceiver");


        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("com.huaxian.Activity.HttpBroadcastReceiver");


        localReceiver = new BaseActivity.LocalReceiver();
        localHttpReceiver = new BaseActivity.LocalHttpReceiver();


        //注册本地接收器
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
        localBroadcastManager.registerReceiver(localHttpReceiver, intentFilter1);
    }

    /**
     * 广播接受者 内部类  主要接受Http信息
     * jiao hao kang
     * 2019年5月29日 10:37:45
     */

    private class LocalHttpReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Message obtain = Message.obtain();
            obtain.what = 12;
            obtain.obj = intent.getParcelableExtra("http_result_msg");
            handler.sendMessage(obtain);

        }
    }


    /**
     * 广播接收者  内部类   主要接收WebSocket信息
     */
    private class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            Message obtain = Message.obtain();
            int websocket_status = intent.getIntExtra("websocket_status", 10);
            if (websocket_status != 10) {
                Message obtain1 = Message.obtain();
                obtain1.what = 11;
                obtain1.obj = websocket_status;
                handler.handleMessage(obtain1);
            }


            String service_data = intent.getStringExtra("service_data");


            if (!TextUtils.isEmpty(service_data)) {

                Log.i("jtest", "onReceive: base接到来自本地服务" + intent.getStringExtra("service_data"));

                if (service_data.equals("onError")) {
                    //Toast.makeText(context, "请重试或联系管理员", Toast.LENGTH_SHORT).show();
                    dismissProgressDialog();
                } else if (service_data.equals("onOpen suceeses!")) {

                    int socket_connect_count = SharedPreUtils.getInt(BaseActivity.this, "socket_connect_count");
                    if (socket_connect_count <= 1) {
                        Intent intent1 = new Intent(BaseActivity.this, WaitActivity.class);
                        startActivity(intent1);
                        finish();
                    }
                } else {
                    obtain.what = 10;
                    obtain.obj = service_data;
                    handler.sendMessage(obtain);
                }
            }


        }

    }

    /**
     * 使用BufferedWriter进行文本内容的追加
     *
     * @param file
     * @param content
     */
    private void addTxtToFileBuffered(File file, String content, String logMsg) {
        //在文本文本中追加内容

        if (!file.exists()) {
            try {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedWriter out = null;
        try {
            //FileOutputStream(file, true),第二个参数为true是追加内容，false是覆盖
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.newLine();//换行
            out.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.i("jtest", "addTxtToFileBuffered: " + logMsg + "写入完成!");
    }

    public void onHttpServiceResultListener(HttpParametersBean httpParametersBean) {
        if (httpParametersBean != null) {
            if (httpParametersBean.getName().equals("BaseActivity_UpLoadGrade")) {
                if (httpParametersBean.getStatus() == 0) {
                    Log.i("jtest", "success: 上传成绩成功!");
                } else if (httpParametersBean.getStatus() == 1) {
                    File file = new File(getExternalCacheDir().getAbsolutePath() + "/crash/upLoadGrade/" + SharedPreUtils.getString(this, "user_name") + "_" + SharedPreUtils.getString(this, "user_numberplate").trim() + "_log.txt");
                    String time = format.format(new Date());
                    addTxtToFileBuffered(file, "失败信息=" + time + "= error= " + httpParametersBean.getResultValue() + "==data==" + httpParametersBean.getMap().toString(), "data");
                    Log.i("jtest", "fail: 上传成绩失败!=" + httpParametersBean.getResultValue());
                }
            }
        }
    }


    public void UploadGrade(UploadGradeDataBean uploadGradeDataBean) {
        Gson gson = new Gson();
        String s = gson.toJson(uploadGradeDataBean);
        Log.i("jtest", "UploadGrade: 上传成绩前保存到数据库:" + s);

        HashMap<String, String> map = new HashMap<>();

        map.put("trUsername", SharedPreUtils.getString(this, "user_name"));//姓名
        map.put("trUsernum", SharedPreUtils.getString(this, "user_numberplate"));//桌号
        map.put("trScene", SharedPreUtils.getString(this, "user_sceneid"));//场次
        map.put("trSchool", SharedPreUtils.getString(this, "user_school"));//学校

        map.put("trClass", uploadGradeDataBean.getTrClass());//班级
        map.put("trPapernum", uploadGradeDataBean.getTrPapernum());//题号
        map.put("trTime", uploadGradeDataBean.getTrTime());//耗时
        map.put("trAnswer", uploadGradeDataBean.getTrAnswer());//选手答案
        map.put("trMark", uploadGradeDataBean.getTrMark());//分数
        map.put("trRight", uploadGradeDataBean.getTrRight());//正确


        map.put("trRightAnswer", uploadGradeDataBean.getTrRightAnswer());//正确答案
        map.put("trType", uploadGradeDataBean.getTrType());//题的类型
        map.put("trQuestion", uploadGradeDataBean.getTrQuestion());//题的 问题


        Intent intent = new Intent(this, ExamHpptService.class);
        HttpParametersBean httpParametersBean = new HttpParametersBean();
        httpParametersBean.setUrl(AnswerConstants.UP_LOAD_GRADE);
        httpParametersBean.setMap(map);
        httpParametersBean.setType(2);
        httpParametersBean.setName("BaseActivity_UpLoadGrade");
        //obtain.obj = httpParametersBean;
        intent.putExtra("http_msg", httpParametersBean);
        startService(intent);
        /*new HttpHelper(this).post(AnswerConstants.UP_LOAD_GRADE, map).result(new HttpHelper.HttpListener() {
            @Override
            public void success(String data) {
                Log.i("jtest", "success: 上传成绩成功!");

            }

            @Override
            public void fail(String data) {
                Log.i("jtest", "fail: 上传成绩失败!=" + data);
            }
        });*/
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }

        if (mCountDownTimer != null) {
            mCountDownTimer = null;
        }

        //onDestroy的时候取消注册广播
        if (localBroadcastManager != null && localReceiver != null) {
            localBroadcastManager.unregisterReceiver(localReceiver);
            localReceiver = null;

        } else {
            Log.i("jtest", "BaseActivity:onDestroy: localBroadcastManager为null或者localReceiver为null取消注册失败");
        }

        if (localBroadcastManager != null && localHttpReceiver != null) {
            localBroadcastManager.unregisterReceiver(localHttpReceiver);
            localHttpReceiver = null;
            localBroadcastManager = null;
        } else {
            Log.i("jtest", "BaseActivity:onDestroy: localBroadcastManager为null或者localHttpReceiver为null取消注册失败");
        }

        if (animatorSet != null) {
            animatorSet.cancel();
            animatorSet = null;
        }

        if (animatorSet1 != null) {
            animatorSet1.cancel();
            animatorSet1 = null;
        }
        dismissProgressDialog();
    }


    /**
     * 导航栏，状态栏隐藏
     *
     * @param activity
     */
    public static void NavigationBarStatusBar(Activity activity, boolean hasFocus) {
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    public ProgressDialog wait;

    public void showProgressDialog(Context context, String msg) {
        wait = new ProgressDialog(context);
        //设置风格为圆形
        wait.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        wait.setTitle("请稍等......");
        wait.setIcon(null);
        //设置提示信息
        wait.setMessage(msg);
        //设置是否可以通过返回键取消
        wait.setCancelable(false);
        wait.setIndeterminate(false);
        wait.show();
    }

    public void dismissProgressDialog() {
        if (wait != null) {
            wait.dismiss();
        } else {
            Log.i("test", "dismissProgressDialog: " + "因为没有开启ProgressDialog所以没有关闭");
        }
    }



}
