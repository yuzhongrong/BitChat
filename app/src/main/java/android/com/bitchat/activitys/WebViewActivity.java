package android.com.bitchat.activitys;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;

import android.com.bitchat.R;

/**
 * author: $XiaoBing
 * date: 2018/9/27 0027
 */
public class WebViewActivity extends BaseActivity {
    private WebView mWebview;
    private TextView mTvtitle;
    private ImageView mImageBack;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        mWebview = (WebView) $(R.id.webview);
//        mWebview.getSettings().setSupportZoom(false);
//        mWebview.getSettings().setUseWideViewPort(true);
//        mWebview.setHorizontalScrollBarEnabled(false);
//        mWebview.getSettings().setLoadWithOverviewMode(true);
//        mWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        if(getIntent()!=null&&getIntent().getExtras()!=null){
            url= (String) getIntent().getExtras().get("url");

        }

        mWebview.loadUrl(url);
        mTvtitle = (TextView) $(R.id.title);
        mImageBack = (ImageView) $(R.id.ic_back);
        mTvtitle.setText("Skynet");
        mImageBack.setImageDrawable(getResources().getDrawable(R.mipmap.ic_back));
        mImageBack.setVisibility(View.VISIBLE);

    }

    @Override
     protected void onEventListener() {
        mImageBack.setOnClickListener(this::OnClick);
    }

    private void OnClick(View view) {
        finish();
    }

    @Override
    protected BaseView getView() {
        return null;
    }

}
