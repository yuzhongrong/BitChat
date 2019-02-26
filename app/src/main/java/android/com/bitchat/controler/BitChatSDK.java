package android.com.bitchat.controler;

import android.annotation.SuppressLint;
import android.widget.LinearLayout;

import com.orhanobut.logger.Logger;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BitChatSDK {


    @SuppressLint("CheckResult")
    public static Flowable<Object> login(String ip,int port,String publickey,String privatekey,String address){

       return Flowable.just("")
                .observeOn(Schedulers.io())
                .map(new Function<String, Object>() {

                    @Override
                    public Object apply(String s) throws Exception {
                        Logger.d("-----login前------>");

                      int result=  GsSocketManager.getInstance().gchatlogin(ip,port,publickey,privatekey,address);
                        Logger.d("-----login实际情况------>"+result);
                        return result;

                    }
                })
                .observeOn(AndroidSchedulers.mainThread());


    }



    @SuppressLint("CheckResult")
    public static Flowable<Object> gchatgenkey(String path){

        return Flowable.just("")
                .observeOn(Schedulers.io())
                .map(new Function<String, Object>() {

                    @Override
                    public Object apply(String s) throws Exception {

                        boolean result=  GsSocketManager.getInstance().gchatgenkey(path);
                        Logger.d("-----login实际情况------>"+result);
                        return result;

                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }





    @SuppressLint("CheckResult")
    public static Flowable<Object> loginout(){

        return Flowable.just("")
                .observeOn(Schedulers.io())
                .map(new Function<String, Object>() {

                    @Override
                    public Object apply(String s) throws Exception {
                        return GsSocketManager.getInstance().gchatlogout();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());


    }


    @SuppressLint("CheckResult")
    public static Flowable<Object> helloBitChat(){

        return Flowable.just("")
                .observeOn(Schedulers.io())
                .map(new Function<String, Object>() {

                    @Override
                    public Object apply(String s) throws Exception {
                        return GsSocketManager.getInstance().helloBitchat();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());


    }


    @SuppressLint("CheckResult")
    public static Flowable<Object> gchatsearch(String address){

        return Flowable.just("")
                .observeOn(Schedulers.io())
                .map(new Function<String, Object>() {

                    @Override
                    public Object apply(String s) throws Exception {
                        return GsSocketManager.getInstance().gchatsearch(address);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

    }




    @SuppressLint("CheckResult")
    public static Flowable<Object> gchatconnectfriend(String publickey){

        return Flowable.just("")
                .observeOn(Schedulers.io())
                .map(new Function<String, Object>() {

                    @Override
                    public Object apply(String s) throws Exception {
                        return GsSocketManager.getInstance().gchatconnectfriend(publickey);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

    }




    @SuppressLint("CheckResult")
    public static Flowable<Object> gchatchat(String handler,String content){

        return Flowable.just("")
                .observeOn(Schedulers.io())
                .map(new Function<String, Object>() {

                    @Override
                    public Object apply(String s) throws Exception {
                        return GsSocketManager.getInstance().gchatchat(handler,content);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

    }



//    @SuppressLint("CheckResult")
//    public Flowable<Object> search(String id,byte[] result){
//
//        return Flowable.just("")
//                .observeOn(Schedulers.io())
//                .map(new Function<String, Object>() {
//
//                    @Override
//                    public Object apply(String s) throws Exception {
//                        return GsSocketManager.getInstance().gchat_search(id,result);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread());
//
//
//    }



//    @SuppressLint("CheckResult")
//    public Flowable<Object> chat(byte[] result){
//
//        return Flowable.just("")
//                .observeOn(Schedulers.io())
//                .map(new Function<String, Object>() {
//
//                    @Override
//                    public Object apply(String s) throws Exception {
//                        return GsSocketManager.getInstance().gchat_chat(result);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread());
//
//
//    }





}
