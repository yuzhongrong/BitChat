package android.com.bitchat.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.Utils.RxTimerUtil;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.Utils.image.ImageUtils;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;
import com.cjwsc.idcm.net.callback.RxProgressSubscriber;
import com.cjwsc.idcm.net.exception.ResponseThrowable;
import com.lqr.imagepicker.ImagePicker;
import com.lqr.imagepicker.bean.ImageItem;
import com.lqr.imagepicker.ui.ImageGridActivity;
import com.lqr.imagepicker.ui.ImagePreviewActivity;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.com.bitchat.R;
import android.com.bitchat.bean.HDWallet;
import android.com.bitchat.utils.dialog.LoadDialog;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class VerifyActivity extends BaseActivity implements View.OnClickListener{
    private TextView recycler1;
    private Button select_img;
    private Button finish;
    private final int REQUEST_IMAGE_PICKER_SIMPLE= ImagePicker.RESULT_CODE_ITEMS;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_backups_verify_layout;


    }

    @Override
    protected void onInitView(Bundle bundle) {
        recycler1= (TextView) $(R.id.recycler1);
        select_img= (Button) $(R.id.select_img);
        finish=(Button) $(R.id.finish);

    }

    @Override
     protected void onEventListener() {
        select_img.setOnClickListener(this);
        finish.setOnClickListener(this);
    }

    @Override
    protected BaseView getView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.select_img:
                //选择二维码图片
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setMultiMode(false);//覆盖application中设置
                imagePicker.setShowCamera(false);  //显示拍照按钮
                imagePicker.setCrop(false);

                Intent intent = new Intent(this, ImageGridActivity.class);
                intent.putExtra(ImagePreviewActivity.ISORIGIN,true);//默认选择原图
                 startActivityForResult(intent, REQUEST_IMAGE_PICKER_SIMPLE);
                break;

            case R.id.finish:
                if(TextUtils.isEmpty(recycler1.getText().toString())){
                    ToastUtil.show(getString(R.string.str_record_mnemonic));
                    return;
                }

                LoadDialog.show(this,getString(R.string.str_checking));
                RxTimerUtil.timer(3, TimeUnit.SECONDS, n->{//增加用户体验

                    HDWallet hdWallet= (HDWallet) ACacheUtil.get(this).getAsObject("wallet");
                    if(hdWallet!=null&&(recycler1.getText().toString().equals(hdWallet.getMnemonic()))){
                        LoadDialog.dismiss(this);
                        //保存校验状态
                        hdWallet.setIsbackups(true).save(this);
                        startActivity(new Intent(this,MainActivity.class));
                        finish();
                    }else{
                        LoadDialog.dismiss(this);
                      //  NToast.shortToast(this,"校验失败，助记词不正确");
                        ToastUtil.show(getString(R.string.str_check_faile));



                    }

                });




                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (resultCode){

           case REQUEST_IMAGE_PICKER_SIMPLE:
               if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {//返回单张或多张照片
                   if (data != null) {
                       //是否发送原图
                       boolean isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
                       ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                       Log.e("CSDN_LQR", isOrig ? "发原图" : "不发原图");//若不发原图的话，需要在自己在项目中做好压缩图片算法
                       for (ImageItem imageItem : images) {
                           File imageFileThumb;
                           File imageFileSource;
                           if (isOrig) {
                               imageFileSource = new File(imageItem.path);
                               imageFileThumb = ImageUtils.genThumbImgFile(imageItem.path);

                           } else {
                               //压缩图片
                               imageFileSource = ImageUtils.genThumbImgFile(imageItem.path);
                               imageFileThumb = ImageUtils.genThumbImgFile(imageFileSource.getAbsolutePath());
                           }
//                           if (imageFileSource != null && imageFileSource != null)
//                               mPresenter.sendImgMsg(imageFileThumb, imageFileSource);

                           if(imageFileSource!=null){

                               Flowable.just("")
                                       .compose(bindToLifecycle())

                                       .observeOn(Schedulers.io())
                                       .map(new Function<String, String>() {


                                           @Override
                                           public String apply(String s) throws Exception {
                                               return QRCodeDecoder.syncDecodeQRCode(imageFileSource.getPath());
                                           }
                                       })
                                       .observeOn(AndroidSchedulers.mainThread())
                                       .subscribe(new RxProgressSubscriber<String>(this) {
                                           @Override
                                           public void onSuccess(String data) {
                                               Logger.d("-----图片识别成功--->");
                                               if(!TextUtils.isEmpty(data)) recycler1.setText(data);
                                           }

                                           @Override
                                           protected void onError(ResponseThrowable ex) {

                                               if(ex.getErrorCode().equals("1002"))return;
                                               Logger.d("-----图片识别失败--->"+ex.getErrorMsg());
                                               ToastUtil.show(getString(R.string.str_scan_faile));

                                           }
                                       });
                           }

                       }





                   }
               }
               break;



       }
    }
}
