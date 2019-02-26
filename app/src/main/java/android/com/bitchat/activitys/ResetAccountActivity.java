package android.com.bitchat.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjwsc.idcm.Utils.ACacheUtil;
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

import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import android.com.bitchat.utils.CusEditText;
import android.com.bitchat.R;
import android.com.bitchat.bean.HDWallet;
import android.com.bitchat.utils.WalletUtil;


public class ResetAccountActivity extends BaseActivity {

    private TextView title;
    private ImageView ic_right;
    private EditText mnemonics;
    private Button action_reset;
    private CusEditText nickname;
    private CusEditText pwd;
    private CusEditText comfirmpwd;
    private final int REQUEST_IMAGE_PICKER_SIMPLE= ImagePicker.RESULT_CODE_ITEMS;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_resetwallet_layout;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        title= (TextView) $(R.id.title);
        ic_right=(ImageView) $(R.id.ic_right);
        title.setText(R.string.str_reset_account);
        ic_right.setImageResource(R.mipmap.scan_white);
        ic_right.setVisibility(View.VISIBLE);
        mnemonics= (EditText) $(R.id.mnemonics);
        action_reset= (Button) $(R.id.action_reset);
        nickname= (CusEditText) $(R.id.nickname);
        pwd=(CusEditText) $(R.id.pwd);
        comfirmpwd=(CusEditText) $(R.id.pwd1);

    }

    @Override
     protected void onEventListener() {
        ic_right.setOnClickListener(v->{
            jump2ScanPage();
        });

        action_reset.setOnClickListener(v->{

            checkParam();

        });
    }



    private void checkParam(){


        if(TextUtils.isEmpty(mnemonics.getText().toString())){
            ToastUtil.show(getString(R.string.str_input_mnemonic));
            return;

        }
        if(TextUtils.isEmpty(nickname.getText().toString())){

            ToastUtil.show(getString(R.string.str_input_nickname));
            return;
        }
        if(TextUtils.isEmpty(pwd.getText().toString())){

            ToastUtil.show(getString(R.string.str_input_pwd));
            return;
        }

        if(TextUtils.isEmpty(comfirmpwd.getText().toString())){

            ToastUtil.show(getString(R.string.str_input_pwd));
            return;
        }
        if(!comfirmpwd.getText().toString().equals(pwd.getText().toString())){
            ToastUtil.show(getString(R.string.str_pwd_not_same));
            return;
        }

        WalletUtil.CreateWallet(nickname.getText().toString(),pwd.getText().toString(),mnemonics.getText().toString().trim() )
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxProgressSubscriber<HDWallet>(this) {
                    @Override
                    public void onSuccess(HDWallet data) {

                        if(data!=null){
                            ToastUtil.show(getString(R.string.str_craate_account_success));
                            ACacheUtil.get(ResetAccountActivity.this).put("wallet",data);
                            Intent intent=new Intent(ResetAccountActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();

                        }

                    }

                    @Override
                    protected void onError(ResponseThrowable ex) {
                        ToastUtil.show(getString(R.string.str_craate_account_fail));

                    }
                });
    }

    @Override
    protected BaseView getView() {
        return null;
    }


    private void jump2ScanPage(){
        //选择二维码图片
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setMultiMode(false);//覆盖application中设置
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);

        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImagePreviewActivity.ISORIGIN,true);//默认选择原图
        startActivityForResult(intent, REQUEST_IMAGE_PICKER_SIMPLE);

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

                                Flowable.just(QRCodeDecoder.syncDecodeQRCode(imageFileSource.getPath()))
                                        .compose(bindToLifecycle())
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new RxProgressSubscriber<String>(this) {
                                            @Override
                                            public void onSuccess(String data) {
                                                Logger.d("-----图片识别成功--->");
                                                if(!TextUtils.isEmpty(data)) mnemonics.setText(data);
                                            }

                                            @Override
                                            protected void onError(ResponseThrowable ex) {
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
