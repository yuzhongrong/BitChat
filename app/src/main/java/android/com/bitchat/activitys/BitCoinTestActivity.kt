package android.com.bitchat.activitys

import android.annotation.SuppressLint
import android.com.bitchat.BuildConfig
import android.com.bitchat.R
import android.com.bitchat.bean.TianWallet
import android.com.bitchat.controler.GsSocketManager
import android.com.bitchat.utils.MnemonicUtil
import android.com.bitchat.utils.WalletUtil
import android.os.Bundle
import com.cjwsc.idcm.Utils.JsonUtil
import com.cjwsc.idcm.kotlin.base.BaseKTActivity
import com.cjwsc.idcm.net.callback.RxSubscriber
import com.cjwsc.idcm.net.exception.ResponseThrowable
import com.google.common.collect.ImmutableList
import com.orhanobut.logger.Logger
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import org.bitcoinj.core.Address
import org.bitcoinj.core.Coin
import org.bitcoinj.core.Context
import org.bitcoinj.core.ECKey
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.params.MainNetParams
import org.bitcoinj.params.TestNet3Params
import org.bitcoinj.wallet.*
import org.web3j.utils.Numeric
import java.math.BigInteger
import java.util.*

class BitCoinTestActivity:BaseKTActivity() {
    override val layoutId: Int= R.layout.activity_test

    override fun onInitView(bundle: Bundle?) {

//        createBTCWallet()
//                .subscribeWith(object :RxSubscriber<TianWallet>(){
//                    override fun onSuccess(t: TianWallet?) {
//
//                        Logger.d("-----创建成功------>"+t?.address+"\n"+t?.privatekey+"\n"+t?.publickey+"\n"+t?.words)
//                    }
//
//                    override fun onError(ex: ResponseThrowable?) {
//                        Logger.d("-----创建失败------>${ex?.message}")
//
//
//                    }
//
//
//                })


        getBanlanceforWallet()?.subscribeWith(object :RxSubscriber<Unit>(){
            override fun onSuccess(t: Unit?) {
                Logger.d("----查询成功------>")
            }

            override fun onError(ex: ResponseThrowable?) {
                Logger.d("----查询失败------>")

            }


        })





    }


    fun createBTCWallet(): Flowable<TianWallet> {

        return Flowable.just(makeMnemonic())
                .observeOn(Schedulers.io())
                .map { createBTCWalletFromWords(it) }
                .observeOn(AndroidSchedulers.mainThread())

    }


    fun getBanlanceforWallet(): Flowable<Unit>? {

        return Flowable.just("wink unveil action hope chase grant denial else matter metal crack purpose")
                .observeOn(Schedulers.io())
                .map {getBalance(it)
                }
                .observeOn(AndroidSchedulers.mainThread())

    }



    override fun onEventListener() {
    }


    //average green proud remember advance trick estate oblige trouble when cube person

    fun makeMnemonic(): String {

        return WalletUtil.generateMnemonic()



    }



     fun createBTCWalletFromWords(words: String): TianWallet {
        //把助记词切割成数组
        val wordsList = Arrays.asList(*words.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        val deterministicSeed = DeterministicSeed(wordsList, null, "", 0)
        val deterministicKeyChain = DeterministicKeyChain.builder().seed(deterministicSeed).build()
        //这里运用了BIP44里面提到的算法, 44'是固定的, 后面的一个0'代表的是币种BTC
        var privKeyBTC = deterministicKeyChain.getKeyByPath(parsePath("m/44'/0'/0'/0/0"), true).privKey
        //如果是调试模式, 第二个字符串应该填1'
        if (BuildConfig.DEBUG) {
            privKeyBTC = deterministicKeyChain.getKeyByPath(parsePath("m/44'/1'/0'/0/0"), true).privKey
        }

        val ecKey = ECKey.fromPrivate(privKeyBTC)
        val publickey = Numeric.toHexStringNoPrefixZeroPadded(BigInteger(ecKey.pubKey), 66)
        //正式环境应该是主网参数
        var privateKey = ecKey.getPrivateKeyEncoded(MainNetParams.get()).toString()
        //如果是测试环境, 应该调用测试网参数
        if (BuildConfig.DEBUG) {
            privateKey = ecKey.getPrivateKeyEncoded(TestNet3Params.get()).toString()
            return TianWallet(ecKey.toAddress(TestNet3Params.get()).toString(), publickey, privateKey, words)
        }
         Logger.d("-------->privateKey:"+privateKey+"  "+"address"+ecKey.toAddress(TestNet3Params.get()).toString()+"  "+"publickey:"+publickey+"   "+"words:"+words)

        return TianWallet(ecKey.toAddress(MainNetParams.get()).toString(), publickey, privateKey, words)
    }


    //"m/44'/60'/0'/0"
    private fun parsePath(str: String): ImmutableList<ChildNumber> {
        val split = str.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val arrayList = ArrayList<ChildNumber>()
        for (numberStr in split) {
            if (!"m".equals(numberStr, ignoreCase = true)) {
                val z = numberStr[numberStr.length - 1] == '\''
                var newNumberStr = numberStr
                if (z) {
                    newNumberStr = numberStr.substring(0, numberStr.length - 1)
                }
                arrayList.add(ChildNumber(Integer.parseInt(newNumberStr), z))
            }
        }
        return ImmutableList.copyOf(arrayList)
    }



    fun getBalance(words:String){

        //把助记词切割成数组
        val wordsList = Arrays.asList(*words.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        val deterministicSeed = DeterministicSeed(wordsList, null, "", 0)
        val deterministicKeyChain = DeterministicKeyChain.builder().seed(deterministicSeed).build()

        //这里运用了BIP44里面提到的算法, 44'是固定的, 后面的一个0'代表的是币种BTC
        var privKeyBTC = deterministicKeyChain.getKeyByPath(parsePath("m/44'/0'/0'/0/0"), true).privKey
        //如果是调试模式, 第二个字符串应该填1'
        if (BuildConfig.DEBUG) {
            privKeyBTC = deterministicKeyChain.getKeyByPath(parsePath("m/44'/1'/0'/0/0"), true).privKey
        }

        val ecKey = ECKey.fromPrivate(privKeyBTC)

        var privateKey = ecKey.getPrivateKeyEncoded(TestNet3Params.get()).toString()


        Logger.d("-----privatekey----->"+privateKey)


    }


}