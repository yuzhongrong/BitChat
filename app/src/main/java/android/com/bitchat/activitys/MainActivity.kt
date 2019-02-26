package android.com.bitchat.activitys

import android.Manifest
import android.com.bitchat.R
import android.com.bitchat.bean.HDWallet
import android.com.bitchat.controler.BitChatSDK
import android.com.bitchat.fragments.ChatFragment
import android.com.bitchat.fragments.HomeFragment
import android.com.bitchat.fragments.MeFragment
import android.com.bitchat.fragments.MyContactFragment
import android.com.bitchat.utils.OkGoUtils
import android.com.bitchat.utils.WalletUtil
import android.com.bitchat.utils.dialog.LoadDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import com.cjwsc.idcm.Utils.ACacheUtil
import com.cjwsc.idcm.Utils.ToastUtil
import com.cjwsc.idcm.base.AppManager
import com.cjwsc.idcm.kotlin.base.BaseKTActivity
import com.cjwsc.idcm.net.callback.RxProgressSubscriber
import com.cjwsc.idcm.net.callback.RxSubscriber
import com.cjwsc.idcm.net.exception.ResponseThrowable
import com.cjwsc.idcm.widget.NavigateTabBar
import com.hwangjr.rxbus.annotation.Tag
import com.hwangjr.rxbus.thread.EventThread
import com.lzy.okgo.model.Progress
import com.lzy.okgo.model.Response
import com.orhanobut.logger.Logger
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yy.chat.bean.Session
import com.yy.chat.db.BitchatDBManager
import com.zhl.cbdialog.CBDialogBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import q.rorbin.badgeview.QBadgeView
import java.io.File

class MainActivity : BaseKTActivity() {

    var languageTag: String? = ""
    var isOpen = false
    var mNavigateTabBar: NavigateTabBar? = null

    var isdownload:Boolean=false

    override val layoutId: Int get() = R.layout.activity_main

    var wallet: HDWallet?=null

    var sdPath:String?=null

    var badgeView:QBadgeView?=null


      override fun onEventListener() {


    }

    override fun onInitView(bundle: Bundle?) {

        isOpen = true
        initNavigeteTabBar()

        val intent = intent
        val bundleString = intent.getBundleExtra("Language")
        if (bundleString != null) {
            languageTag = bundleString.getString("language")
        }


        wallet= ACacheUtil.get(this).getAsObject("wallet") as HDWallet?
        //初始化聊天登录
//        GsSocketManager.getInstance().gchat_login()


        //create wallet

        initData()
    }

    fun initData(){

        var wallet=ACacheUtil.get(this).getAsObject("wallet")
        sdPath= Environment.getExternalStorageDirectory().toString()+"/"//fix path
        if(wallet==null){


            RxPermissions(this).requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(Consumer { permission ->
                        if (permission.granted) {

                            createWallet()
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Logger.d("----拒绝访问-------->")
                            finish()
                            return@Consumer
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Logger.d("----拒绝访问-------->")
                            finish()
                            return@Consumer
                        }
                    })



        }else{
            login()

        }


    }




    fun login(){

       var wallet= ACacheUtil.get(this).getAsObject("wallet") as HDWallet
        Logger.d("-------sdPath----->"+sdPath+"  "+wallet?.address)
        BitChatSDK.login("59.37.126.187",10822,sdPath+"public.pem",sdPath+"private.pem",wallet?.address)
                .compose(bindToLifecycle())
                .subscribeWith(object : RxProgressSubscriber<Any>(this){
                    override fun onSuccess(t: Any?) {

                        Logger.d("----登录成功------>$t")
                        //  searcHFriend()
                        when(t.toString()){

                               "-1" -> {
                                   ToastUtil.show(getString(R.string.str_net_error))
                               }
                        }
                    }

                    override fun onError(ex: ResponseThrowable?) {

                        Logger.d("----登录失败------>${ex?.message}")

                    }

                })

    }

    fun createGenkey(){

        BitChatSDK.gchatgenkey(sdPath)
                .compose(bindToLifecycle())
                .subscribeWith(object : RxProgressSubscriber<Any>(this){
                    override fun onSuccess(t: Any?) {

                        //  Logger.d("----生成key成功------>$t")
                        login()

                    }

                    override fun onError(ex: ResponseThrowable?) {

                            Logger.d("----生成key失败------>${ex?.message}")

                    }


                })




    }



    private fun initNavigeteTabBar() {
        mNavigateTabBar = findViewById(R.id.home_navigate)

        val CHAT_PAGE = getString(R.string.str_chat)
        mNavigateTabBar!!.addTab(ChatFragment::class.java,
                NavigateTabBar.TabParam(
                        R.mipmap.icon_msg,
                        R.mipmap.icon_msg_press,
                        CHAT_PAGE))
        val CONTACTS_PAGE = getString(R.string.str_contact)
        mNavigateTabBar!!.addTab(MyContactFragment::class.java,
                NavigateTabBar.TabParam(
                        R.mipmap.icon_contact,
                        R.mipmap.icon_contact_press,
                        CONTACTS_PAGE))

        val DISCOVER_PAGE = getString(R.string.str_find)
        mNavigateTabBar!!.addTab(HomeFragment::class.java,
                NavigateTabBar.TabParam(
                        R.mipmap.icon_find,
                        R.mipmap.icon_find_press,
                        DISCOVER_PAGE))

//        val AMONG_PAGE = getString(R.string.paihang)
//        mNavigateTabBar!!.addTab(SearchFragment1::class.java,
//                NavigateTabBar.TabParam(
//                        R.mipmap.paihang,
//                        R.mipmap.paihang_sel,
//                        AMONG_PAGE))

        val MINE_PAGE = getString(R.string.str_mine)
        mNavigateTabBar!!.addTab(MeFragment::class.java,
                NavigateTabBar.TabParam(
                        R.mipmap.icon_me,
                        R.mipmap.icon_me_press,
                        MINE_PAGE))

        mNavigateTabBar!!.setTabSelectListener(object : NavigateTabBar.OnTabSelectedListener {
            override fun onTabSelected(holder: NavigateTabBar.ViewHolder) {
                if (CHAT_PAGE == holder.tag.toString()) {
                    mNavigateTabBar!!.showFragment(holder)
                } else if (CONTACTS_PAGE == holder.tag.toString()) {
                    mNavigateTabBar!!.showFragment(holder)
                } else if (MINE_PAGE == holder.tag.toString()) {
                    //EventBus.getDefault().postSticky(new TradeSeletedEvent());
                    mNavigateTabBar!!.showFragment(holder)

                }else if(DISCOVER_PAGE==holder.tag.toString()){
                    mNavigateTabBar!!.showFragment(holder)

                }
//                else if(AMONG_PAGE==holder.tag.toString()){
//                    mNavigateTabBar!!.showFragment(holder)
//
//
//                }
            }
        })

        initBadgeView(mNavigateTabBar!!)
    }


