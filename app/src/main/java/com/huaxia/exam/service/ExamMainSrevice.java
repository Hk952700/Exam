package com.huaxia.exam.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.huaxia.exam.utils.AnswerConstants;
import com.huaxia.exam.utils.SharedPreUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 2019年4月12日 09:53:38
 * jiao hao kang
 * 应用总服务  主要功能为  连接服务器  接受和发送到服务器
 */
public class ExamMainSrevice extends Service {

    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private WebSocketClient mWebSocketClient;
    private boolean isUserClose = false;
    private boolean isFirstConnect = true;
    private boolean isOpen = false;
    private URI uri;
    private File mFile;
    private int testCount = 0;
    private SimpleDateFormat format = new java.text.SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");// 用于格式化日期,作为日志文件名的一部分

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("jtest", "onCreate: 服务启动");
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.huaxian.ActivityBroadcastReceiver");
        localReceiver = new LocalReceiver();
        //注册本地接收器
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);


        //创建一个文件对象
        long timetamp = System.currentTimeMillis();
        String time = format.format(new Date());
        mFile = new File(getExternalCacheDir().getAbsolutePath() + "/crash/socketLog/" + "client_" + time + "_" + timetamp + "_log.txt");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("jtest", "onStartCommand: intent" + intent + "  flags" + flags + "  startId" + startId);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isUserClose = true;
        if (mWebSocketClient != null) {
            if (mWebSocketClient.isOpen()) {
                mWebSocketClient.close();
                Log.i("jtest", "onDestroy:websocket连接关闭");
            }
            mWebSocketClient = null;
        }
        localBroadcastManager.unregisterReceiver(localReceiver);
        Log.i("jtest", "onDestroy:服务关闭");
    }

    private int connectCount = 0;

    private void LoginUser(final URI uri) {
        this.uri = uri;
        Log.i("jtest", "ExamMainSrevice:LoginUser: ============================" + uri.toString());

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                int socket_connect_count = SharedPreUtils.getInt(getApplicationContext(), "socket_connect_count");
                socket_connect_count++;
                Log.i("jiao", "onOpen: " + socket_connect_count);
                SharedPreUtils.put(getApplicationContext(), "socket_connect_count", socket_connect_count);
                Intent intent6 = new Intent("com.huaxian.ServiceBroadcastReceiver");
                intent6.putExtra("websocket_status", 1);


                addTxtToFileBuffered(mFile, "onOpen  :" + format.format(new Date()) + "connectCount=" + connectCount + "   isOpen=" + isOpen + "   canCloseConnect=" + canCloseConnect + "    handshakedata.getHttpStatus=" + handshakedata.getHttpStatus() + "  handshakedata.getHttpStatusMessage=" + handshakedata.getHttpStatusMessage() + "   isUserClose=" + isUserClose, "Open");
                localBroadcastManager.sendBroadcast(intent6);
                isUserClose = false;
                connectCount = 0;
                isOpen = true;
                canCloseConnect = true;

                Log.i("jtest", "ExamMainSrevice:onOpen: ");
                /*Intent intent = new Intent("com.huaxian.ServiceBroadcastReceiver");
                Message obtain = Message.obtain();
                obtain.what = 1;
                obtain.obj = "service_connected";
                intent.putExtra("service_data", obtain);
                localBroadcastManager.sendBroadcast(intent);*/

            }

            @Override
            public void onMessage(String message) {
                testCount++;
                Intent intent5 = new Intent("com.huaxian.ServiceBroadcastReceiver");
                intent5.putExtra("websocket_status", 2);


                addTxtToFileBuffered(mFile, "onMessage  :" + format.format(new Date()) + "connectCount=" + connectCount + "   isOpen=" + isOpen + "   canCloseConnect=" + canCloseConnect + "    message=" + message + "   isUserClose=" + isUserClose, "Message");
                localBroadcastManager.sendBroadcast(intent5);
                Log.i("jtest", "服务端接受到websocket信息:ExamMainSrevice:onMessage:= " + message);
                if (!TextUtils.isEmpty(message)) {
                    if (isFirstConnect && message.equals("onOpen suceeses!")) {
                        Intent intent = new Intent("com.huaxian.ServiceBroadcastReceiver");
                        intent.putExtra("service_data", message);
                        localBroadcastManager.sendBroadcast(intent);
                        isFirstConnect = false;
                    } else {
                        Intent intent = new Intent("com.huaxian.ServiceBroadcastReceiver");
                        intent.putExtra("service_data", message);
                        localBroadcastManager.sendBroadcast(intent);
                    }

                }


            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Intent intent = new Intent("com.huaxian.ServiceBroadcastReceiver");
                intent.putExtra("websocket_status", -1);
                addTxtToFileBuffered(mFile, "onClose  :" + format.format(new Date()) + "connectCount=" + connectCount + "   isOpen=" + isOpen + "   canCloseConnect=" + canCloseConnect + "    code=" + code + "   reason=" + reason + "   remote=" + remote + "   isUserClose=" + isUserClose, "Close");
                localBroadcastManager.sendBroadcast(intent);

                isOpen = false;
                Log.i("jtest", "ExamMainSrevice:onClose: code=" + code + ":reason=" + reason + ":remote=" + remote);
                if (!isUserClose) {
                    canCloseConnect = true;
                    reConnect(mWebSocketClient, true, false);
                }


            }


            @Override
            public void onError(Exception ex) {
                Intent intent3 = new Intent("com.huaxian.ServiceBroadcastReceiver");
                intent3.putExtra("websocket_status", 0);


                addTxtToFileBuffered(mFile, "onError  :" + format.format(new Date()) + "connectCount=" + connectCount + "   isOpen=" + isOpen + "   canCloseConnect=" + canCloseConnect + "    ex=" + ex.toString() + "   isUserClose=" + isUserClose, "Error");
                Log.i("jtest", "ExamMainSrevice:onError: " + ex.getMessage());
                Intent intent = new Intent("com.huaxian.ServiceBroadcastReceiver");
                   /* Message obtain = Message.obtain();
                    obtain.what = 0;
                    obtain.obj = message;*/
                intent.putExtra("service_data", "onError");
                localBroadcastManager.sendBroadcast(intent);
                isOpen = false;
                reConnect(mWebSocketClient, false, true);

                localBroadcastManager.sendBroadcast(intent3);
            }
        };

        mWebSocketClient.connect();
    }


    public class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Message msg = (Message) intent.getParcelableExtra("msg");
            Log.i("jtest", "服务接受广播onReceive: msg.what=" + msg.what);
            if (msg != null) {
                switch (msg.what) {
                    case 9:  //用户关闭WebSocket连接
                        if (mWebSocketClient != null) {
                            isUserClose = true;
                            canCloseConnect = false;
                            mWebSocketClient.close();
                            mWebSocketClient = null;

                        }
                        break;
                    case 1:
                        //和服务端建立WebSocket连接
                        Log.i("jtest", "ExamMainSrevice:onReceive:和服务端建立WebSocket连接:" + msg.obj);
                        LoginUser(URI.create(msg.obj.toString()));
                        break;

                    case 2:
                        //发送消息
                        if (mWebSocketClient != null) {
                            mWebSocketClient.send(msg.obj.toString());
                            Log.i("jtest", "ExamMainSrevice:onReceive:要发送:" + msg.obj.toString());
                        }
                        break;
                    case AnswerConstants.MESSAGE_TYPE_CLOSE_CONNECTION:
                        //和服务器断开连接
                        //TODO 和服务器断开连接
                        mWebSocketClient.close();
                        break;
                    default:
                        break;
                }
            }

        }
    }

    /**
     * @param webSocketClient
     * @param isImproperClose 是否是非正常关闭
     * @param isError         是否是异常错误
     */
    private boolean canCloseConnect = false;

    private void reConnect(final WebSocketClient webSocketClient, boolean isImproperClose, boolean isError) {
        if (isError) {
            if (connectCount < 5) {
                if (isOpen) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            try {
                                sleep(connectCount * 500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            webSocketClient.reconnect();

                        }
                    }.start();

                    addTxtToFileBuffered(mFile, "reConnect  :" + format.format(new Date()) + "connectCount=" + connectCount + "   isOpen=" + isOpen + "   canCloseConnect=" + canCloseConnect + " isError=" + isError + "   isImproperClose=" + isImproperClose + "   isUserClose=" + isUserClose, "reConnect  Error");
                }
                Log.i("jtest", "onError: 连接次数" + connectCount);

                connectCount++;
            } else {
                reConnect(webSocketClient, true, false);
            }

        } else if (isImproperClose) {
            if (uri != null && canCloseConnect) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(1 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.sendEmptyMessage(0);

                    }
                }.start();
                addTxtToFileBuffered(mFile, "reConnect  :" + format.format(new Date()) + "connectCount=" + connectCount + "   isOpen=" + isOpen + "   canCloseConnect=" + canCloseConnect + " isError=" + isError + "   isImproperClose=" + isImproperClose + "   isUserClose=" + isUserClose, "reConnect  Close");
                Log.i("jtest", "reConnect: Close 重新连接中..");
            }

        }


    }


    private MyHandler handler = new MyHandler(ExamMainSrevice.this);

    /**
     * Handler 内部类(防内存泄露)
     */
    private class MyHandler extends Handler {
        // 弱引用 ，防止内存泄露
        private WeakReference<ExamMainSrevice> weakReference;

        public MyHandler(ExamMainSrevice handlerMemoryActivity) {
            weakReference = new WeakReference<ExamMainSrevice>(handlerMemoryActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 通过  软引用  看能否得到
            ExamMainSrevice handlerMemoryActivity = weakReference.get();
            // 防止内存泄露
            if (handlerMemoryActivity != null) {
                switch (msg.what) {
                    case 0:

                        if (mWebSocketClient != null) {
                            if (mWebSocketClient.isOpen()) {
                                mWebSocketClient.close();
                            }
                            mWebSocketClient = null;
                        }

                        canCloseConnect = false;
                        LoginUser(uri);
                        break;
                    default:
                        break;
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
        Log.i("jtest", "addTxtToFileBuffered: " + testCount);
        Log.i("jtest", "addTxtToFileBuffered: " + logMsg + "写入完成!");
    }

}