package android.com.bitchat.activitys

import android.annotation.SuppressLint
import android.com.bitchat.R
import android.com.bitchat.bean.HDWallet
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.TextView
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import com.alibaba.android.arouter.facade.annotation.Route
import com.cjwsc.idcm.Utils.ACacheUtil
import com.cjwsc.idcm.Utils.ToastUtil
import com.cjwsc.idcm.Utils.UIUtils
import com.cjwsc.idcm.Utils.image.SaveImgUtil
import com.cjwsc.idcm.kotlin.base.BaseKTActivity
import com.cjwsc.idcm.net.callback.RxProgressSubscriber
import com.cjwsc.idcm.net.exception.ResponseThrowable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_qrcode.*

@Route(path = "/app/activitys/QRCodeActivity")
class QRCodeActivity:BaseKTActivity() {

    var bitmap:Bitmap?=null
    override val layoutId: Int get() = R.layout.activity_qrcode

    override fun onInitView(bundle: Bundle?) {

        findViewById<TextView>(R.id.title).text=getString(R.string.str_identify)
        (ACacheUtil.get(this).getAsObject("wallet") as HDWallet)?.run {
            findViewById<TextView>(R.id.nickname).text=username
            saveImg2Gallery(address)
        }


    }

      override fun onEventListener() {


        save.setOnClickListener {

              SaveImgUtil.saveBitmap(this@QRCodeActivity, bitmap, System.currentTimeMillis().toString() + "")
              ToastUtil.show(getString(R.string.str_save_success))

        }
    }


    private fun saveImg2Gallery(address: String) {

        Flowable.just("")
                .compose(bindToLifecycle())
                .observeOn(Schedulers.io())
                .map { QRCodeEncoder.syncEncodeQRCode(address, UIUtils.dp2px(200f).toInt()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RxProgressSubscriber<Bitmap>(this) {
                    @SuppressLint("ResourceType")
                    override fun onSuccess(data: Bitmap?) {

                        data?.run {
                            qrcode.setImageBitmap(data)
                            bitmap=data
                        }

                    }

                    override fun onError(ex: ResponseThrowable) {
                           ToastUtil.show(getString(R.string.str_code_fail))
                    }
                })


    }
}