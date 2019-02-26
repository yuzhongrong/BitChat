package android.com.bitchat.fragments

import android.os.Bundle
import android.widget.TextView

import com.cjwsc.idcm.Utils.ACacheUtil

import android.com.bitchat.R
import android.com.bitchat.bean.HDWallet
import com.alibaba.android.arouter.launcher.ARouter
import com.cjwsc.idcm.kotlin.base.BaseKTFragment
import kotlinx.android.synthetic.main.fragment_me.*

class MeFragment : BaseKTFragment() {
    override val layoutId: Int get() = R.layout.fragment_me




    override fun onInitView(bundle: Bundle?) {

        (ACacheUtil.get(activity).getAsObject("wallet") as HDWallet)?.run {
            rootView?.findViewById<TextView>(R.id.eth_address)?.text=address
            rootView?.findViewById<TextView>(R.id.nickname)?.text=username

        }

    }

      override fun onEventListener() {
        scan.setOnClickListener { ARouter.getInstance().build("/app/activitys/QRCodeActivity").navigation(activity) }

    }



    override fun lazyFetchData() {

    }


}
