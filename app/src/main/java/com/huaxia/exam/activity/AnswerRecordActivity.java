package com.huaxia.exam.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.huaxia.exam.R;
import com.huaxia.exam.adapter.AnswerRecodeAdapter3;
import com.huaxia.exam.adapter.AnswerRecordAdapter1;
import com.huaxia.exam.adapter.AnswerRecordAdapter2;
import com.huaxia.exam.base.BaseActivity;
import com.huaxia.exam.bean.HttpParametersBean;
import com.huaxia.exam.bean.UserRecoderBean;
import com.huaxia.exam.bean.UserStandingListBean;
import com.huaxia.exam.service.ExamHpptService;
import com.huaxia.exam.utils.AnswerConstants;
import com.huaxia.exam.utils.ScreenCaptureUtil;
import com.huaxia.exam.utils.SharedPreUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class AnswerRecordActivity extends BaseActivity {

    private RecyclerView answer_record_rv1;
    private RecyclerView answer_record_rv2;
    private RecyclerView answer_record_rv3;
    private TextView mtvScore;
    private TextView mtvtTime;
    private TextView mtvAccuracy;
    private TextView mTvUserName;
    private Button mBtnLogout;
    private RelativeLayout mWebsocket_status;
    //TODO  2019年5月25日 16:46:57 添加获取失败的时候显示的按钮  和 失败信息添加文件 的 操作  未测试
    private SimpleDateFormat format = new java.text.SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private Button mBtnRecordRetry;
    private TextView mTvUserNumberplate;
    private ImageView answer_record_score_hundreds;
    private ImageView answer_record_score_tens;
    private ImageView answer_record_score_ones;
    //-----------

    @Override
    public int setContentView() {
        return R.layout.activity_answer_record;
    }

    @Override
    public Context setContext() {
        return AnswerRecordActivity.this;
    }

    @Override
    public void init() {

        //右上角WebSocket状态方框
        mWebsocket_status = (RelativeLayout) findViewById(R.id.answer_record_websocket_status);

        //TODO  2019年5月25日 16:46:57 添加获取失败的时候显示的按钮  和 失败信息添加文件 的 操作  未测试
        mBtnRecordRetry = (Button) findViewById(R.id.btn_answer_record_retry);
        mBtnRecordRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserRecored();
            }
        });

        answer_record_rv1 = (RecyclerView) findViewById(R.id.answer_record_rv1);
        answer_record_rv2 = (RecyclerView) findViewById(R.id.answer_record_rv2);
        answer_record_rv3 = (RecyclerView) findViewById(R.id.answer_record_rv3);

        //mtvScore = (TextView) findViewById(R.id.tv_answer_record_score);
        mtvtTime = (TextView) findViewById(R.id.answer_record_user_time);
        mtvAccuracy = (TextView) findViewById(R.id.answer_record_user_accuracy);


        answer_record_score_hundreds = (ImageView) findViewById(R.id.answer_record_user_score_hundreds);
        answer_record_score_tens = (ImageView) findViewById(R.id.answer_record_user_score_tens);
        answer_record_score_ones = (ImageView) findViewById(R.id.answer_record_user_score_ones);


        mTvUserName = (TextView) findViewById(R.id.answer_record_user_name);
        mTvUserNumberplate = (TextView) findViewById(R.id.answer_record_user_numberplate);
        mTvUserName.setText(SharedPreUtils.getString(this, "user_name"));
        mTvUserNumberplate.setText(SharedPreUtils.getString(this, "user_numberplate"));

        mBtnLogout = (Button) findViewById(R.id.btn_answer_record_logout);
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //准备清空Activity栈
                Intent intent = new Intent(AnswerRecordActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                //发送广播到服务 通知服务 关闭WebSocket连接
                Message obtain = Message.obtain();
                obtain.what = 9;
                sendMssage(obtain);


                String ip = SharedPreUtils.getString(AnswerRecordActivity.this, "ip");
                //修改用户登录状态
                //setUserLandingState(false);


                //清空当前用户的SP存值
                SharedPreUtils.cleardata(AnswerRecordActivity.this);
                SharedPreUtils.put(AnswerRecordActivity.this, "ip", ip);


                //截图并保存到指定文件  通知相册更新
                ScreenCaptureUtil screenCaptureUtil = new ScreenCaptureUtil();
                Bitmap bitmap = screenCaptureUtil.captureScreen(AnswerRecordActivity.this);
                screenCaptureUtil.saveImageToGallery(AnswerRecordActivity.this, bitmap);


                //跳转Activity并清空清空Activity栈栈
                startActivity(intent);
            }
        });


        getUserRecored();

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
        httpParametersBean.setName("AnswerRecordActivity_SetUserStatus");
        //obtain.obj = httpParametersBean;
        intent.putExtra("http_msg", httpParametersBean);
        startService(intent);


       /* new HttpHelper(this).get(AnswerConstants.UPDATE_STATE, map).result(new HttpHelper.HttpListener() {
            @Override
            public void success(String data) {

                Log.i("jtest", "ConfirmActivity:success: 用户状态更更改为未登录");
            }

            @Override
            public void fail(String data) {
                Log.i("jtest", "ConfirmActivity:success: 用户登录状态更改失败");
            }
        });*/
    }


    private int AnswerNum = 0;

    /**
     * 2019年5月17日 15:59:34
     * jiao hao kang
     * 获取用户答题列表
     *
     * @return
     */
    private void getUserRecored() {
        showProgressDialog(this, "正在获取考试记录请稍等...");
        HashMap<String, String> map = new HashMap<>();
        //班级
        map.put("trClass", SharedPreUtils.getString(this, "user_grade"));
        //场次
        map.put("trScene", SharedPreUtils.getString(this, "user_sceneid"));
        //桌牌号
        map.put("trUsernum", SharedPreUtils.getString(this, "user_numberplate"));

        Log.i("jtest", "getUserRecored: 请求成绩列表前获取sp  trClass=" + SharedPreUtils.getString(this, "user_grade") + "trUsernum=" + SharedPreUtils.getString(this, "user_numberplate") + "   trScene=" + SharedPreUtils.getString(this, "user_sceneid"));

        Intent intent = new Intent(this, ExamHpptService.class);
        HttpParametersBean httpParametersBean = new HttpParametersBean();
        httpParametersBean.setUrl(AnswerConstants.GET_RECORDE);
        httpParametersBean.setMap(map);
        httpParametersBean.setType(1);
        httpParametersBean.setName("AnswerRecordActivity_GetRecorde");
        intent.putExtra("http_msg", httpParametersBean);
        startService(intent);


    }

    private void initScoreInfo() {
        Intent intent = new Intent(this, ExamHpptService.class);
        HttpParametersBean httpParametersBean = new HttpParametersBean();
        httpParametersBean.setUrl(AnswerConstants.GET_USER_STANDING_LIST);
        httpParametersBean.setMap(null);
        httpParametersBean.setType(1);
        httpParametersBean.setName("AnswerRecordActivity_GetUserStandingList");

        intent.putExtra("http_msg", httpParametersBean);
        startService(intent);


    }

    //正确率?
    private int accuracyNum = 0;

    private void initRecyclerView(List<UserRecoderBean.TestrecordesBean> list) {
        answer_record_rv1.setLayoutManager(new LinearLayoutManager(this));
        answer_record_rv2.setLayoutManager(new LinearLayoutManager(this));
        answer_record_rv3.setLayoutManager(new LinearLayoutManager(this));

        AnswerRecordAdapter1 answerRecordAdapter1 = new AnswerRecordAdapter1(this);
        AnswerRecordAdapter2 answerRecordAdapter2 = new AnswerRecordAdapter2(this);
        AnswerRecodeAdapter3 answerRecodeAdapter3 = new AnswerRecodeAdapter3(this);

        answer_record_rv1.setAdapter(answerRecordAdapter1);
        answer_record_rv2.setAdapter(answerRecordAdapter2);
        answer_record_rv3.setAdapter(answerRecodeAdapter3);

        ArrayList<UserRecoderBean.TestrecordesBean> testrecordesBeans1 = new ArrayList<>();
        ArrayList<UserRecoderBean.TestrecordesBean> testrecordesBeans2 = new ArrayList<>();
        ArrayList<UserRecoderBean.TestrecordesBean> testrecordesBeans3 = new ArrayList<>();


        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTrType() == 1 || list.get(i).getTrType() == 2 || list.get(i).getTrType() == 3 || list.get(i).getTrType() == 4) {
                if ((list.get(i).getTrPapernum() >= 1) && (list.get(i).getTrPapernum() <= 8)) {
                    testrecordesBeans1.add(list.get(i));
                } else if ((list.get(i).getTrPapernum() >= 9) && (list.get(i).getTrPapernum() <= 15)) {
                    testrecordesBeans2.add(list.get(i));
                }
            } else if (list.get(i).getTrType() == 5 || list.get(i).getTrType() == 6) {
                testrecordesBeans3.add(list.get(i));
            }

            if (list.get(i).getTrRight() == 0) {
                accuracyNum++;
            }
        }
        answerRecordAdapter1.setmList(testrecordesBeans1);
        answerRecordAdapter2.setmList(testrecordesBeans2);
        answerRecodeAdapter3.setmList(testrecordesBeans3);

        Log.i("jjjjj", "initRecyclerView: ml1==" + testrecordesBeans1.size() + "    " + testrecordesBeans1.toString());
        Log.i("jjjjj", "initRecyclerView: ml2==" + testrecordesBeans2.size() + "    " + testrecordesBeans2.toString());
        Log.i("jjjjj", "initRecyclerView: ml3==" + testrecordesBeans3.size() + "    " + testrecordesBeans3.toString());


        long timetamp = System.currentTimeMillis();
        String time = format.format(new Date());

        File mFile0 = new File(getExternalCacheDir().getAbsolutePath() + "/crash/list0/" + time + "_" + timetamp + "_log.txt");
        File mFile1 = new File(getExternalCacheDir().getAbsolutePath() + "/crash/list1/" + time + "_" + timetamp + "_log.txt");
        File mFile2 = new File(getExternalCacheDir().getAbsolutePath() + "/crash/list2/" + time + "_" + timetamp + "_log.txt");
        File mFile3 = new File(getExternalCacheDir().getAbsolutePath() + "/crash/list3/" + time + "_" + timetamp + "_log.txt");


        addTxtToFileBuffered(mFile0, "size=" + list.size() + list.toString(), "list0");
        addTxtToFileBuffered(mFile1, "size=" + testrecordesBeans1.size() + testrecordesBeans1.toString(), "list1");
        addTxtToFileBuffered(mFile2, "size=" + testrecordesBeans2.size() + testrecordesBeans2.toString(), "list2");
        addTxtToFileBuffered(mFile3, "size=" + testrecordesBeans3.size() + testrecordesBeans3.toString(), "list3");
        dismissProgressDialog();
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

    @Override
    public void websocketStatusChange(int color) {
        if (mWebsocket_status != null) {
            mWebsocket_status.setBackgroundResource(color);
        }
    }

    @Override
    public void onHttpServiceResultListener(HttpParametersBean httpParametersBean) {
        if (httpParametersBean != null) {
            if ("WaitActivity_SetUserStatus".equals(httpParametersBean.getName())) {
                if (httpParametersBean.getStatus() == 0) {
                    Log.i("jtest", "ConfirmActivity:success: 用户状态更更改为未登录");

                } else if (httpParametersBean.getStatus() == 1) {
                    Log.i("jtest", "ConfirmActivity:success: 用户登录状态更改失败");
                }
            } else if (httpParametersBean.getName().equals("AnswerRecordActivity_GetRecorde")) {
                if (httpParametersBean.getStatus() == 0) {
                    Log.i("jtest", "AnswerRecordActivity:success: 获取用户成绩列表" + httpParametersBean.getResultValue());
                    if (!TextUtils.isEmpty(httpParametersBean.getResultValue())) {
                        List<UserRecoderBean.TestrecordesBean> testrecordes = new Gson().fromJson(httpParametersBean.getResultValue(), UserRecoderBean.class).getTestrecordes();
                        if (testrecordes.size() > 0) {
                            //获取成绩列表 赋值recyclerview 数据
                            initScoreInfo();
                            initRecyclerView(testrecordes);
                            //右边的成绩信息的初复赋值

                            AnswerNum = testrecordes.size();
                        }
                    }

                } else if (httpParametersBean.getStatus() == 1) {
                    mBtnRecordRetry.setVisibility(View.VISIBLE);
                    dismissProgressDialog();
                    Log.i("jtest", "AnswerRecordActivity:fail: 获取用户成绩列表" + httpParametersBean.getResultValue());
                    Toast.makeText(AnswerRecordActivity.this, "获取成绩列表失败,请联系管理员!", Toast.LENGTH_SHORT).show();
                    long timetamp = System.currentTimeMillis();
                    String time = format.format(new Date());
                    File mFile4 = new File(getExternalCacheDir().getAbsolutePath() + "/crash/getUserRecored/" + time + "_" + timetamp + "_log.txt");
                    addTxtToFileBuffered(mFile4, "失败信息=" + httpParametersBean.getResultValue(), "data");
                    initScoreInfo();
                }
            } else if (httpParametersBean.getName().equals("AnswerRecordActivity_GetUserStandingList")) {
                if (httpParametersBean.getStatus() == 0) {
                    Log.i("jtest", "AnswerRecordActivity:success: 获取用户成绩结果" + httpParametersBean.getResultValue());

                    if (!TextUtils.isEmpty(httpParametersBean.getResultValue())) {
                        UserStandingListBean userStandingListBean = new Gson().fromJson(httpParametersBean.getResultValue(), UserStandingListBean.class);
                        List<UserStandingListBean.RankingListBean> rankingList = userStandingListBean.getRankingList();
                        for (int i = 0; i < rankingList.size(); i++) {
                            if (String.valueOf(rankingList.get(i).getTrUsernum()).equals(SharedPreUtils.getString(AnswerRecordActivity.this, "user_numberplate"))) {
                                //时间
                                int timesum = rankingList.get(i).getTimesum();
                                //成绩
                                int marksum = rankingList.get(i).getMarksum();
                                setScore(marksum);
                                //用时
                                mtvtTime.setText(stampToDate(timesum) + "");
                                //正确率
                                mtvAccuracy.setText(accuracyNum + "/" + "20");

                            }
                        }

                    }
                } else if (httpParametersBean.getStatus() == 1) {
                    dismissProgressDialog();
                    mBtnRecordRetry.setVisibility(View.VISIBLE);
                    Log.i("jtest", "AnswerRecordActivity:fail: 获取用户成绩结果" + httpParametersBean.getResultValue());
                    Toast.makeText(AnswerRecordActivity.this, "获取成绩结果失败,请联系管理员!", Toast.LENGTH_SHORT).show();
                    long timetamp = System.currentTimeMillis();
                    String time = format.format(new Date());
                    File mFile5 = new File(getExternalCacheDir().getAbsolutePath() + "/crash/initScoreInfo/" + time + "_" + timetamp + "_log.txt");
                    addTxtToFileBuffered(mFile5, "失败信息=" + httpParametersBean.getResultValue(), "data");
                }

            }
        }

    }

    private void setScore(int marksum) {
        String s1 = String.valueOf(marksum);
        char[] chars1 = s1.toCharArray();
     /*   ArrayList<Character> chars2 = new ArrayList<>();
        for (int i = 0; i < chars1.length; i++) {
            chars2.add(chars1[i]);
        }*/
        Log.i("hhh", "setScore: " + marksum + "  --" + chars1.toString());


        for (int i = 0; i < chars1.length; i++) {
            switch (i) {
                case 0:
                    setScore2(chars1[i], answer_record_score_hundreds);
                    Log.i("hhh", "setScore: " + 0 + "==" + chars1[i]);
                    break;
                case 1:
                    Log.i("hhh", "setScore: " + 1 + "==" + chars1[i]);
                    setScore2(chars1[i], answer_record_score_tens);
                    break;
                case 2:
                    Log.i("hhh", "setScore: " + 2 + "==" + chars1[i]);
                    setScore2(chars1[i], answer_record_score_ones);
                    break;
                default:
                    break;
            }
        }
    }

    private void setScore2(char s, ImageView answer_record_score_img) {
        switch (s) {
            case '0':
                answer_record_score_img.setImageResource(R.drawable.str0);
                break;
            case '1':
                answer_record_score_img.setImageResource(R.drawable.str1);
                break;
            case '2':
                answer_record_score_img.setImageResource(R.drawable.str2);
                break;
            case '3':
                answer_record_score_img.setImageResource(R.drawable.str3);
                break;
            case '4':
                answer_record_score_img.setImageResource(R.drawable.str4);
                break;
            case '5':
                answer_record_score_img.setImageResource(R.drawable.str5);
                break;
            case '6':
                answer_record_score_img.setImageResource(R.drawable.str6);
                break;
            case '7':
                answer_record_score_img.setImageResource(R.drawable.str7);
                break;
            case '8':
                answer_record_score_img.setImageResource(R.drawable.str8);
                break;
            case '9':
                answer_record_score_img.setImageResource(R.drawable.str9);
                break;
            default:
                break;
        }
    }
}
