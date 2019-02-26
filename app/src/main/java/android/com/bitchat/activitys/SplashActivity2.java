package android.com.bitchat.activitys;

import android.com.bitchat.R;
import android.com.bitchat.bean.HDWallet;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;


public class SplashActivity2 extends BaseActivity {

    private Context context;
    private android.os.Handler handler = new android.os.Handler();



    public void initView() {
        context = this;

      //  LoginStatus loginStatus= (LoginStatus) ACacheUtil.get(this).getAsObject("loginbean");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              goToMain();
            }
        },3000);


    }

    private void goToMain() {
        finish();
        startActivity(new Intent(context,MainActivity.class));

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
