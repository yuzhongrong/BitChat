package com.yy.chat.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.adapter.CommonAdapter;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.yy.chat.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public class SearchPoiActivity extends BaseActivity {

    private RelativeLayout mLayoutBack;
    private EditText mEtSearch;
    private SmartRefreshLayout mSmartRefreshLayout;
    private RecyclerView mRlPoi;
    private String mCity;
    private PoiSearch mPoiSearch;
    private CommonAdapter mCommonAdapter;
    private int mPageNum = 1;
    private String mKeyword;
    private boolean mIsLoad;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_poi;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        if (getIntent() != null && getIntent().getExtras() != null) {
            mCity = getIntent().getStringExtra("city");
        }
        mLayoutBack = (RelativeLayout) $(R.id.layout_back);
        mEtSearch = (EditText) $(R.id.et_search);
        mSmartRefreshLayout = (SmartRefreshLayout) $(R.id.layout_smart);
        mRlPoi = (RecyclerView) $(R.id.rl_poi);
        initRecyclerView();
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (!mCommonAdapter.getData().isEmpty()) {
                    mIsLoad = true;
                    searchPoi(mKeyword);
                }
            }
        });
    }

    private void initRecyclerView() {
        mCommonAdapter = new CommonAdapter<PoiInfo>(R.layout.layout_item_location) {
            @Override
            public void commonconvert(BaseViewHolder helper, PoiInfo item) {
                TextView addrName = helper.getView(R.id.tv_addr_name);
                TextView addrDetail = helper.getView(R.id.tv_addr_detail);
                addrName.setText(item.getName());
                addrDetail.setText(item.getAddress());
                helper.getConvertView().setTag(item);
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("poiInfo", item);
                        setResult(1, intent);
                        finish();
                    }
                });
            }

        };
        mRlPoi.setLayoutManager(new LinearLayoutManager(this));
        mRlPoi.setAdapter(mCommonAdapter);
    }


    @SuppressLint("CheckResult")
    @Override
     protected void onEventListener() {
        mPoiSearch = PoiSearch.newInstance();
        mLayoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        OnGetPoiSearchResultListener poiSearchResultListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult != null && poiResult.getAllPoi() != null && !poiResult.getAllPoi().isEmpty()) {
                    if (!TextUtils.isEmpty(mEtSearch.getText().toString().trim())) {
                        mCommonAdapter.addData(poiResult.getAllPoi());
                    }
                } else {
                    mCommonAdapter.getData().clear();
                    mCommonAdapter.notifyDataSetChanged();
                    ToastUtil.show("无法搜索到相关位置信息");
                }
                if (mIsLoad) {
                    mIsLoad = false;
                    mSmartRefreshLayout.finishLoadmore();
                }
                mPageNum++;
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };
        mPoiSearch.setOnGetPoiSearchResultListener(poiSearchResultListener);

        RxTextView.textChanges(mEtSearch).debounce(200, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        if (!TextUtils.isEmpty(charSequence.toString().trim())) {
                            mPageNum = 1;
                            mCommonAdapter.getData().clear();
                            searchPoi(charSequence.toString().trim());
                        } else {
                            mCommonAdapter.getData().clear();
                            mCommonAdapter.notifyDataSetChanged();
                        }
                    }
                });


    }

    private void searchPoi(String keyword) {
        mKeyword = keyword;
        PoiCitySearchOption citySearchOption = new PoiCitySearchOption();
        citySearchOption.city(mCity);
        citySearchOption.keyword(keyword);
        citySearchOption.isReturnAddr(true);
        citySearchOption.pageCapacity(20);
        citySearchOption.pageNum(mPageNum);
        mPoiSearch.searchInCity(citySearchOption);
    }

    @Override
    protected BaseView getView() {
        return null;
    }
}
