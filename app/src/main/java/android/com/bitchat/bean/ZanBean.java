package android.com.bitchat.bean;

import java.io.Serializable;

public class ZanBean  implements Serializable{


    /**
     * code : 0
     * message : 更新成功
     * result : {"appid":34,"commentid":8,"praisecount":2}
     * resultUpdate : {"fieldCount":0,"affectedRows":1,"insertId":0,"info":"Rows matched: 1 Changed: 1 Warnings: 0","serverStatus":2,"warningStatus":0,"changedRows":1}
     */

    private int code;
    private String message;
    private ResultBean result;
    private ResultUpdateBean resultUpdate;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public ResultUpdateBean getResultUpdate() {
        return resultUpdate;
    }

    public void setResultUpdate(ResultUpdateBean resultUpdate) {
        this.resultUpdate = resultUpdate;
    }

    public static class ResultBean {
        /**
         * appid : 34
         * commentid : 8
         * praisecount : 2
         */

        private int appid;
        private int commentid;
        private int praisecount;

        public int getAppid() {
            return appid;
        }

        public void setAppid(int appid) {
            this.appid = appid;
        }

        public int getCommentid() {
            return commentid;
        }

        public void setCommentid(int commentid) {
            this.commentid = commentid;
        }

        public int getPraisecount() {
            return praisecount;
        }

        public void setPraisecount(int praisecount) {
            this.praisecount = praisecount;
        }
    }

    public static class ResultUpdateBean {
        /**
         * fieldCount : 0
         * affectedRows : 1
         * insertId : 0
         * info : Rows matched: 1 Changed: 1 Warnings: 0
         * serverStatus : 2
         * warningStatus : 0
         * changedRows : 1
         */

        private int fieldCount;
        private int affectedRows;
        private int insertId;
        private String info;
        private int serverStatus;
        private int warningStatus;
        private int changedRows;

        public int getFieldCount() {
            return fieldCount;
        }

        public void setFieldCount(int fieldCount) {
            this.fieldCount = fieldCount;
        }

        public int getAffectedRows() {
            return affectedRows;
        }

        public void setAffectedRows(int affectedRows) {
            this.affectedRows = affectedRows;
        }

        public int getInsertId() {
            return insertId;
        }

        public void setInsertId(int insertId) {
            this.insertId = insertId;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getServerStatus() {
            return serverStatus;
        }

        public void setServerStatus(int serverStatus) {
            this.serverStatus = serverStatus;
        }

        public int getWarningStatus() {
            return warningStatus;
        }

        public void setWarningStatus(int warningStatus) {
            this.warningStatus = warningStatus;
        }

        public int getChangedRows() {
            return changedRows;
        }

        public void setChangedRows(int changedRows) {
            this.changedRows = changedRows;
        }
    }
}
