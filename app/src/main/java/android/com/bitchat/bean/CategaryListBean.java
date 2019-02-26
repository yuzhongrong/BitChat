package android.com.bitchat.bean;

import java.io.Serializable;
import java.util.List;

public class CategaryListBean implements Serializable {


    /**
     * code : 0
     * message : 成功
     * result : [{"app_id":11,"developer":"","source":"android","categary":"9","categaryName":"游戏","size":"45432","version":"7.3.2","appicon":"https://cmccoin.io/skynet-20181117001623b021ee61e351b.png","appname":"携程旅行","update":"","tag":"","recommendlevel":"10","associate":"","downcount":787,"commentcount":79,"account":"0x62ad54a292e4141e3497191f8d624c968f8ff142","downloadurl":"http://cmccoin.io/skynet-20181115163201a26b2281a563a.apk","c_time":"2018-11-16 07:32:38","u_time":"2018-11-16 07:32:38"},{"app_id":17,"developer":"","source":"android","categary":"9","categaryName":"游戏","size":"88040","version":"7.9.2","appicon":"https://cmccoin.io/skynet-20181117001200e9962b32df077.png","appname":"链家","update":"","tag":"","recommendlevel":"2","associate":"","downcount":778,"commentcount":7,"account":"0x62ad54a292e4141e3497191f8d624c968f8ff142","downloadurl":"http://cmccoin.io/skynet-20181115171135c905a019b3de5.apk","c_time":"2018-11-16 08:12:29","u_time":"2018-11-16 08:12:29"},{"app_id":3,"developer":"","source":"android","categary":"9","categaryName":"游戏","size":"32648","version":"8.7.5","appicon":"","appname":"去哪儿旅行","update":"","tag":"","recommendlevel":"1","associate":"","downcount":445,"commentcount":789,"account":"0x62ad54a292e4141e3497191f8d624c968f8ff142","downloadurl":"http://cmccoin.io/skynet-201811132144582d17d828f2696.apk","c_time":"2018-11-14 12:45:20","u_time":"2018-11-14 12:45:20"},{"app_id":16,"developer":"","source":"android","categary":"9","categaryName":"游戏","size":"129706","version":"9.1.3","appicon":"https://cmccoin.io/skynet-20181117001232fb2a920763a4e.png","appname":"途牛旅游","update":"","tag":"","recommendlevel":"4","associate":"","downcount":445,"commentcount":78,"account":"0x62ad54a292e4141e3497191f8d624c968f8ff142","downloadurl":"http://cmccoin.io/skynet-201811151705207e3c60cabf18f.apk","c_time":"2018-11-16 08:05:30","u_time":"2018-11-16 08:05:30"}]
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

    public static class ResultBean {
        /**
         * app_id : 11
         * developer :
         * source : android
         * categary : 9
         * categaryName : 游戏
         * size : 45432
         * version : 7.3.2
         * appicon : https://cmccoin.io/skynet-20181117001623b021ee61e351b.png
         * appname : 携程旅行
         * update :
         * tag :
         * recommendlevel : 10
         * associate :
         * downcount : 787
         * commentcount : 79
         * account : 0x62ad54a292e4141e3497191f8d624c968f8ff142
         * downloadurl : http://cmccoin.io/skynet-20181115163201a26b2281a563a.apk
         * c_time : 2018-11-16 07:32:38
         * u_time : 2018-11-16 07:32:38
         */

        private int app_id;
        private String developer;
        private String source;
        private String categary;
        private String categaryName;
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
        private String account;
        private String downloadurl;
        private String c_time;
        private String u_time;

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

        public String getCategaryName() {
            return categaryName;
        }

        public void setCategaryName(String categaryName) {
            this.categaryName = categaryName;
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
    }
}
