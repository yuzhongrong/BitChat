package android.com.bitchat.activitys;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.Utils.RxTimerUtil;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.base.AppManager;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.zhl.cbdialog.CBDialogBuilder;

import java.util.concurrent.TimeUnit;

import android.com.bitchat.R;
import android.com.bitchat.bean.HDWallet;
import android.com.bitchat.utils.dialog.LoadDialog;

public class AccountActivity extends BaseActivity {

    private ImageView back;
    private TextView title;
    private TextView address;
    private TextView nickname;
    private HDWallet wallet;
    private Button exitaccount;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_layout;
    }

    @Override
    protected void onInitView(Bundle bundle) {

        back= (ImageView) $(R.id.ic_back);
        back.setVisibility(View.VISIBLE);
        title= (TextView) $(R.id.title);
        title.setText(R.string.str_title_account);
        address= (TextView) $(R.id.address);
        wallet= (HDWallet) ACacheUtil.get(this).getAsObject("wallet");
        address.setText(wallet.getAddress());
        nickname= (TextView) $(R.id.id_name);
        nickname.setText(wallet.getUsername());
        exitaccount= (Button) $(R.id.exitaccount);

    }

    @Override
     protected void onEventListener() {

    }

    @Override
    protected BaseView getView() {
        return null;
    }



    private void showDelIdentify(){

        new CBDialogBuilder(this, R.layout.dialog_input)
                .setTouchOutSideCancelable(true)
                .showCancelButton(true)
                .setTitle(getString(R.string.str_input_pwd))

                .setTitleTextColor(getResources().getColor(R.color.colorPrimary))
                .setConfirmButtonText(getString(R.string.str_ok))
                .setCancelButtonText(getString(R.string.str_cancel))
                .setCancelable(false)
                .showIcon(false)
                .setButtonClickListener(true, new CBDialogBuilder.onDialogbtnClickListener() {
                    @Override
                    public void onDialogbtnClick(Context context, Dialog dialog, int i) {
                        switch (i) {
                            case BUTTON_CONFIRM:
                                EditText editText=dialog.findViewById(R.id.input);

                                if(TextUtils.isEmpty(editText.getText().toString())){

                                    ToastUtil.show(getString(R.string.str_input_pwd));
                                    return;
                                }


                                if(!editText.getText().toString().equals(wallet.getPwd())){

                                    ToastUtil.show(getString(R.string.str_pwd_error));
                                    return;
                                }

                                LoadDialog.show(AccountActivity.this,getString(R.string.str_checking));
                                RxTimerUtil.timer(3, TimeUnit.SECONDS, n->{//增加用户体验


                                    if(wallet!=null){
                                        LoadDialog.dismiss(AccountActivity.this);
                                        if(editText.getText().toString().equals(wallet.getPwd())){
                                            resetBitStore();
                                        }else {
                                            ToastUtil.show(getString(R.string.str_pwd_error));
                                        }

                                    }else{
                                        LoadDialog.dismiss(AccountActivity.this);
                                        ToastUtil.show(getString(R.string.str_reset_now));
                                    }

                                });

                                break;

                        }
                    }
                })
                .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_NORMAL)
                .create().show();

    }

    private void resetBitStore(){

        ACacheUtil.get(this).clear();//clear cache
        AppManager.getInstance().finishAllActivity();//finish all activity
        startActivity(new Intent(this,LoginActivity.class));



    }

}
