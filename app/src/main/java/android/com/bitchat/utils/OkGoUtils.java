package android.com.bitchat.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.Utils.JsonUtil;
import com.cjwsc.idcm.base.BaseActivity;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.cjwsc.idcm.constant.AcacheKeys;
import com.cjwsc.idcm.language.LanguageSettingBean;
import com.cjwsc.idcm.language.LanguageUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;

import retrofit2.http.HTTP;

public class OkGoUtils {


    public static void upLoadFiles(BaseActivity activity,String url, List<File> datas,OkGoCallBack callBack){


        HttpParams httpParams=new HttpParams();
        httpParams.put("lang", AppLanguageUtils.getCurrentLang());
        OkGo.<String>post(url)
                .tag(activity)
                .params(httpParams)
                .isMultipart(true)
                .addFileParams("files",datas)
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {


                    }

                    @Override
                    public void onSuccess(Response<String> response) {

                        Logger.d("-----上传成功---->"+response.body());
                        if(callBack!=null)callBack.onSuccess(response);
                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                        Logger.d("-----上传进度---->"+progress.currentSize);
                        if(callBack!=null)callBack.onloadProgress(progress);
                    }

                    @Override
                    public void onError(Response<String> response) {
                     //   activity.dismissDialog();
                        Logger.d("-----上传失败---->"+response.body());
                        if(callBack!=null)callBack.onError(response);
                    }
                });

    }


    /**下载文件**/


    public static void DownLoadFile(Activity activity,String url,OkGoFileCallBack callBack){


        OkGo.<File>get(url)
                .tag(activity)
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(Response<File> response) {

                        Logger.d("------下载文件成功------>"+response.body());
                        callBack.onSuccess(response);

                    }

                    @Override
                    public void onError(Response<File> response) {

                        Logger.d("------下载文件失败------>"+response.body());

                        callBack.onError(response);
                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                        Logger.d("------下载中------>"+progress.currentSize/progress.totalSize*100);
                        callBack.onloadProgress(progress);

                    }

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        Logger.d("------开始下载------>");

                    }
                });

    }



    /**通用的请求接口**/

    public static void postCommon(BaseActivity context,String url,String jsonbean,OkGoCommonResult callBack){

        HttpParams httpParams=new HttpParams();
        httpParams.put("lang", AppLanguageUtils.getCurrentLang());
        OkGo.<String>post(url)
                .tag(context)
                .params(httpParams)
                .upJson(jsonbean)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        context.dismissDialog();
                        if(callBack!=null)callBack.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        context.dismissDialog();
                        if(callBack!=null)callBack.onError(response);
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        context.showDialog();
                    }
                });


    }




    /**上传页面获取分类接口**/

    public static void getCategaryFromUpload(BaseActivity context,String url,OkGoCommonResult callBack){

        HttpParams httpParams=new HttpParams();
        httpParams.put("lang", AppLanguageUtils.getCurrentLang());
        OkGo.<String>post(url)
                .tag(context)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        context.dismissDialog();
                        if(callBack!=null)callBack.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        context.dismissDialog();
                        if(callBack!=null)callBack.onError(response);
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        context.showDialog();
                    }
                });


    }









    /**获取app 详情接口**/


    public static void getAppDetail(BaseActivity activity, String url,String appid,String source, OkGoCommonResult commonResult){


        HttpParams httpParams=new HttpParams();

        httpParams.put("appid",appid);
        httpParams.put("source",source);

        httpParams.put("lang",AppLanguageUtils.getCurrentLang());

        OkGo.<String>post(url)
                .tag(activity)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        activity.dismissDialog();
                        if(commonResult!=null)commonResult.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        activity.dismissDialog();
                        if(commonResult!=null)commonResult.onError(response);
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        activity.showDialog();
                    }
                });


    }


