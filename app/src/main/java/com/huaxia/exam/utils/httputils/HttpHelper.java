package com.huaxia.exam.utils.httputils;

import android.content.Context;
import android.util.Log;

import com.huaxia.exam.utils.AnswerConstants;
import com.huaxia.exam.utils.SharedPreUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * author:AbnerMing
 * date:2018/11/7
 * 联网工具类
 */
public class HttpHelper {


/*    private int retryDelaySeconds = 3;//延迟重试的时间
    private int retryCount;//记录当前重试次数
    private int retryCountMax = 3;//最大重试次数*/


    private BaseService mBaseService;
    private Observable<ResponseBody> mObservable;
    private Context context;
    private SimpleDateFormat format = new java.text.SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    Interceptor myInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            request = chain.request();
            //在这里获取到request后就可以做任何事情了
            Log.i("jhtto", "intercept: " + request.toString());
            File mFile0 = new File(context.getExternalCacheDir().getAbsolutePath() + "/crash/HttpLog/" + "http" + "_log.txt");
            String time = format.format(new Date());
            addTxtToFileBuffered(mFile0, time + "------" + request.toString(), "jhttp");

            Response response = chain.proceed(request);
            return response;
        }
    };
    private OkHttpClient build = new OkHttpClient().newBuilder()
            .addInterceptor(myInterceptor)//添加自定义的拦截器
            .connectTimeout(10, TimeUnit.SECONDS)//设置连接超时时间
            .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
            .build();
    private Request request;


    public HttpHelper(Context context) {
        this.context = context;
        Retrofit mRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://" + SharedPreUtils.getString(context, "ip") + ":" + AnswerConstants.PORT + "/")
                //.baseUrl("http://" + "192.168.169.101" + ":" + "8089" + "/")
                //todo 2019年5月25日 15:25:24  添加了连接超时  未测试
                .client(build)
                .build();

        mBaseService = mRetrofit.create(BaseService.class);
    }


    public HttpHelper(Context context, String BaseUrl) {
        this.context = context;

        Retrofit mRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BaseUrl)
                //todo 2019年5月25日 15:25:24  添加了连接超时  未测试
                .client(build)
                .build();

        mBaseService = mRetrofit.create(BaseService.class);
    }

    //get请求
    public HttpHelper get(String url, Map<String, String> map) {

        if (map == null) {
            map = new HashMap<>();
        }
        mObservable = mBaseService.get(url, map);
        setObservable();

        return this;
    }

    //post请求
    public HttpHelper post(String url, Map<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        mObservable = mBaseService.post(url, map);
        setObservable();
        return this;
    }

    /**
     * 2018年12月18日 14:01:12
     * 焦浩康
     * 上传头像
     *
     * @param url
     * @param map
     * @param part
     * @return
     */
    public HttpHelper part(String url, Map<String, String> map, MultipartBody.Part part) {

        if (map == null) {
            map = new HashMap<>();
        }
        mObservable = mBaseService.part(url, map, part);
        setObservable();
        return this;


    }


    //设置被观察者
    private void setObservable() {
        mObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {

                    private int mRetryCount = 1;

                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {

                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                String errorMsg = throwable.getMessage();
                                long waitTime = 0;
                                /*switch (errorMsg) {
                                    case MSG_WAIT_SHORT:
                                        waitTime = 2000;
                                        break;
                                    case MSG_WAIT_LONG:
                                        waitTime = 4000;
                                        break;
                                    default:
                                        break;
                                }*/
                                if (errorMsg != null) {
                                    waitTime = 1000;
                                }
                                Log.d("jtest", "发生错误，尝试等待时间=" + waitTime + ",当前重试次数=" + mRetryCount);
                                mRetryCount++;
                                return waitTime > 0 && mRetryCount <= 3 ? Observable.timer(waitTime, TimeUnit.MILLISECONDS) : Observable.error(throwable);
                            }

                        });
                    }

                }).subscribe(observer);
    }

    private Observer observer = new Observer<ResponseBody>() {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String data = responseBody.string();
                listener.success(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {

            Log.i("jtest", "onError: " + e.getMessage());
           /* //没超过最大重试次数的话则进行重试
            if (++retryCount <= retryCountMax) {
                //延迟retryDelaySeconds后开始重试
                Log.i("jtest00", "onError: 重试次数" + retryCount);
                Observable.timer(retryDelaySeconds, TimeUnit.SECONDS);
            } else {*/

            //记录错误请求
            File mFile0 = new File(context.getExternalCacheDir().getAbsolutePath() + "/crash/HttpLog/" + "httpError" + "_log.txt");
            String time = format.format(new Date());
            addTxtToFileBuffered(mFile0, time + "------" + request.toString(), "jhttp");

            String error = e.getMessage();
            listener.fail(error);
            /*   }*/

        }

        @Override
        public void onComplete() {

        }


    };


    //接口回调
    private HttpListener listener;

    public HttpHelper result(HttpListener listener) {
        this.listener = listener;
        return this;
    }

    public interface HttpListener {
        void success(String data);

        void fail(String data);

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

}
