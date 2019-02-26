package android.com.bitchat.activitys;

import android.com.bitchat.R;
import android.os.Bundle;

import com.cjwsc.idcm.Utils.RxTimerUtil;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;
import com.orhanobut.logger.Logger;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.concurrent.TimeUnit;

public class TestEventBusActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_layout;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        RxTimerUtil.timer(4, TimeUnit.SECONDS,n->{

            EventBus.getDefault().post("222","ACTION_UPDATE_RECEIVE_MESSAGE");

        });


    }

    @Override
     protected void onEventListener() {

    }

    @Override
    protected BaseView getView() {
        return null;
    }

    @Subscriber(tag = "ACTION_UPDATE_RECEIVE_MESSAGE")
    public void onUpdateTest(String test){

        Logger.d("----测试ok能收到------->");
    }

}
