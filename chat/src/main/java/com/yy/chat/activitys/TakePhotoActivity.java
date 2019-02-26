package com.yy.chat.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;

import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.widget.JCameraView;
import com.yy.chat.R;
import com.yy.chat.contasts.AppConst;
import com.cjwsc.idcm.Utils.RxTimerUtil;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.base.BaseView;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;


/**
 * @创建者 CSDN_LQR
 * @描述 拍照界面
 */
public class TakePhotoActivity extends BaseActivity {


    private JCameraView mJCameraView;




    @PermissionSuccess(requestCode = 100)
    public void permissionSuccess() {
//        UIUtils.postTaskDelay(() -> {
//            recreate();
////            Intent intent = getIntent();
////            finish();
////            startActivity(intent);
//        }, 500);

        RxTimerUtil.timer(500, TimeUnit.MILLISECONDS,n->{recreate();});

//        UIUtils.postTaskSafely(() -> recreate());
    }

    @PermissionFail(requestCode = 100)
    public void permissionFail() {
       ToastUtil.show(getString(R.string.str_camera_permission_fail));
    }

    @Override
    protected int getLayoutId() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            PermissionGen.with(this)
                    .addRequestCode(100)
                    .permissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                    .request();

         return R.layout.activity_take_photo;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        mJCameraView = (JCameraView) findViewById(R.id.cameraview);
        //(0.0.7+)设置视频保存路径（如果不设置默认为Environment.getExternalStorageDirectory().getPath()）
        mJCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath());
        //(0.0.8+)设置手动/自动对焦，默认为自动对焦
        mJCameraView.setAutoFoucs(false);
        //设置小视频保存路径
        File file = new File(AppConst.VIDEO_SAVE_DIR);
        if (!file.exists())
            file.mkdirs();
        mJCameraView.setSaveVideoPath(AppConst.VIDEO_SAVE_DIR);
    }

    @Override
     protected void onEventListener() {


        mJCameraView.setCameraViewListener(new JCameraView.CameraViewListener() {
            @Override
            public void quit() {
                //返回按钮的点击时间监听
                finish();
            }

            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取到拍照成功后返回的Bitmap
                String path = saveBitmap(bitmap, AppConst.PHOTO_SAVE_DIR);
                Intent data = new Intent();
                data.putExtra("take_photo", true);
                data.putExtra("path", path);
                setResult(Activity.RESULT_OK, data);
                finish();
            }

            @Override
            public void recordSuccess(String url) {
                //获取成功录像后的视频路径
                Intent data = new Intent();
                data.putExtra("take_photo", false);
                data.putExtra("path", url);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected BaseView getView() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mJCameraView != null)
            mJCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mJCameraView != null)
            mJCameraView.onPause();
    }


    public String saveBitmap(Bitmap bm, String dir) {
        String path = "";
        File f = new File(dir, "CSDN_LQR_" + SystemClock.currentThreadTimeMillis() + ".png");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            path = f.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
