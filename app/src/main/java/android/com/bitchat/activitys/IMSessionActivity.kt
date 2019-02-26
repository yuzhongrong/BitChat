package android.com.bitchat.activitys

import android.com.bitchat.bean.HDWallet
import android.com.bitchat.bean.SearchFriendBean
import android.com.bitchat.controler.BitChatSDK
import android.content.Context
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
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
import com.yy.chat.R
import com.yy.chat.activitys.SessionActivity
import com.yy.chat.bean.ChatMessage
import com.yy.chat.bean.Friend
import com.yy.chat.db.BitchatDBManager
import io.reactivex.Flowable
import io.reactivex.internal.fuseable.QueueFuseable.ASYNC
import kotlinx.android.synthetic.main.item_home_list.*
import org.bitcoinj.params.TestNet3Params
import org.litepal.crud.callback.SaveCallback
import java.util.concurrent.TimeUnit



@Route(path = "/app/activitys/IMSessionActivity")
class IMSessionActivity:SessionActivity(),ChatMessage.HandleThreadMessage {


    var handlerName=""
    var wallet: HDWallet?=null
    var friend:Friend?=null

    override fun onInitView(bundle: Bundle?)
    {
        super.onInitView(bundle)
        wallet= ACacheUtil.get(this).getAsObject("wallet") as HDWallet?

        if(intent?.extras!=null){

            friend= intent.extras.get("friend") as Friend?
            title.text=friend?.remarck

        }

        initData(friend)

    }




    fun initData(friend: Friend?){

        //clear the session
        BitchatDBManager.getOneSession(friend?.userId)?.apply {
           unreadmsgcount=0
           saveOrUpdateAsync("sessionid=?", sessionid).listen(SaveCallback { RxBus.get().post("ACTION_UPDATE_SESSION", sessionid) })
        }

        searcHFriend(friend)

    }




    fun searcHFriend(friend: Friend?){

        BitChatSDK.gchatsearch(friend?.userId)
                .subscribeWith(object : RxSubscriber<Any>(){
                    override fun onSuccess(t: Any?) {

                        Logger.d("----查询成功-success----->$t")

                        JsonUtil.toBean<SearchFriendBean>(t.toString(),SearchFriendBean::class.java)?.apply {

                            if(this.common!!.search!!.isEmpty()){//没有查到好友 显示对方离线
                                title.text=friend?.remarck+getString(android.com.bitchat.R.string.str_outline)
                                return
                            }else{
                                title.text=friend?.remarck+getString(android.com.bitchat.R.string.str_channeling)
                            }

                            Logger.d("----公钥----->"+this.common?.search!![0].publicKey)

                            BitChatSDK.gchatconnectfriend(this.common?.search!![0].publicKey)
                                    .subscribeWith(object:RxProgressSubscriber<Any>(this@IMSessionActivity){
                                        override fun onSuccess(t: Any?) {
                                            Logger.d("----链接成功-------->$t")
                                            handlerName=t.toString()
                                            title.text=friend?.remarck
                                            ToastUtil.show(getString(android.com.bitchat.R.string.str_channel_success))

                                        }

                                        override fun onError(ex: ResponseThrowable?) {
                                            Logger.d("----链接失败-------->${ex?.message}")

                                            title.text=friend?.remarck+getString(android.com.bitchat.R.string.str_channel_error)
                                            ToastUtil.show(getString(android.com.bitchat.R.string.str_channel_error))

                                        }


                                    })

                        }





                    }

                    override fun onError(ex: ResponseThrowable?) {

                        Logger.d("----查询失败-fail----->${ex?.message}")

                    }


                })

    }



    override fun sendMessage(content: ChatMessage?) {

        content?.setHandlerMessage(this)
        messageList.addData(content)
        messageList.refreshSelectLast(true)

    }

    override fun sendRedEnvelope() {
    }

    override fun transferAccounts() {
    }





    override fun handlerMessage(context: Context?, chatMessage: ChatMessage?, callBack: ChatMessage.SendMsgCallBack?) {

        //send message
        BitChatSDK.gchatchat(handlerName,chatMessage!!.body)
                .subscribeWith(object:RxSubscriber<Any>(){
                    override fun onSuccess(t: Any?) {
                        Logger.d("----发送成功-------->$t")

                        when(t.toString()){
                            "-1" ->{
                                callBack?.onFailed()
                                ToastUtil.show("发送消息失败")
                            }
                            "0"->{ callBack?.onSuccess()}

                        }


                    }

                    override fun onError(ex: ResponseThrowable?) {
                        Logger.d("----发送失败-------->${ex?.message}")
                        callBack?.onFailed()

                    }


                })

    }



   @com.hwangjr.rxbus.annotation.Subscribe
     fun onMessage(message: ChatMessage) {


       if(friend?.address!=message.userid)return//filter message
        Logger.d("---收到消息------>"+message)
        messageList.addData(message)

    }




}