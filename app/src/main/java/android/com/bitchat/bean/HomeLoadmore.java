package android.com.bitchat.bean;

import java.util.List;

public class HomeLoadmore {


    /**
     * code : 0
     * message : 成功
     * result : [{"appId":21,"developer":"WEB","source":"android","categary":"14","categaryName":"新闻阅读","size":"5205014","version":"6.6.4","appicon":"https://cmccoin.io/skynet-20181127070538b4af84fbcccc5.png","appname":"今日头条极速版","recommendlevel":"0","downcount":10,"commentcount":0,"downloadurl":"https://cmccoin.io/skynet-201811270704455bcdf8e0298e2.apk","ctime":"2018-11-27 22:07:15","utime":"2018-11-27 22:07:15"},{"appId":16,"developer":"","source":"android","categary":"8","categaryName":"区块链","size":"12363995","version":"5.0.1","appicon":"https://cmccoin.io/skynet-20181126102553cc36dbcc21869.png","appname":"比特币钱包","recommendlevel":"0","downcount":0,"commentcount":0,"downloadurl":"https://cmccoin.io/skynet-2018112610265237433808282a8.apk","ctime":"2018-11-27 01:29:13","utime":"2018-11-27 01:29:13"},{"appId":18,"developer":"WEB","source":"android","categary":"19","categaryName":"购物比价","size":"18683143","version":"4.31.0","appicon":"https://cmccoin.io/skynet-201811270352040ec40f44a7a66.png","appname":"拼多多","recommendlevel":"0","downcount":0,"commentcount":0,"downloadurl":"https://cmccoin.io/skynet-2018112703510622095265884c9.apk","ctime":"2018-11-27 18:52:10","utime":"2018-11-27 18:52:10"},{"appId":19,"developer":"WEB","source":"android","categary":"11","categaryName":"实用工具","size":"16803529","version":"4.3.20","appicon":"https://cmccoin.io/skynet-201811270413556b5eb28647d5b.png","appname":"WiFi万能钥匙 ","recommendlevel":"0","downcount":0,"commentcount":10,"downloadurl":"https://cmccoin.io/skynet-2018112704121255480498bccf1.apk","ctime":"2018-11-27 19:13:59","utime":"2018-11-27 19:13:59"},{"appId":29,"developer":"","source":"android","categary":"11","categaryName":"实用工具","size":"20842506","version":"3.1.2","appicon":"https://cmccoin.io/skynet-201811280009390c8df7c2c289b.png","appname":"APP应用市场","recommendlevel":"0","downcount":0,"commentcount":0,"downloadurl":"https://cmccoin.io/skynet-20181128001218095ae3e879dcf.apk","ctime":"2018-11-28 15:13:16","utime":"2018-11-28 15:13:16"},{"appId":31,"developer":"WEB","source":"android","categary":"10","categaryName":"影音娱乐","size":"20381210","version":"5.1.2","appicon":"https://cmccoin.io/icon/skynet-20181128073935e29adf60f5696/icon.png","appname":"Netflix","recommendlevel":"0","downcount":0,"commentcount":0,"downloadurl":"https://cmccoin.io/application/vnd.android.package-archive/skynet-20181128073935e29adf60f5696.apk","ctime":"2018-11-28 22:41:43","utime":"2018-11-28 22:41:43"},{"appId":32,"developer":"WEB","source":"android","categary":"10","categaryName":"影音娱乐","size":"18167492","version":"8.5","appicon":"https://cmccoin.io/icon/skynet-201811280755355df0a4c4a3215/icon.png","appname":"Pandora","recommendlevel":"0","downcount":0,"commentcount":0,"downloadurl":"https://cmccoin.io/application/vnd.android.package-archive/skynet-201811280755355df0a4c4a3215.apk","ctime":"2018-11-28 22:56:41","utime":"2018-11-28 22:56:41"},{"appId":33,"developer":"WEB","source":"android","categary":"10","categaryName":"影音娱乐","size":"38347753","version":"8.4.4.810","appicon":"https://cmccoin.io/icon/skynet-201811280818082401c307f7333/icon.png","appname":"Spotify","recommendlevel":"0","downcount":0,"commentcount":0,"downloadurl":"https://cmccoin.io/application/vnd.android.package-archive/skynet-201811280818082401c307f7333.apk","ctime":"2018-11-28 23:19:39","utime":"2018-11-28 23:19:39"},{"appId":34,"developer":"WEB","source":"android","categary":"8","categaryName":"区块链","size":"14856958","version":"3.3.1","appicon":"https://cmccoin.io/icon/skynet-2018112808492896ca1abda232d/icon.png","appname":"火币 Global","recommendlevel":"0","downcount":0,"commentcount":0,"downloadurl":"https://cmccoin.io/application/vnd.android.package-archive/skynet-2018112808492896ca1abda232d.apk","ctime":"2018-11-28 23:50:38","utime":"2018-11-28 23:50:38"},{"appId":35,"developer":"WEB","source":"android","categary":"8","categaryName":"区块链","size":"17754241","version":"1.2.9","appicon":"https://cmccoin.io/icon/skynet-20181128091055db028fc2ab47d/ic_launcher.png","appname":"imToken","recommendlevel":"0","downcount":0,"commentcount":0,"downloadurl":"https://cmccoin.io/application/vnd.android.package-archive/skynet-20181128091055db028fc2ab47d.apk","ctime":"2018-11-29 00:13:03","utime":"2018-11-29 00:13:03"}]
     */

    private int code;
    private String message;
    private List<ResultBeanLoad> result;

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

    public List<ResultBeanLoad> getResult() {
        return result;
    }

    public void setResult(List<ResultBeanLoad> result) {
        this.result = result;
    }

    public static class ResultBeanLoad {
        /**
         * appId : 21
         * developer : WEB
         * source : android
         * categary : 14
         * categaryName : 新闻阅读
         * size : 5205014
         * version : 6.6.4
         * appicon : https://cmccoin.io/skynet-20181127070538b4af84fbcccc5.png
         * appname : 今日头条极速版
         * recommendlevel : 0
         * downcount : 10
         * commentcount : 0
         * downloadurl : https://cmccoin.io/skynet-201811270704455bcdf8e0298e2.apk
         * ctime : 2018-11-27 22:07:15
         * utime : 2018-11-27 22:07:15
         */

        private int appId;
        private String developer;
        private String source;
        private String categary;
        private String categaryName;
        private String size;
        private String version;
        private String appicon;
        private String appname;
        private String recommendlevel;
        private int downcount;
        private int commentcount;
        private String downloadurl;
        private String ctime;
        private String utime;

        public int getAppId() {
            return appId;
        }

        public void setAppId(int appId) {
            this.appId = appId;
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

        public String getRecommendlevel() {
            return recommendlevel;
        }

        public void setRecommendlevel(String recommendlevel) {
            this.recommendlevel = recommendlevel;
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

        public String getDownloadurl() {
            return downloadurl;
        }

        public void setDownloadurl(String downloadurl) {
            this.downloadurl = downloadurl;
        }

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getUtime() {
            return utime;
        }

        public void setUtime(String utime) {
            this.utime = utime;
        }
    }
}
