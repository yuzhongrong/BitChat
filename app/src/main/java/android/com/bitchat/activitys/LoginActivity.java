package android.com.bitchat.activitys;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import android.com.bitchat.R;

import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_layout;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onInitView(Bundle bundle) {

      $(R.id.createaccount).setOnClickListener(this::onClick);
      $(R.id.resetaccount).setOnClickListener(this::onClick);



    }

    @Override
     protected void onEventListener() {

    }

    @Override
    protected BaseView getView() {
        return null;
    }

    private void onClick(View v){

        switch (v.getId()){

            case R.id.createaccount:
                startActivity(new Intent(this,RegisterActivity.class));
                break;

            case R.id.resetaccount:
                startActivity(new Intent(this,ResetAccountActivity.class));

                break;

        }


    }
}
