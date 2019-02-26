package android.com.bitchat.activitys

import android.com.bitchat.R
import android.com.bitchat.bean.EventAddfriend
import android.com.bitchat.bean.HDWallet
import android.com.bitchat.bean.SearchFriendBean
import android.com.bitchat.controler.BitChatSDK
import android.com.bitchat.utils.WalletUtil
import android.content.Intent
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder
import cn.bingoogolapple.qrcode.zxing.ZXingView
import com.alibaba.android.arouter.launcher.ARouter
import com.cjwsc.idcm.Utils.ACacheUtil
import com.cjwsc.idcm.Utils.JsonUtil
import com.cjwsc.idcm.Utils.ToastUtil
import com.cjwsc.idcm.Utils.image.ImageUtils
import com.cjwsc.idcm.kotlin.base.BaseKTActivity
import com.cjwsc.idcm.net.callback.RxProgressSubscriber
import com.cjwsc.idcm.net.exception.ResponseThrowable
import com.hwangjr.rxbus.RxBus
import com.lqr.imagepicker.ImagePicker
import com.lqr.imagepicker.bean.ImageItem
import com.lqr.imagepicker.ui.ImageGridActivity
import com.lqr.imagepicker.ui.ImagePreviewActivity
import com.orhanobut.logger.Logger
import com.yy.chat.bean.Friend
import com.yy.chat.db.BitchatDBManager
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.simple.eventbus.EventBus
import java.io.File
import java.util.ArrayList

class ScanActivity:BaseKTActivity(), QRCodeView.Delegate {


    var zxingview: ZXingView?=null
    var wallet: HDWallet?=null
    override val layoutId: Int get() = R.layout.activity_scan


    override fun onInitView(bundle: Bundle?) {

        findViewById<TextView>(R.id.title).run {
            text=getString(R.string.str_add_friend)

        }

        findViewById<TextView>(R.id.ic_right_tv)?.apply {
            visibility= View.VISIBLE
            text=getString(R.string.str_gallery)
            setOnClickListener {jump2GridImage()  }
        }

        findViewById<RelativeLayout>(R.id.toolbar).setBackgroundColor(resources.getColor(R.color.bg_pop))

        zxingview= findViewById(R.id.zxingview)
        wallet= ACacheUtil.get(this).getAsObject("wallet") as HDWallet?

        zxingview?.setDelegate(this)

    }

      override fun onEventListener() {

    }


    override fun onStart() {
        super.onStart()
        //打开后置摄像头预览,但并没有开始扫描
        zxingview?.startCamera()
        //开启扫描框
        zxingview?.showScanRect()
        zxingview?.startSpot()
    }

    override fun onStop() {
        zxingview?.stopCamera()
        super.onStop()

    }

    override fun onDestroy() {
        zxingview?.onDestroy()
        super.onDestroy()

    }


    override fun onScanQRCodeSuccess(result: String?) {

        //扫描成功后调用震动器
        (getSystemService(VIBRATOR_SERVICE) as Vibrator).apply { vibrate(200); }
        //显示扫描结果
       // ToastUtil.show("扫码成功$result")
        //扫码成功添加好友
        if(WalletUtil.isETHValidAddress(result)){
            //search friend
          //  AddFriend(result)
            ARouter.getInstance().build("/app/activitys/AddFriendActivity").withString("address",result).navigation()
            finish()

        }
        //再次延时1.5秒后启动
        zxingview?.startSpot()


    }

    override fun onScanQRCodeOpenCameraError() {
        ToastUtil.show("打开相机失败")

    }


    fun jump2GridImage(){
        //选择相册选择二维码
        val imagePicker = ImagePicker.getInstance()
        imagePicker.isMultiMode = false//覆盖application中设置
        imagePicker.isShowCamera = false  //显示拍照按钮
        imagePicker.isCrop = false
        val intent = Intent(this, ImageGridActivity::class.java)
        intent.putExtra(ImagePreviewActivity.ISORIGIN, true)//默认选择原图
        startActivityForResult(intent, ImagePicker.RESULT_CODE_ITEMS)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {

            ImagePicker.RESULT_CODE_ITEMS -> if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {//返回单张或多张照片
                if (data != null) {
                    //是否发送原图
                    val isOrig = data.getBooleanExtra(ImagePreviewActivity.ISORIGIN, false)
                    val images = data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) as ArrayList<ImageItem>
                    Log.e("CSDN_LQR", if (isOrig) "发原图" else "不发原图")//若不发原图的话，需要在自己在项目中做好压缩图片算法
                    for (imageItem in images) {
                        val imageFileThumb: File?
                        val imageFileSource: File?
                        if (isOrig) {
                            imageFileSource = File(imageItem.path)
                            imageFileThumb = ImageUtils.genThumbImgFile(imageItem.path)

                        } else {
                            //压缩图片
                            imageFileSource = ImageUtils.genThumbImgFile(imageItem.path)
                            imageFileThumb = ImageUtils.genThumbImgFile(imageFileSource!!.absolutePath)
                        }
                        //                           if (imageFileSource != null && imageFileSource != null)
                        //                               mPresenter.sendImgMsg(imageFileThumb, imageFileSource);

                        if (imageFileSource != null) Flowable.just("")
                                //   .compose(this.bindToLifecycle())
                                .observeOn(Schedulers.io())
                                .map { QRCodeDecoder.syncDecodeQRCode(imageFileSource.path) }
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : RxProgressSubscriber<String>(this) {
                                    override fun onSuccess(data: String) {
                                        Logger.d("-----图片识别成功--->")
                                        // if (!TextUtils.isEmpty(data)) recycler1.setText(data)
                                        if(WalletUtil.isETHValidAddress(data)){
                                           //add friend
                                            ARouter.getInstance().build("/app/activitys/AddFriendActivity").withString("address",data).navigation()

                                        }

                                    }

                                    override fun onError(ex: ResponseThrowable) {

                                        if (ex.errorCode == "1002") return
                                        Logger.d("-----图片识别失败--->" + ex.errorMsg)
                                        ToastUtil.show(getString(R.string.str_scan_faile))

                                    }
                                })

                    }


                }
            }
        }
    }






}