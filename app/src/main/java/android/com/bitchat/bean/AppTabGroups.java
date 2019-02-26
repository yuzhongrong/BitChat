package android.com.bitchat.bean;

import java.util.List;

public class AppTabGroups {


    /**
     * code : 0
     * message : 获取成功
     * result : [{"appid":78,"appsize":"6553552","version":"2.1","appicon":"https://cmccoin.io/image/webp/skynet-20181209075314c86ebc43b0829.webp","appname":"CamScanner - Phone PDF Creator","downcount":0,"columnId":"3"},{"appid":77,"appsize":"19895608","version":"2.18.1","appicon":"https://cmccoin.io/icon/skynet-201812090745192fb8e34e9fa49/ic_launcher.png","appname":"Quizlet: Learn Languages & Vocab with Flashcards","downcount":0,"columnId":"3"},{"appid":76,"appsize":"15446524","version":"5.42","appicon":"https://cmccoin.io/icon/skynet-2018120907382957a6af89d85f9/ic_launcher.png","appname":"StudyBlue Flashcards & Quizzes","downcount":0,"columnId":"3"},{"appid":75,"appsize":"27294823","version":"1.3.0","appicon":"https://cmccoin.io/icon/skynet-201812090736526020f996b6fd7/ic_launcher.png","appname":"Periodic Table","downcount":0,"columnId":"3"},{"appid":74,"appsize":"73916168","version":"16.0.8241.1769","appicon":"https://cmccoin.io/icon/skynet-201812090733021b4c081a73079/icon.png","appname":"Microsoft OneNote","downcount":0,"columnId":"3"},{"appid":73,"appsize":"18246881","version":"1.15.1","appicon":"https://cmccoin.io/icon/skynet-20181209072405d64be288dbdff/ic_launcher.png","appname":"Mendeley","downcount":0,"columnId":"3"},{"appid":72,"appsize":"22528418","version":"6.1.2","appicon":"https://cmccoin.io/icon/skynet-201812090722360c0f2c143a77c/icon.png","appname":"My Study Life - School Planner","downcount":0,"columnId":"3"},{"appid":71,"appsize":"25933923","version":"3.1.2","appicon":"https://cmccoin.io/icon/skynet-201812090719146d390204b37d8/ic_launcher.png","appname":"Photomath","downcount":0,"columnId":"3"},{"appid":70,"appsize":"8382434","version":"2.8.1","appicon":"https://cmccoin.io/icon/skynet-20181209071539fbbd0d26ed07d/ic_launcher.png","appname":"edX - Online Courses by Harvard, MIT & more","downcount":0,"columnId":"3"},{"appid":69,"appsize":"45482541","version":"7.2.113","appicon":"https://cmccoin.io/icon/skynet-20181209071229f11a776ac11b8/icon.png","appname":"Ready4 SAT (Prep4 SAT)","downcount":0,"columnId":"3"}]
     */

    private int code;
    private String message;
    private List<HomeDataBean.ResultBean.DatasBean.AppsBean> result;

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

    public List<HomeDataBean.ResultBean.DatasBean.AppsBean> getResult() {
        return result;
    }

    public void setResult(List<HomeDataBean.ResultBean.DatasBean.AppsBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * appid : 78
         * appsize : 6553552
         * version : 2.1
         * appicon : https://cmccoin.io/image/webp/skynet-20181209075314c86ebc43b0829.webp
         * appname : CamScanner - Phone PDF Creator
         * downcount : 0
         * columnId : 3
         */

        private int appid;
        private String appsize;
        private String version;
        private String appicon;
        private String appname;
        private int downcount;
        private String columnId;

        public int getAppid() {
            return appid;
        }

        public void setAppid(int appid) {
            this.appid = appid;
        }

        public String getAppsize() {
            return appsize;
        }

        public void setAppsize(String appsize) {
            this.appsize = appsize;
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

        public int getDowncount() {
            return downcount;
        }

        public void setDowncount(int downcount) {
            this.downcount = downcount;
        }

        public String getColumnId() {
            return columnId;
        }

        public void setColumnId(String columnId) {
            this.columnId = columnId;
        }
    }
}
