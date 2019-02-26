package android.com.bitchat.activitys

import android.com.bitchat.R
import android.com.bitchat.bean.HDWallet
import android.com.bitchat.bean.SearchFriendBean
import android.com.bitchat.controler.BitChatSDK
import android.com.bitchat.utils.dialog.LoadDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.cjwsc.idcm.Utils.ACacheUtil
import com.cjwsc.idcm.Utils.JsonUtil
import com.cjwsc.idcm.Utils.RxTimerUtil
import com.cjwsc.idcm.Utils.ToastUtil
import com.cjwsc.idcm.kotlin.base.BaseKTActivity
import com.cjwsc.idcm.net.callback.RxProgressSubscriber
import com.cjwsc.idcm.net.callback.RxSubscriber
import com.cjwsc.idcm.net.exception.ResponseThrowable
import com.hwangjr.rxbus.RxBus
import com.orhanobut.logger.Logger
import com.yy.chat.bean.Friend
import com.yy.chat.db.BitchatDBManager
import kotlinx.android.synthetic.main.activity_add_friend.*
import java.util.concurrent.TimeUnit

@Route(path = "/app/activitys/AddFriendActivity")
class AddFriendActivity:BaseKTActivity() {

    @Autowired(name = "address")
    lateinit var address:String

    var wallet: HDWallet?=null


    override val layoutId: Int= R.layout.activity_add_friend

    override fun onInitView(bundle: Bundle?) {

        ARouter.getInstance().inject(this)

        wallet= ACacheUtil.get(this).getAsObject("wallet") as HDWallet?
    }

    override fun onEventListener() {


        findViewById<TextView>(R.id.title).apply {
            text = getString(R.string.str_vcard)
        }



        action_add_friend.setOnClickListener {
            if(remark.text.isEmpty()){
                ToastUtil.show(getString(R.string.str_add_remark_input_toast))
                return@setOnClickListener
            }
            if(remark1.text.isEmpty()){
                ToastUtil.show(getString(R.string.str_add_remark_input_to_toast))
                return@setOnClickListener
            }

            AddFriend(address,remark.text.toString())
        }

    }



    fun AddFriend(address: String?,remark:String){


        //不能添加自己
        if(wallet?.address==address){
            ToastUtil.show("不能添加自己")
            finish()
            return
        }


        BitChatSDK.gchatsearch(address)
                .subscribeWith(object : RxSubscriber<Any>(){
                    override fun onSuccess(t: Any?) {

                        Logger.d("----查询好友在线----->$t")

                        JsonUtil.toBean<SearchFriendBean>(t.toString(), SearchFriendBean::class.java)?.apply {

                            if(this.common!!.search!!.isEmpty()){//没有查到好友 显示对方离线
                                ToastUtil.show(getString(R.string.str_not_add_friend))

                                return
                            }

                            BitChatSDK.gchatconnectfriend(this.common?.search!![0].publicKey)
                                    .subscribeWith(object: RxProgressSubscriber<Any>(this@AddFriendActivity){
                                        override fun onSuccess(t: Any?) {
                                            Logger.d("----链接成功-------->$t")

                                            //send message
                                            BitChatSDK.gchatchat(t.toString(),"remark:"+remark1.text.toString())
                                                    .subscribeWith(object:RxSubscriber<Any>(){
                                                        override fun onSuccess(t: Any?) {
                                                            Logger.d("----发送成功-------->$t")
                                                            addFriend2DB(address,remark)


                                                        }

                                                        override fun onError(ex: ResponseThrowable?) {

                                                        }


                                                    })

                                        }

                                        override fun onError(ex: ResponseThrowable?) {
                                            Logger.d("----链接失败-------->${ex?.message}")
                                            ToastUtil.show(getString(android.com.bitchat.R.string.str_add_fail))

                                        }


                                    })

                        }





                    }

                    override fun onError(ex: ResponseThrowable?) {

                        Logger.d("----查询失败-fail----->${ex?.message}")
                        ToastUtil.show(getString(R.string.str_not_add_friend))

                    }


                })

    }



    fun addFriend2DB(address: String?,remark:String) {

        //用户添加到数据库
        if(!BitchatDBManager.isexitFriend(address)){//not friend to add
            Friend(address, address,"").apply {
                isFriend=0
                remarck=remark
                saveOrUpdateAsync("userId=?",address).listen { if(it){
                     ToastUtil.show("添加好友成功")
                    RxBus.get().post("ACTION_UPDATE_ADD_FRIEND","")
                    finish()
                }else{
                    ToastUtil.show(getString(R.string.str_add_fail))} }
            }

        }else{
            ToastUtil.show(getString(R.string.str_friend_already))
        }

    }


}