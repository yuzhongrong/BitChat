package android.com.bitchat.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;

import android.com.bitchat.R;

public class BackupsActivity extends BaseActivity implements View.OnClickListener {

    private Button start_backups;
    private TextView backup_txt;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_backup_layout;
    }

    @Override
    protected void onInitView(Bundle bundle) {

        start_backups=(Button)$(R.id.start_backups);
        backup_txt=(TextView)$(R.id.backup_txt);
        backup_txt.setText(getResources().getString(R.string.str_BackUps));
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
                startBackups();
                break;

        }
    }

    private void startBackups() {

      startActivity(new Intent(BackupsActivity.this,StartBackupsActivity.class));
      finish();

    }

    @Override
     protected void onEventListener() {

    }


}
