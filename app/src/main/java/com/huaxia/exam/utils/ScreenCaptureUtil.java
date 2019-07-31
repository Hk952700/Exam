package com.huaxia.exam.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 截屏工具类
 */
public class ScreenCaptureUtil {


    /**
     * 获取整个窗口的截图
     *
     * @param context
     * @return
     */
    @SuppressLint("NewApi")
    public Bitmap captureScreen(Activity context) {
        View cv = context.getWindow().getDecorView();
        cv.setDrawingCacheEnabled(true);
        cv.buildDrawingCache();
        Bitmap bmp = cv.getDrawingCache();
        if (bmp == null) {
            return null;
        }
        bmp.setHasAlpha(false);
        bmp.prepareToDraw();
        return bmp;
    }


    public void saveImageToGallery(Activity context, Bitmap bmp) {
        //  String local_path = Parser.getSdCard() + Const.PATH + Const.IMGAGE_PATH ;
        // 创建文件夹

        SimpleDateFormat format = new java.text.SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        long timetamp = System.currentTimeMillis();
        String time = format.format(new Date());


        File appDir = new File(Environment.getExternalStorageDirectory(), "imageok");
        // File appDir = new File(local_path, "imageok");
        //判断不存在就创建
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //以时间命名
        //String fileName = System.currentTimeMillis() + ".png";
        String fileName = time + timetamp + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        String path = Environment.getExternalStorageDirectory().getPath();
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }


}
