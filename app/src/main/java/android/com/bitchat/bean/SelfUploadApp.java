package android.com.bitchat.bean;

import java.io.Serializable;
import java.util.List;

public class SelfUploadApp implements Serializable {


    /**
     * code : 0
     * message : 成功
     * result : [{"app_id":24,"developer":"","source":"android","categary":"9","categaryName":"游戏","size":"34506827","version":"1.2.3","appicon":"https://cmccoin.io/skynet-201811202012298dccb95634b2a.png","appname":"元气骑士","update":"","tag":"","recommendlevel":"0","associate":"","downcount":0,"commentcount":0,"account":"0xe3606647073a7dbdc562fd8a117e498b1fab63a1","downloadurl":"https://cmccoin.io/skynet-20181120201438b61c2a27ef30c.apk","c_time":"2018-11-21 11:15:52","u_time":"2018-11-21 11:15:52"}]
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
         * app_id : 24
         * developer :
         * source : android
         * categary : 9
         * categaryName : 游戏
         * size : 34506827
         * version : 1.2.3
         * appicon : https://cmccoin.io/skynet-201811202012298dccb95634b2a.png
         * appname : 元气骑士
         * update :
         * tag :
         * recommendlevel : 0
         * associate :
         * downcount : 0
         * commentcount : 0
         * account : 0xe3606647073a7dbdc562fd8a117e498b1fab63a1
         * downloadurl : https://cmccoin.io/skynet-20181120201438b61c2a27ef30c.apk
         * c_time : 2018-11-21 11:15:52
         * u_time : 2018-11-21 11:15:52
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
