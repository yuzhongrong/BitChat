package android.com.bitchat.bean;

import java.io.Serializable;
import java.util.List;

public class CommentBean implements Serializable{


    /**
     * code : 0
     * message : 获取成功
     * result : [{"commentid":6,"appid":34,"model":"红米4","praisecount":0,"account":"0xf881e5b83f0c1341d909cecf777102fd746de4d3","commenttext":"这下下载火币就方便了 ","ctime":1544017815,"utime":1544017815},{"commentid":5,"appid":34,"model":"华为荣耀3","praisecount":0,"account":"0xf881e5b83f0c1341d909cecf777102fd746de4d3","commenttext":"这个应用市场很牛逼 这个app都有 ","ctime":1544017696,"utime":1544017696}]
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
         * commentid : 6
         * appid : 34
         * model : 红米4
         * praisecount : 0
         * account : 0xf881e5b83f0c1341d909cecf777102fd746de4d3
         * commenttext : 这下下载火币就方便了
         * ctime : 1544017815
         * utime : 1544017815
         */

        private int commentid;
        private int appid;
        private String model;
        private int praisecount;
        private String account;
        private String commenttext;
        private int ctime;
        private int utime;

        public int getCommentid() {
            return commentid;
        }

        public void setCommentid(int commentid) {
            this.commentid = commentid;
        }

        public int getAppid() {
            return appid;
        }

        public void setAppid(int appid) {
            this.appid = appid;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public int getPraisecount() {
            return praisecount;
        }

        public void setPraisecount(int praisecount) {
            this.praisecount = praisecount;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getCommenttext() {
            return commenttext;
        }

        public void setCommenttext(String commenttext) {
            this.commenttext = commenttext;
        }

        public int getCtime() {
            return ctime;
        }

        public void setCtime(int ctime) {
            this.ctime = ctime;
        }

        public int getUtime() {
            return utime;
        }

        public void setUtime(int utime) {
            this.utime = utime;
        }
    }
}
