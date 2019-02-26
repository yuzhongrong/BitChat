package android.com.bitchat.bean;

import java.io.Serializable;
import java.util.List;

public class AppDetailBean implements Serializable {


    /**
     * code : 0
     * message : 请求成功
     * result : [{"c_time":"2018-c_11-16 07:37:47","u_time":"2018-c_11-17 c_15:25:05","app_id":c_12,"developer":"","source":"android","categary":"10","size":"192857","version":"6.0.3","appicon":"https://cmccoin.io/skynet-2018111700155312e482be40b52.png","appname":"唯品会","update":"","tag":"","recommendlevel":"8","associate":"","downcount":4546,"commentcount":46,"introduce":"http://cmccoin.io/skynet-2018111516350771e6eba750c32.jpg,http://cmccoin.io/skynet-201811151635095db8ceb61cdd4.jpg,http://cmccoin.io/skynet-20181115163513639d06415e5a9.jpg,http://cmccoin.io/skynet-2018111516351562d18002b6e82.jpg,http://cmccoin.io/skynet-201811151635188adc0cd0d7f7a.jpg","account":"0x62ad54a292e4141e3497191f8d624c968f8ff142","downloadurl":"http://cmccoin.io/skynet-201811151637171b15575a27ec7.apk"}]
     */

    private int code;
    private String message;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * c_time : 2018-c_11-16 07:37:47
         * u_time : 2018-c_11-17 c_15:25:05
         * app_id : c_12
         * developer :
         * source : android
         * categary : 10
         * size : 192857
         * version : 6.0.3
         * appicon : https://cmccoin.io/skynet-2018111700155312e482be40b52.png
         * appname : 唯品会
         * update :
         * tag :
         * recommendlevel : 8
         * associate :
         * downcount : 4546
         * commentcount : 46
         * introduce : http://cmccoin.io/skynet-2018111516350771e6eba750c32.jpg,http://cmccoin.io/skynet-201811151635095db8ceb61cdd4.jpg,http://cmccoin.io/skynet-20181115163513639d06415e5a9.jpg,http://cmccoin.io/skynet-2018111516351562d18002b6e82.jpg,http://cmccoin.io/skynet-201811151635188adc0cd0d7f7a.jpg
         * account : 0x62ad54a292e4141e3497191f8d624c968f8ff142
         * downloadurl : http://cmccoin.io/skynet-201811151637171b15575a27ec7.apk
         */

        private String c_time;
        private String u_time;
        private int app_id;
        private String developer;
        private String source;
        private String categary;
        private String size;
        private String version;
        private String appicon;
        private String appname;
        private String update;
        private String tag;
        private String recommendlevel;
        private String associate;
        private int downcount;
        private int commentcount;
        private String introduce;
        private String account;
        private String downloadurl;

        public String getC_time() {
            return c_time;
        }

        public void setC_time(String c_time) {
            this.c_time = c_time;
        }

        public String getU_time() {
            return u_time;
        }

        public void setU_time(String u_time) {
            this.u_time = u_time;
        }

        public int getApp_id() {
            return app_id;
        }

        public void setApp_id(int app_id) {
            this.app_id = app_id;
        }

        public String getDeveloper() {
            return developer;
        }

        public void setDeveloper(String developer) {
            this.developer = developer;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getCategary() {
            return categary;
        }

        public void setCategary(String categary) {
            this.categary = categary;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getAppicon() {
            return appicon;
        }

        public void setAppicon(String appicon) {
            this.appicon = appicon;
        }

        public String getAppname() {
            return appname;
        }

        public void setAppname(String appname) {
            this.appname = appname;
        }

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getRecommendlevel() {
            return recommendlevel;
        }

        public void setRecommendlevel(String recommendlevel) {
            this.recommendlevel = recommendlevel;
        }

        public String getAssociate() {
            return associate;
        }

        public void setAssociate(String associate) {
            this.associate = associate;
        }

        public int getDowncount() {
            return downcount;
        }

        public void setDowncount(int downcount) {
            this.downcount = downcount;
        }

        public int getCommentcount() {
            return commentcount;
        }

        public void setCommentcount(int commentcount) {
            this.commentcount = commentcount;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getDownloadurl() {
            return downloadurl;
        }

        public void setDownloadurl(String downloadurl) {
            this.downloadurl = downloadurl;
        }
    }
}
