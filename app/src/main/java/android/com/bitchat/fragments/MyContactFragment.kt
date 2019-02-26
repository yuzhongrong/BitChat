package android.com.bitchat.fragments

import android.Manifest
import android.com.bitchat.R
import android.com.bitchat.activitys.IMSessionActivity
import android.com.bitchat.activitys.ScanActivity
import android.com.bitchat.bean.EventAddfriend
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder
import com.alibaba.android.arouter.launcher.ARouter
import com.cjwsc.idcm.Utils.RxTimerUtil
import com.cjwsc.idcm.Utils.ToastUtil
import com.cjwsc.idcm.Utils.image.ImageUtils
import com.cjwsc.idcm.base.BaseActivity
import com.cjwsc.idcm.kotlin.base.BaseKTActivity
import com.cjwsc.idcm.net.callback.RxProgressSubscriber
import com.cjwsc.idcm.net.exception.ResponseThrowable
import com.hwangjr.rxbus.annotation.Produce
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.hwangjr.rxbus.thread.EventThread
import com.lqr.adapter.LQRViewHolderForRecyclerView
import com.lqr.imagepicker.ImagePicker
import com.lqr.imagepicker.bean.ImageItem
import com.lqr.imagepicker.ui.ImageGridActivity
import com.lqr.imagepicker.ui.ImagePreviewActivity
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.android.FragmentEvent
import com.yy.chat.bean.Friend
import com.yy.chat.db.BitchatDBManager
import com.yy.chat.db.DBManager
import com.yy.chat.fragments.ContactFragment
import com.yy.chat.widget.SortUtils
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import org.simple.eventbus.EventBus
import java.io.File
import java.util.ArrayList
import java.util.concurrent.TimeUnit

class MyContactFragment:ContactFragment() {



    override fun onInitView(bundle: Bundle?) {
        super.onInitView(bundle)
        mHeaderView.findViewById<TextView>(R.id.title).text=getString(R.string.str_contacts)
        llNewFriend.setOnClickListener { jump2Scan() }
    }


    override fun loadData() {
        getAllFriends("")
    }

    override fun handlerListItemClick(item: Friend?, holder: LQRViewHolderForRecyclerView?) {
        ARouter.getInstance().build("/app/activitys/IMSessionActivity").with(Bundle().apply { putSerializable("friend",item) }).navigation()
    }



    fun jump2Scan(){

        RxPermissions(activity).requestEach(Manifest.permission.CAMERA)
                .subscribe { permission ->
                    if (permission.granted) {
                        Logger.d("----同意请求相机-------->")
                        startActivity(Intent(activity, ScanActivity::class.java))
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        Logger.d("----拒绝访问-------->")
                    } else {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        Logger.d("----拒绝访问-------->")
                    }
                }

    }


    @Subscribe(thread = EventThread.MAIN_THREAD, tags = [(Tag("ACTION_UPDATE_ADD_FRIEND"))])
    fun getAllFriends(string: String){

        Logger.d("------更新好友列表------>")
        BitchatDBManager.getAllFriends()
                .compose((activity as BaseKTActivity).bindToLifecycle())
                .subscribeWith(object :RxProgressSubscriber<MutableList<Friend>>(this){
                    override fun onSuccess(data: MutableList<Friend>?) {

                        if (data != null && data.size > 0) {
                            mData.clear()
                            mData.addAll(data)
                            SortUtils.sortContacts(mData)
                            mFooterView.text = data.size.toString() + resources.getString(R.string.count_of_contacts)
                            if (mAdapter != null)
                                RxTimerUtil.timer(500, TimeUnit.MILLISECONDS) { n -> mAdapter.notifyDataSetChanged() }
                        } else {
                            mData.clear()
                            mFooterView.text = data?.size.toString() + resources.getString(R.string.count_of_contacts)
                            mAdapter.notifyDataSetChanged()
                        }

                    }

                    override fun onError(ex: ResponseThrowable?) {
                        ToastUtil.show("获取好友列表失败")

                    }


                })


    }


    fun getAllFriend(){

        DBManager.getAllFriends()


    }


    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }



}