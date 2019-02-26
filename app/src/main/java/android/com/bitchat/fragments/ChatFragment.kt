package android.com.bitchat.fragments

import android.com.bitchat.R
import android.os.Bundle
import android.text.style.ImageSpan
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.cjwsc.idcm.Utils.TimeUtils
import com.cjwsc.idcm.kotlin.base.BaseKTActivity
import com.cjwsc.idcm.kotlin.base.BaseKTFragment
import com.cjwsc.idcm.net.callback.RxSubscriber
import com.cjwsc.idcm.net.exception.ResponseThrowable
import com.google.common.eventbus.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.hwangjr.rxbus.thread.EventThread
import com.itheima.roundedimageview.RoundedImageView
import com.lqr.adapter.LQRAdapterForRecyclerView
import com.lqr.adapter.LQRViewHolderForRecyclerView
import com.lqr.emoji.MoonUtils
import com.lqr.recyclerview.LQRRecyclerView
import com.orhanobut.logger.Logger
import com.yy.chat.activitys.SessionActivity
import com.yy.chat.bean.ChatMessage
import com.yy.chat.bean.Friend
import com.yy.chat.bean.Session
import com.yy.chat.db.BitchatDBManager
import kotlinx.android.synthetic.main.fragment_chat.*
import org.litepal.LitePal
import org.simple.eventbus.EventBus
import org.simple.eventbus.Subscriber
import org.simple.eventbus.ThreadMode
import q.rorbin.badgeview.Badge
import q.rorbin.badgeview.QBadgeView


class ChatFragment:BaseKTFragment() {
    lateinit var chatlist:LQRRecyclerView
    lateinit var lqrAdapterForRecyclerView: LQRAdapterForRecyclerView<Session>
    var badgeView:QBadgeView?=null

    override val layoutId: Int
        get() = R.layout.fragment_chat

    override fun onInitView(bundle: Bundle?) {
        rootView!!.findViewById<TextView>(R.id.title).text=getString(R.string.str_msg)
        chatlist=rootView!!.findViewById(R.id.chatlist)
        lqrAdapterForRecyclerView=object:LQRAdapterForRecyclerView<Session>(activity, mutableListOf(),R.layout.item_session_list){
            override fun convert(helper: LQRViewHolderForRecyclerView?, item: Session?, position: Int) {

               var friend= BitchatDBManager.getRemarkBySessionId(item?.sessionid)
                helper?.convertView?.setOnClickListener {
                    //create friend bean
                    ARouter.getInstance().build("/app/activitys/IMSessionActivity").with(Bundle().apply { putSerializable("friend",friend)}).navigation(activity)
                }

                helper?.getView<ImageView>(R.id.headimg)?.setBackgroundResource(R.drawable.em_default_avatar)

                helper?.getView<TextView>(R.id.fromname)?.text=friend.remarck

                when(item?.type){
                    0 ->{MoonUtils.identifyFaceExpression(mContext, helper?.getView<TextView>(R.id.lastmessage), item?.newmessage, ImageSpan.ALIGN_BOTTOM) }
                    1 ->{helper?.getView<TextView>(R.id.lastmessage)?.text="[图片]"}
                    2 ->{helper?.getView<TextView>(R.id.lastmessage)?.text="[语音]"}
                    3 ->{helper?.getView<TextView>(R.id.lastmessage)?.text="[视频]"}
                    4 ->{helper?.getView<TextView>(R.id.lastmessage)?.text="[位置]"}
                    5 ->{helper?.getView<TextView>(R.id.lastmessage)?.text="[红包]"}

                }

                helper?.getView<TextView>(R.id.time)?.text= TimeUtils.getNewChatTime(item!!.updatetime)

                //update redpoint
                Logger.d("-----item.unreadmsgcount----->"+item.unreadmsgcount)
                helper?.getView<LinearLayout>(R.id.ll_img)?.let { binderTag(it,item) }
            }

        }.apply {chatlist.adapter=this}


        initData()

    }



    fun binderTag(view:View,item: Session){


        val tag = view?.tag
        var badgeView: QBadgeView? = null

        if(tag==null){
            badgeView= QBadgeView(activity).apply {
                this!!.bindTarget(view)
                badgeGravity = Gravity.END or Gravity.TOP
                setGravityOffset((-5).toFloat(), (-5).toFloat(), false)
                setBadgeTextSize(resources.getDimensionPixelSize(R.dimen.dp10).toFloat(), false)
                isShowShadow = false
                badgeNumber = item.unreadmsgcount
                view?.tag=this

            }

        } else run {
            badgeView = (tag as QBadgeView)
            badgeView?.badgeNumber = item.unreadmsgcount

        }

    }


   fun initData(){
       getAllSession()

    }

      override fun onEventListener() {
    }

    override fun lazyFetchData() {



    }


    @com.hwangjr.rxbus.annotation.Subscribe(thread = EventThread.MAIN_THREAD, tags = [(Tag("ACTION_UPDATE_SESSION"))])
     fun onUpdateSession(sessionid: String) {
        getAllSession()

    }

    fun getAllSession(){

        BitchatDBManager.getAllSession()
                .compose((activity as BaseKTActivity).bindToLifecycle())
                .subscribeWith(object :RxSubscriber<MutableList<Session>>(){
                    override fun onSuccess(t: MutableList<Session>?) {
                        lqrAdapterForRecyclerView.data= t

                    }

                    override fun onError(ex: ResponseThrowable?) {
                    }


                })

    }

}