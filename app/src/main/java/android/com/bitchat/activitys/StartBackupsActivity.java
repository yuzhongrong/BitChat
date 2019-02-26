package android.com.bitchat.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.Utils.UIUtils;
import com.cjwsc.idcm.Utils.image.SaveImgUtil;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;
import com.cjwsc.idcm.net.callback.RxProgressSubscriber;
import com.cjwsc.idcm.net.exception.ResponseThrowable;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import android.com.bitchat.R;
import android.com.bitchat.bean.HDWallet;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class StartBackupsActivity extends BaseActivity implements View.OnClickListener {
    private TextView mnemonics;
    private Button start_backups;
    private ImageView code;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_backup_layout;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        mnemonics=(TextView)$(R.id.mnemonics);
        start_backups= (Button) $(R.id.start_backups);
        code=(ImageView) $(R.id.code);
        HDWallet wallet= (HDWallet) ACacheUtil.get(this).getAsObject("wallet");
        if(mnemonics!=null){
            mnemonics.setText(wallet.getMnemonic());
        }
    }

    @Override
     protected void onEventListener() {

        start_backups.setOnClickListener(this);

    }

    @Override
    protected BaseView getView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.start_backups:

                new RxPermissions(this).requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Permission>() {
                            @Override
                            public void accept(com.tbruyelle.rxpermissions2.Permission permission) throws Exception {
                                if(permission.granted){

                                    saveImg2Gallery();


                                }else if (permission.shouldShowRequestPermissionRationale) {
                                    // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                                    Logger.d("----拒绝访问-------->");
                                    return;
                                } else {
                                    // 用户拒绝了该权限，并且选中『不再询问』
                                    Logger.d("----拒绝访问-------->");
                                    return;
                                }


                            }
                        });


                break;

            case 0x111:
                startActivity(new Intent(StartBackupsActivity.this,VerifyActivity.class));
                finish();
                break;

        }
    }




    private void saveImg2Gallery(){

        Flowable.just("")
                .compose(bindToLifecycle())
                .observeOn(Schedulers.io())
                .map(new Function<String, Bitmap>() {


                    @Override
                    public Bitmap apply(String s) throws Exception {
                        return QRCodeEncoder.syncEncodeQRCode(mnemonics.getText().toString(), (int) UIUtils.dp2px(200));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxProgressSubscriber<Bitmap>(this) {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onSuccess(Bitmap data) {

                        if(data!=null){
                             SaveImgUtil.saveBitmap(StartBackupsActivity.this,data,System.currentTimeMillis()+"");

                            code.setImageBitmap(data);
                            start_backups.setText(getString(R.string.str_next));
                            start_backups.setId(0x111);
                        }
                    }

                    @Override
                    protected void onError(ResponseThrowable ex) {
                     //   ToastUtil.show(getString(R.string.str_code_fail));
                    }
                });


    }
}
