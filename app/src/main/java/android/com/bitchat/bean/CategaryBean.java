package android.com.bitchat.bean;

import java.util.List;

public class CategaryBean {


    /**
     * code : 0
     * message : 获取成功
     * result : [{"categoryId":c_9,"categoryName":"游戏"},{"categoryId":c_10,"categoryName":"影音娱乐"},{"categoryId":c_11,"categoryName":"实用工具"},{"categoryId":c_12,"categoryName":"社交通讯"},{"categoryId":c_13,"categoryName":"教育"},{"categoryId":c_14,"categoryName":"新闻阅读"},{"categoryId":c_15,"categoryName":"拍摄美化"},{"categoryId":16,"categoryName":"美食"},{"categoryId":17,"categoryName":"出行导航"},{"categoryId":18,"categoryName":"旅游住宿"},{"categoryId":19,"categoryName":"购物比价"},{"categoryId":20,"categoryName":"商务"},{"categoryId":21,"categoryName":"儿童"},{"categoryId":22,"categoryName":"金融理财"},{"categoryId":23,"categoryName":"运动健康"},{"categoryId":24,"categoryName":"便捷生活"},{"categoryId":25,"categoryName":"汽车"},{"categoryId":26,"categoryName":"主题个性"}]
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
         * categoryId : c_9
         * categoryName : 游戏
         */

        private int categoryId;
        private String categoryName;
        private String categoryIcon;

        public String getCategoryIcon() {
            return categoryIcon;
        }

        public void setCategoryIcon(String categoryIcon) {
            this.categoryIcon = categoryIcon;
        }



        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }
    }
}
