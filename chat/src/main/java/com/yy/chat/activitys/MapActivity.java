package com.yy.chat.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cjwsc.idcm.Utils.ToastUtil;
import com.cjwsc.idcm.Utils.image.SaveImgUtil;
import com.cjwsc.idcm.adapter.CommonAdapter;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.BaseView;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.cjwsc.idcm.net.callback.RxSubscriber;
import com.cjwsc.idcm.net.exception.ResponseThrowable;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.yy.chat.R;
import com.yy.chat.bean.LocationData;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class MapActivity extends BaseActivity implements OnGetGeoCoderResultListener {
    Button btn_location;
    MapView mapView;
    private BaiduMap baiduMap;
    private RelativeLayout right_layout;
    private SmartRefreshLayout mSmartRefreshLayout;
    private TextView mTvSend;
    private ImageView mIvSearch;
    private boolean isFirstLocation = true;
    //初始化LocationClient定位类
    private LocationClient mLocationClient = null;
    private MyLocationListener myListener;
    private BDLocation mMyLocation = null;
    //自己的经纬度
    private LatLng myLL;
    PoiSearch mPoiSearch;
    private RecyclerView mRlLocation;
    private CommonAdapter mCommonAdapter;
    private String mChoosedAddr;
    private Marker mMarker;
    private int mPage = 1;
    //当前用于搜索的经纬度
    private LatLng mCurrentSearchLL;
    //当前选中的经纬度
    private LatLng mCurrentChoosedLL;
    private String mKeyword;
    private boolean mIsLoad = false;
    private GeoCoder mGeoCoder;
    private String mCurrentPoiName;
    private String mCurrentAddr;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_map;
    }

    @Override
    protected void onInitView(Bundle bundle) {
        mapView = (MapView) $(R.id.mv_baidu);
        right_layout = (RelativeLayout) $(R.id.right_layout);
        btn_location = (Button) $(R.id.btn_location);
        mRlLocation = (RecyclerView) $(R.id.rl_addr);
        mSmartRefreshLayout = (SmartRefreshLayout) $(R.id.layout_refresh);
        mTvSend = (TextView) $(R.id.ic_right);
        mIvSearch = (ImageView) $(R.id.iv_search);
        mIvSearch.setOnClickListener(mOnClickListener);
        right_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyLocation != null) {
                    baiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
                        @Override
                        public void onSnapshotReady(Bitmap bitmap) {
                            Flowable.just(bitmap).map(new Function<Bitmap, Uri>() {
                                @Override
                                public Uri apply(Bitmap bitmap) throws Exception {
                                    return SaveImgUtil.saveImageToPath(MapActivity.this,bitmap);
                                }
                            }).subscribe(new RxSubscriber<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    LocationData locationData=new LocationData(mCurrentChoosedLL.latitude,mCurrentChoosedLL.longitude,"",uri.toString());
                                    Intent inten = new Intent();
                                    inten.putExtra("location", locationData);
                                    setResult(Activity.RESULT_OK, inten);
                                    finish();
                                }

                                @Override
                                protected void onError(ResponseThrowable ex) {

                                }
                            });

                        }
                    });

                }

            }
        });
        mSmartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mIsLoad = true;
                if (!TextUtils.isEmpty(mKeyword) && mCurrentSearchLL != null) {
                    searchNearby(mKeyword, mCurrentSearchLL);
                }
            }
        });
        mGeoCoder = GeoCoder.newInstance();
        initRecyclerView();
        initMap();
    }

    private View.OnClickListener mOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.iv_search) {
                Intent intent = new Intent(MapActivity.this, SearchPoiActivity.class);
                intent.putExtra("city", mMyLocation.getCity());
                startActivityForResult(intent,1);
                return;
            }
            PoiInfo poiInfo = (PoiInfo) view.getTag();
            mChoosedAddr = poiInfo.getName();
            mCommonAdapter.notifyDataSetChanged();
            mapStatusUpdate(poiInfo.location);
            mCurrentChoosedLL = poiInfo.location;
        }
    };

    private void mapStatusUpdate(LatLng latLng) {
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
        baiduMap.animateMapStatus(mapStatusUpdate);
        mMarker.setPosition(latLng);
    }

    private void initRecyclerView() {
        mCommonAdapter = new CommonAdapter<PoiInfo>(R.layout.layout_item_location) {
            @Override
            public void commonconvert(BaseViewHolder helper, PoiInfo item) {
                TextView addrName = helper.getView(R.id.tv_addr_name);
                TextView addrDetail = helper.getView(R.id.tv_addr_detail);
                ImageView choosedView = helper.getView(R.id.iv_choosed);
                addrName.setText(item.getName());
                if (TextUtils.isEmpty(item.getAddress())) {
                    addrDetail.setVisibility(View.GONE);
                } else {
                    addrDetail.setVisibility(View.VISIBLE);
                    addrDetail.setText(item.getAddress());
                }
                if (item.getName().equals(mChoosedAddr)) {
                    choosedView.setVisibility(View.VISIBLE);
                } else {
                    choosedView.setVisibility(View.GONE);
                }
                helper.getConvertView().setTag(item);
                helper.getConvertView().setOnClickListener(mOnClickListener);
            }

        };
        mRlLocation.setLayoutManager(new LinearLayoutManager(this));
        mRlLocation.setAdapter(mCommonAdapter);
    }

    @Override
     protected void onEventListener() {

    }

    @Override
    protected BaseView getView() {
        return null;
    }
    public void initMap() {
        //得到地图实例
        baiduMap = mapView.getMap();

        /*
        设置地图类型
         */
        //普通地图
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //地图点击事件响应
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mPage = 1;
                mCurrentPoiName = "";
                mapStatusUpdate(latLng);
                reverseGeoCode(latLng);
                mCommonAdapter.getData().clear();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                mPage = 1;
                mCurrentPoiName = mapPoi.getName();
                mapStatusUpdate(mapPoi.getPosition());
                reverseGeoCode(mapPoi.getPosition());
                mCommonAdapter.getData().clear();
                return false;
            }
        });
        //地图加载完成事件响应
        baiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //地图的有些操作需要再加载完成之后进行
            }
        });
        //地图定位图标点击响应事件
        baiduMap.setOnMyLocationClickListener(new BaiduMap.OnMyLocationClickListener() {
            @Override
            public boolean onMyLocationClick() {

                return false;
            }
        });
        mPoiSearch = PoiSearch.newInstance();
        /**
         *回调
         */
        OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {

            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult != null && poiResult.getAllPoi()!=null && !poiResult.getAllPoi().isEmpty()) {
                    List<PoiInfo> poiInfos = poiResult.getAllPoi();
                    if (mPage == 1) {
                        if (mCurrentSearchLL.equals(myLL)) {
                            PoiInfo poiInfo = new PoiInfo();
                            poiInfo.setName(mMyLocation.getDistrict() + mMyLocation.getStreet() + "(" + mMyLocation.getLocationDescribe() + ")");
                            poiInfo.setLocation(new LatLng(mMyLocation.getLatitude(), mMyLocation.getLongitude()));
                            poiInfos.add(0, poiInfo);
                            mChoosedAddr = poiInfo.getName();
                        } else {
                            PoiInfo poiInfo = new PoiInfo();
                            if (!TextUtils.isEmpty(mCurrentPoiName)) {
                                poiInfo.setName(mCurrentPoiName + "(" + mCurrentAddr + ")");
                            } else {
                                poiInfo.setName(mCurrentAddr);
                            }
                            poiInfo.setLocation(mCurrentSearchLL);
                            poiInfos.add(0, poiInfo);
                            mChoosedAddr = poiInfo.getName();
                        }
                    }
                    mCommonAdapter.addData(poiInfos);
                }else {
                    ToastUtil.show("无法搜索到相关位置信息");
                }
                mTvSend.setTextColor(getResources().getColor(R.color.white));
                mTvSend.setEnabled(true);
                if (mIsLoad) {
                    mIsLoad = false;
                    mSmartRefreshLayout.finishLoadmore();
                }
                mPage++;
                Logger.d("-----onGetPoiResult-------"+poiResult);
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                Logger.d("------------");
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
                Logger.d("------------");
            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
                Logger.d("------------");
            }
        };
        /**
         *设置监听
         */
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);
        myListener = new MyLocationListener();
        //卫星地图
        //baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        //空白地图, 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
        //baiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
        //开启交通图
        baiduMap.setTrafficEnabled(true);
        //关闭缩放按钮
        mapView.showZoomControls(false);
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);
        //声明LocationClient类
        mLocationClient = new LocationClient(BaseApplication.getContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        initLocation();
        //开始定位
        mLocationClient.start();
        btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCurrentSearchLL.equals(myLL)) {
                    mPage = 1;
                    searchNearby(mMyLocation.getCity() + mMyLocation.getDistrict(), myLL);
                    mCommonAdapter.getData().clear();
                } else {
                    PoiInfo myInfo = (PoiInfo) mCommonAdapter.getData().get(0);
                    mChoosedAddr = myInfo.getName();
                    mCommonAdapter.notifyDataSetChanged();
                }
                mapStatusUpdate(myLL);
                mCurrentChoosedLL = myLL;
            }
        });

    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        int span = 5000;
        option.setScanSpan(span);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
    }

    /**
     * 设置中心点和添加marker
     *
     * @param map
     * @param bdLocation
     * @param isShowLoc
     */
    public void setPosition2Center(BaiduMap map, BDLocation bdLocation, Boolean isShowLoc) {
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(0)
                .direction(bdLocation.getDirection()).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();
        map.setMyLocationData(locData);

        myLL = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
        OverlayOptions options = new MarkerOptions().position(myLL)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_func_location))
                .zIndex(2)
                .anchor(0.5f,0.85f)
                .animateType(MarkerOptions.MarkerAnimateType.drop)
                .draggable(true);
        mMarker = (Marker) map.addOverlay(options);

        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(myLL).zoom(18.0f);
        map.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        searchNearby(bdLocation.getCity() + bdLocation.getDistrict(), myLL);

    }

    private void searchNearby(String keyword, LatLng ll) {
        mKeyword = keyword;
        mCurrentSearchLL = ll;
        if (mPage == 1) {
            mCurrentChoosedLL = mCurrentSearchLL;
            mTvSend.setTextColor(getResources().getColor(R.color.gray8));
            mTvSend.setEnabled(false);
        }
        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .sortType(PoiSortType.distance_from_near_to_far)
                .pageCapacity(20)
                .pageNum(mPage)
                .keyword(keyword)
                .radius(500)
                .location(ll));

    }


    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    //经纬度转地址结果监听
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        mCurrentAddr = reverseGeoCodeResult.getAddress();
        ReverseGeoCodeResult.AddressComponent addressDetail = reverseGeoCodeResult.getAddressDetail();
        searchNearby(addressDetail.city + addressDetail.district, reverseGeoCodeResult.getLocation());
        Logger.d("onGetReverseGeoCodeResult" + reverseGeoCodeResult);
    }

    /**
     * 经纬度转地址
     * @param latLng 经纬度
     */
    private void reverseGeoCode(LatLng latLng) {
        mGeoCoder.setOnGetGeoCodeResultListener(this);
        mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
    }

    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            mMyLocation = location;
            //获取定位结果
            location.getTime();    //获取定位时间
            location.getLocationID();    //获取定位唯一ID，v7.2版本新增，用于排查定位问题
            location.getLocType();    //获取定位类型
            location.getLatitude();    //获取纬度信息
            location.getLongitude();    //获取经度信息
            location.getRadius();    //获取定位精准度
            location.getAddrStr();    //获取地址信息
            location.getCountry();    //获取国家信息
            location.getCountryCode();    //获取国家码
            location.getCity();    //获取城市信息
            location.getCityCode();    //获取城市码
            location.getDistrict();    //获取区县信息
            location.getStreet();    //获取街道信息
            location.getStreetNumber();    //获取街道码
            location.getLocationDescribe();    //获取当前位置描述信息
            location.getPoiList();    //获取当前位置周边POI信息

            location.getBuildingID();    //室内精准定位下，获取楼宇ID
            location.getBuildingName();    //室内精准定位下，获取楼宇名称
            location.getFloor();    //室内精准定位下，获取当前位置所处的楼层信息
            //经纬度

            //这个判断是为了防止每次定位都重新设置中心点和marker
            if (isFirstLocation) {
                isFirstLocation = false;
                //设置并显示中心点
                setPosition2Center(baiduMap, location, true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (data != null) {
                PoiInfo poiInfo = data.getParcelableExtra("poiInfo");
                mCurrentPoiName = poiInfo.getName();
                mCurrentAddr = poiInfo.getAddress();
                mPage = 1;
                mapStatusUpdate(poiInfo.getLocation());
                searchNearby(poiInfo.getCity()+poiInfo.getArea(), poiInfo.getLocation());
                mCommonAdapter.getData().clear();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        // 退出时销毁定位
        if (mLocationClient != null && myListener != null) {
            mLocationClient.unRegisterLocationListener(myListener);
            mLocationClient.stop();
        }
        if (mapView != null) {
            // 关闭定位图层
            baiduMap.setMyLocationEnabled(false);
            mapView.onDestroy();
            mapView = null;

        }
    }


}