//    /**模糊查找**/
//
//    public synchronized static void getSearchFromNet(BaseKTActivity activity, String url, String str, OkGoCommonResult commonResult){
//
//
//        HttpParams httpParams=new HttpParams();
//        httpParams.put("appname",str);
//        httpParams.put("lang",AppLanguageUtils.getCurrentLang());
//        OkGo.<String>post(url)
//                .tag(activity)
//                .params(httpParams)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                       // activity.dismissDialog();
//                        if(commonResult!=null)commonResult.onSuccess(response);
//                    }
//
//                    @Override
//                    public void onError(Response<String> response) {
//                     //   activity.dismissDialog();
//                        if(commonResult!=null)commonResult.onError(response);
//                    }
//
//                    @Override
//                    public void onStart(Request<String, ? extends Request> request) {
//                     //   activity.showDialog();
//                    }
//                });
//
//
//    }





    /**查询分类列表**/


    public  static  void getCategary(BaseActivity activity,String url,int categaryid,int page,boolean isshowdialog,OkGoCommonResult commonResult){

        HttpParams httpParams=new HttpParams();
        httpParams.put("categoryid",categaryid);
        httpParams.put("lang",AppLanguageUtils.getCurrentLang());
        httpParams.put("page",page);
        httpParams.put("pageNum",10);
        OkGo.<String>post(url)
                .tag(activity)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        activity.dismissDialog();
                        if(commonResult!=null)commonResult.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        activity.dismissDialog();
                        if(commonResult!=null)commonResult.onError(response);
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        if(isshowdialog)activity.showDialog();
                    }
                });



    }





    /**获取自己上传的app**/


    public  static  void getSelfUploadApps(BaseActivity activity,String url,String address,OkGoCommonResult commonResult){

        HttpParams httpParams=new HttpParams();
        httpParams.put("account",address);
        httpParams.put("lang",AppLanguageUtils.getCurrentLang());
        OkGo.<String>post(url)
                .tag(activity)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        activity.dismissDialog();
                        if(commonResult!=null)commonResult.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        activity.dismissDialog();
                        if(commonResult!=null)commonResult.onError(response);
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        activity.showDialog();
                    }
                });


    }


    /**获取显示评论**/

    public static void getAllComment(BaseActivity activity,String url,int appid,OkGoCommonResult commonResult){


        HttpParams httpParams=new HttpParams();
        httpParams.put("appid",appid);
       // httpParams.put("lang",AppLanguageUtils.getCurrentLang());
        OkGo.<String>post(url)
                .tag(activity)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        activity.dismissDialog();
                        if(commonResult!=null)commonResult.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        activity.dismissDialog();
                        if(commonResult!=null)commonResult.onError(response);
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        activity.showDialog();
                    }
                });

    }




    /**设置评论**/

    public static void SetComment(BaseActivity activity,String url,String account,int appid,String content,String model,OkGoCommonResult commonResult){


        HttpParams httpParams=new HttpParams();
        httpParams.put("appid",appid);
        httpParams.put("commenttext",content);
        httpParams.put("account",account);
        httpParams.put("model",model);

     //   httpParams.put("lang",AppLanguageUtils.getCurrentLang());
        OkGo.<String>post(url)
                .tag(activity)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        activity.dismissDialog();
                        if(commonResult!=null)commonResult.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        activity.dismissDialog();
                        if(commonResult!=null)commonResult.onError(response);
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        activity.showDialog();
                    }
                });

    }



    /**点赞**/

    public static void ZanComment(BaseActivity activity,String url,int appid,int commentid,OkGoCommonResult commonResult){


        HttpParams httpParams=new HttpParams();
        httpParams.put("appid",appid);
        httpParams.put("commentid",commentid);

        OkGo.<String>post(url)
                .tag(activity)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        activity.dismissDialog();
                        if(commonResult!=null)commonResult.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        activity.dismissDialog();
                        if(commonResult!=null)commonResult.onError(response);
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        activity.showDialog();
                    }
                });

    }



    /**获取所有app列表**/





    public  static  void getAllApps(Activity activity,String url,int page,OkGoCommonResult commonResult){

        HttpParams httpParams=new HttpParams();
        httpParams.put("lang",AppLanguageUtils.getCurrentLang());
        httpParams.put("page",page);
        httpParams.put("pageNum",10);
        OkGo.<String>post(url)
                .tag(activity)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {


                        if(commonResult!=null)commonResult.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {

                        if(commonResult!=null)commonResult.onError(response);
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {

                    }
                });



    }



    public static void getAppVersionInfo(Activity activity,String url,OkGoCommonResult commonResult){
        HttpParams httpParams=new HttpParams();
        OkGo.<String>post(url)
                .tag(activity)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {


                        if(commonResult!=null)commonResult.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {

                        if(commonResult!=null)commonResult.onError(response);
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {

                    }
                });


    }


    public interface OkGoCallBack{

        void onSuccess(Response<String> response);
        void onError(Response<String> response);
        void onloadProgress(Progress progress);



    }


    public interface OkGoFileCallBack{

        void onSuccess(Response<File> response);
        void onError(Response<File> response);
        void onloadProgress(Progress progress);



    }



    public interface OkGoCommonResult{

        void onSuccess(Response<String> response);
        void onError(Response<String> response);


    }



    public static int getRandom(int min,int max){

        Random random = new Random();
        int num = random.nextInt(max)%(max-min+1) + min;
        return num;

    }



}