    //初始化badgeview
     fun initBadgeView(mNavigateTabBar: NavigateTabBar) {
        val img = mNavigateTabBar.getViewHolderList(0).tabIcon
        badgeView = QBadgeView(this).apply {
                bindTarget(img.parent as ViewGroup)
                badgeGravity = Gravity.END or Gravity.TOP
                setGravityOffset(resources.getDimensionPixelSize(R.dimen.dp20).toFloat(), resources.getDimensionPixelSize(R.dimen.dp1).toFloat(), false)
                setBadgeTextSize(resources.getDimensionPixelSize(R.dimen.dp8).toFloat(), false)
                isShowShadow = false
                badgeNumber = 0//初始化默认设置0
        }



        onUpdateCountRedPoint("")

    }


    override fun onBackPressed() {
        exitApp()
    }

    /**
     * 退出应用
     */

    private var exitTime: Long = 0

    fun exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.show(getString(R.string.tv_again_click_out))
            exitTime = System.currentTimeMillis()
        } else {
            AppManager.getInstance().finishAllActivity()
            finish()
            System.exit(0)
        }
    }




    fun showUpdateDialog(downloadurl:String){


        CBDialogBuilder(this, R.layout.dialog_upgrade)
                .setTouchOutSideCancelable(true)
                .showCancelButton(true)
                .setTitle(getString(R.string.str_check_newversion))
                .setTitleTextColor(resources.getColor(R.color.white))
                .setMessage(getString(R.string.str_check_newversion_tip))
                .setConfirmButtonText(getString(R.string.str_update_now))
                .showCancelButton(false)
                .setConfirmButtonTextColor(resources.getColor(R.color.colorPrimary))
                .setCancelButtonTextColor(resources.getColor(R.color.black))
                .setCancelable(false)
                .showIcon(false)
                .setButtonClickListener(false) { context, dialog, i ->
                    when (i) {

                        CBDialogBuilder.onDialogbtnClickListener.BUTTON_CONFIRM -> {
                            var progressbar= dialog.findViewById<ProgressBar>(R.id.progressbar)
                            var dialog_posi_btn=dialog.findViewById<Button>(R.id.dialog_posi_btn)

                            if(!isdownload){
                                isdownload=true
                                DownLoadApk(downloadurl,dialog_posi_btn,progressbar)
                            }

                        }

                    }
                }
                .setDialogAnimation(CBDialogBuilder.DIALOG_ANIM_NORMAL)
                .create().show()


    }


    fun DownLoadApk(downloadurl: String, confirm: Button, progressBar: ProgressBar){

        OkGoUtils.DownLoadFile(this@MainActivity,downloadurl,object :OkGoUtils.OkGoFileCallBack{
            override fun onSuccess(response: Response<File>?) {
                isdownload=false//download finish
                try {
                    val i = Intent(Intent.ACTION_VIEW)
                    val filePath = response?.body()?.path //Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/SanjuScanApp_Android.apk";
                    i.setDataAndType(Uri.parse("file://$filePath"), "application/vnd.android.package-archive")
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(i)
                } catch (e: Exception) {
                    e.printStackTrace()
                }finally {
                    finish()
                }



            }

            override fun onError(response: Response<File>?) {
                ToastUtil.show("网络异常")
            }

            override fun onloadProgress(progress: Progress?) {
                var value=(100 * progress!!.currentSize / progress!!.totalSize).toInt()
                confirm.text=getString(R.string.str_updating)+"("+value+"%"+")"
                progressBar.progress=value
            }


        })

    }


     fun createWallet() {
        WalletUtil.CreateWallet("",null, WalletUtil.generateMnemonic())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RxProgressSubscriber<HDWallet>(this) {
                    override fun onSuccess(data: HDWallet?) {

                        if (data != null) {
                         //   ToastUtil.show(getString(R.string.str_craate_account_success))
                            ACacheUtil.get(this@MainActivity).put("wallet", data)
                            createGenkey()


                        }

                    }

                    override fun onError(ex: ResponseThrowable) {
                        //  ToastUtil.show(getString(R.string.str_craate_account_fail));

                    }
                })


    }




    @com.hwangjr.rxbus.annotation.Subscribe(thread = EventThread.MAIN_THREAD, tags = [(Tag("ACTION_UPDATE_SESSION"))])
   fun onUpdateCountRedPoint(tag:String){

       var pointnum= BitchatDBManager.getAllUnreadFromSession()
        badgeView?.badgeNumber = pointnum

    }




}