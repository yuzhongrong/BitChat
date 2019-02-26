package android.com.bitchat.app

import android.com.bitchat.controler.BitChatSDK
import android.content.Context
import android.content.res.Configuration
import android.widget.LinearLayout
import com.cjwsc.idcm.Utils.GlideUtil
import com.cjwsc.idcm.Utils.ToastUtil
import com.cjwsc.idcm.base.application.BaseApplication
import com.cjwsc.idcm.language.LanguageUtil
import com.cjwsc.idcm.net.callback.RxSubscriber
import com.cjwsc.idcm.net.exception.ResponseThrowable
import com.lqr.emoji.LQREmotionKit
import java.util.*

class BitChatApplication : BaseApplication() {




    override fun onCreate() {
        super.onCreate()
        ToastUtil.init(this)
        LanguageUtil.initAppLanguage(this)
        //初始化表情包
        LQREmotionKit.init(this, { context, path, imageView -> GlideUtil.loadImageView(context, path, imageView) }
                // Glide.with(context).load(path).centerCrop().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView)

        )


    }






    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        LanguageUtil.initAppLanguage(this)
    }


    override fun attachBaseContext(base: Context?) {
        val newContext = LanguageUtil.initAppLanguage(base)
        super.attachBaseContext(newContext)
    }


}