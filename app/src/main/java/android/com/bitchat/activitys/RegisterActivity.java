package android.com.bitchat.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;
import com.cjwsc.idcm.net.callback.RxProgressSubscriber;
import com.cjwsc.idcm.net.exception.ResponseThrowable;

import android.com.bitchat.R;
import android.com.bitchat.bean.HDWallet;
import android.com.bitchat.utils.CusEditText;
import android.com.bitchat.utils.WalletUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private CusEditText nickname;
    private CusEditText pwd;
    private CusEditText pwd1;
    private Button action_create;







    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.action_create:
                checkParam();
                break;


        }
    }

    private void createWallet() {

        WalletUtil.CreateWallet(nickname.getText().toString(),pwd.getText().toString(), WalletUtil.generateMnemonic())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxProgressSubscriber<HDWallet>(this) {
                    @Override
                    public void onSuccess(HDWallet data) {

                        if(data!=null){
                            ToastUtil.show(getString(R.string.str_craate_account_success));


                            ACacheUtil.get(RegisterActivity.this).put("wallet",data);
                            Intent intent=new Intent(RegisterActivity.this,BackupsActivity.class);
                            startActivity(intent);

                        }

                    }

                    @Override
                    protected void onError(ResponseThrowable ex) {
                      //  ToastUtil.show(getString(R.string.str_craate_account_fail));

                    }
                });


    }


    private void checkParam(){


        if(TextUtils.isEmpty(nickname.getText().toString())){

            ToastUtil.show(getString(R.string.str_input_nickname));
            return;
        }
        if(TextUtils.isEmpty(pwd.getText().toString())){

            ToastUtil.show(getString(R.string.str_input_pwd));
            return;
        }

        if(TextUtils.isEmpty(pwd1.getText().toString())){

            ToastUtil.show(getString(R.string.str_input_pwd));
            return;
        }
        if(!pwd1.getText().toString().equals(pwd.getText().toString())){
            ToastUtil.show(getString(R.string.str_pwd_not_same));
            return;
        }

        createWallet();//创建--->备份--->进入主页


    }



    @Override
    protected int getLayoutId() {
        return R.layout.activity_createwallet_layout;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        nickname=(CusEditText) findViewById(R.id.nickname);
        pwd=(CusEditText) findViewById(R.id.pwd);
        pwd1=(CusEditText) findViewById(R.id.pwd1);
        action_create=(Button) findViewById(R.id.action_create);
        action_create.setOnClickListener(this);
    }

    @Override
     protected void onEventListener() {

    }


    @Override
    protected BaseView getView() {
        return null;
    }
}
