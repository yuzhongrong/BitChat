package android.com.bitchat.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.base.AppManager;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;
import com.cjwsc.idcm.constant.AcacheKeys;
import com.cjwsc.idcm.language.LanguageSettingBean;
import com.cjwsc.idcm.language.LanguageUtil;
import com.lqr.adapter.LQRAdapterForRecyclerView;
import com.lqr.adapter.LQRViewHolderForRecyclerView;
import com.lqr.recyclerview.LQRRecyclerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Locale;

import android.com.bitchat.R;
import android.com.bitchat.adapters.LanguageSeletorAdapter;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：hxy
 * 邮箱：hua.xiangyang@shuweikeji.com
 * 版本号：1.0
 * 项目包名：FoxIDCW com.idcg.idcw.activitys
 * 备注消息：
 * 修改时间：2018/3/17 16:46
 **/

public class LanguageActivity extends BaseActivity {


    private TextView title;
    private LQRRecyclerView mLanguageSelet;
    SmartRefreshLayout smartRefreshLayout;

    private LanguageSeletorAdapter mLanguageSeletorAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_language;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        mLanguageSelet= (LQRRecyclerView) $(R.id.rv_language_list);
        title= (TextView) $(R.id.title);
        smartRefreshLayout= (SmartRefreshLayout) $(R.id.smartrefreshlayout);
        title.setText(R.string.str_language);
        smartRefreshLayout.setEnableLoadmore(false);
        smartRefreshLayout.setEnableRefresh(false);


        mLanguageSeletorAdapter = new LanguageSeletorAdapter(LanguageUtil.mArrayList);
        mLanguageSelet.setAdapter(mLanguageSeletorAdapter);
        LanguageSettingBean languageSettingBean = (LanguageSettingBean) ACacheUtil.get(this).getAsObject(AcacheKeys.LANGUAGELOCALE);
        if (languageSettingBean != null) {
            Locale locale = languageSettingBean.getLocale();
            int position = LanguageUtil.getPositionForLocale(locale);
            if (mCtx.getResources().getConfiguration().locale.toString().equals("zh_TW") ||
                    mCtx.getResources().getConfiguration().locale.toString().equals("zh_MO_#Hant") ||
                    mCtx.getResources().getConfiguration().locale.toString().equals("zh_HK_#Hant") ||
                    mCtx.getResources().getConfiguration().locale.toString().equals("zh_TW_#Hant") ||
                    mCtx.getResources().getConfiguration().locale.toString().equals("zh_HK") ||
                    mCtx.getResources().getConfiguration().locale.toString().equals("zh_MO")) {//zh_TW_#Hant
                mLanguageSeletorAdapter.setCurrentPosition(2);
                return;
            }
            mLanguageSeletorAdapter.setCurrentPosition(position);
        }


    }


    @Override
     protected void onEventListener() {
        mLanguageSelet.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

//                if(position==0||position==1||position==2||position==3||position==4||position==LanguageUtil.mArrayList.size()-1){

                    mLanguageSeletorAdapter.setCurrentPosition(position);
                    LanguageSettingBean languageSettingBean = LanguageUtil.mArrayList.get(position);
                    if (languageSettingBean != null) {
                        ACacheUtil.get(LanguageActivity.this).put(AcacheKeys.LANGUAGELOCALE,
                                new LanguageSettingBean(languageSettingBean.getName(), languageSettingBean.getLocale()));
                    }
                    //设置应用语言类型
                    LanguageUtil.initAppLanguage(LanguageActivity.this);
                    AppManager.getInstance().finishAllActivity();
                    Intent intent = new Intent(LanguageActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("language", "language");
                    intent.putExtra("Language", bundle);
                    startActivity(intent);
//                }


            }
        });
    }



    @Override
    protected BaseView getView() {
        return null;
    }
}
