package android.com.bitchat.bean;

public class UploadBean {


    /**
     * code : 0
     * message : 上传成功
     * result : {"size":306498,"url":"http://cmccoin.io/uploads/skynet-20181111101820abe3f276be55b.png","name":"skynet-20181111101820abe3f276be55b.png","type":"image/png"}
     */

    private int code;
    private String message;
    private ResultBean result;

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

    public static class ResultBean {
        /**
         * size : 306498
         * url : http://cmccoin.io/uploads/skynet-20181111101820abe3f276be55b.png
         * name : skynet-20181111101820abe3f276be55b.png
         * type : image/png
         */

        private int size;
        private String url;
        private String name;
        private String type;

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
