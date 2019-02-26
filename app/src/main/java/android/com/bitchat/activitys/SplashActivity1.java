package android.com.bitchat.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;
import android.com.bitchat.bean.HDWallet;

import android.com.bitchat.R;



public class SplashActivity1 extends BaseActivity {

    private Context context;
    private android.os.Handler handler = new android.os.Handler();



    public void initView() {
        context = this;

        HDWallet wallet= (HDWallet) ACacheUtil.get(this).getAsObject("wallet");

      //  LoginStatus loginStatus= (LoginStatus) ACacheUtil.get(this).getAsObject("loginbean");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(wallet!=null){

                    if(!wallet.isIsbackups()){//在保存钱包过程如果没有校验统统让他从新备份
                        goToBackups();
                        return;

                    }
                    goToMain();

                }else{

                    goToLogin();
                }
            }
        },3000);


    }

    private void goToBackups() {

        startActivity(new Intent(context, BackupsActivity.class));
        finish();
    }


    private void goToMain() {
        finish();
        startActivity(new Intent(context,MainActivity.class));

    }

    private void goToLogin() {
        //startActivity(new Intent(context, LoginActivity.class));
        finish();
        startActivity(new Intent(context, LoginActivity.class));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash_layout;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        initView();
    }

    @Override
    protected void onEventListener() {

    }




    @Override
    protected BaseView getView() {
        return null;
    }
}
