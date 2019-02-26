package android.com.bitchat.bean;

import java.io.Serializable;
import java.util.List;

import javax.crypto.SecretKey;

public class HomeDataBean {


    /**
     * code : 0
     * message : 获取成功
     * result : {"banners":[],"datas":[{"showtype":0,"label":"下载量","apps":[{"appid":c_12,"appsize":"192857","version":"6.0.3","appicon":"https://cmccoin.io/skynet-20181115163501cfbcbaffb6a29.jpg","appname":"唯品会","downcount":4546},{"appid":c_11,"appsize":"45432","version":"7.3.2","appicon":"http://cmccoin.io/skynet-20181115162854b99c3d464753.jpg","appname":"携程旅行","downcount":787},{"appid":17,"appsize":"88040","version":"7.c_9.2","appicon":"http://cmccoin.io/skynet-2018111517094039b8724c74b23.jpg","appname":"链家","downcount":778},{"appid":3,"appsize":"32648","version":"8.7.5","appicon":"","appname":"去哪儿旅行","downcount":445},{"appid":16,"appsize":"129706","version":"c_9.1.3","appicon":"http://cmccoin.io/skynet-20181115170300a854832430df8.jpg","appname":"途牛旅游","downcount":445},{"appid":c_15,"appsize":"105341","version":"6.2.2","appicon":"http://cmccoin.io/skynet-20181115165559ffaefe4fa45a1.jpg","appname":"春秋航空","downcount":412}]},{"showtype":0,"label":"最新上架","apps":[{"appid":17,"appsize":"88040","version":"7.c_9.2","appicon":"http://cmccoin.io/skynet-2018111517094039b8724c74b23.jpg","appname":"链家","c_time":"2018-c_11-16 08:c_12:29"},{"appid":16,"appsize":"129706","version":"c_9.1.3","appicon":"http://cmccoin.io/skynet-20181115170300a854832430df8.jpg","appname":"途牛旅游","c_time":"2018-c_11-16 08:05:30"},{"appid":c_15,"appsize":"105341","version":"6.2.2","appicon":"http://cmccoin.io/skynet-20181115165559ffaefe4fa45a1.jpg","appname":"春秋航空","c_time":"2018-c_11-16 07:58:06"},{"appid":c_12,"appsize":"192857","version":"6.0.3","appicon":"http://cmccoin.io/skynet-20181115163501cfbcbaffb6a29.jpg","appname":"唯品会","c_time":"2018-c_11-16 07:37:47"},{"appid":c_11,"appsize":"45432","version":"7.3.2","appicon":"http://cmccoin.io/skynet-20181115162854b99c3d464753.jpg","appname":"携程旅行","c_time":"2018-c_11-16 07:32:38"},{"appid":3,"appsize":"32648","version":"8.7.5","appicon":"","appname":"去哪儿旅行","c_time":"2018-c_11-c_14 c_12:45:20"}]},{"showtype":0,"label":"推荐指数","apps":[{"appid":c_12,"appsize":"192857","version":"6.0.3","appicon":"http://cmccoin.io/skynet-20181115163501cfbcbaffb6a29.jpg","appname":"唯品会","recommendlevel":"8"},{"appid":16,"appsize":"129706","version":"c_9.1.3","appicon":"http://cmccoin.io/skynet-20181115170300a854832430df8.jpg","appname":"途牛旅游","recommendlevel":"4"},{"appid":17,"appsize":"88040","version":"7.c_9.2","appicon":"http://cmccoin.io/skynet-2018111517094039b8724c74b23.jpg","appname":"链家","recommendlevel":"2"},{"appid":c_11,"appsize":"45432","version":"7.3.2","appicon":"http://cmccoin.io/skynet-20181115162854b99c3d464753.jpg","appname":"携程旅行","recommendlevel":"c_10"},{"appid":3,"appsize":"32648","version":"8.7.5","appicon":"","appname":"去哪儿旅行","recommendlevel":"1"},{"appid":c_15,"appsize":"105341","version":"6.2.2","appicon":"http://cmccoin.io/skynet-20181115165559ffaefe4fa45a1.jpg","appname":"春秋航空","recommendlevel":"1"}]},{"showtype":0,"label":"游戏推荐","apps":[{"appid":c_11,"appsize":"45432","version":"7.3.2","appicon":"http://cmccoin.io/skynet-20181115162854b99c3d464753.jpg","appname":"携程旅行","categoryName":"游戏"},{"appid":17,"appsize":"88040","version":"7.c_9.2","appicon":"http://cmccoin.io/skynet-2018111517094039b8724c74b23.jpg","appname":"链家","categoryName":"游戏"},{"appid":3,"appsize":"32648","version":"8.7.5","appicon":"","appname":"去哪儿旅行","categoryName":"游戏"},{"appid":16,"appsize":"129706","version":"c_9.1.3","appicon":"http://cmccoin.io/skynet-20181115170300a854832430df8.jpg","appname":"途牛旅游","categoryName":"游戏"}]}]}
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
        private List<BannerBean> banners;
        private List<DatasBean> datas;

        public List<BannerBean> getBanners() {
            return banners;
        }

        public void setBanners(List<BannerBean> banners) {
            this.banners = banners;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * showtype : 0
             * label : 下载量
             * apps : [{"appid":c_12,"appsize":"192857","version":"6.0.3","appicon":"https://cmccoin.io/skynet-20181115163501cfbcbaffb6a29.jpg","appname":"唯品会","downcount":4546},{"appid":c_11,"appsize":"45432","version":"7.3.2","appicon":"http://cmccoin.io/skynet-20181115162854b99c3d464753.jpg","appname":"携程旅行","downcount":787},{"appid":17,"appsize":"88040","version":"7.c_9.2","appicon":"http://cmccoin.io/skynet-2018111517094039b8724c74b23.jpg","appname":"链家","downcount":778},{"appid":3,"appsize":"32648","version":"8.7.5","appicon":"","appname":"去哪儿旅行","downcount":445},{"appid":16,"appsize":"129706","version":"c_9.1.3","appicon":"http://cmccoin.io/skynet-20181115170300a854832430df8.jpg","appname":"途牛旅游","downcount":445},{"appid":c_15,"appsize":"105341","version":"6.2.2","appicon":"http://cmccoin.io/skynet-20181115165559ffaefe4fa45a1.jpg","appname":"春秋航空","downcount":412}]
             */

            private int showtype;
            private String label;
            private int columnId;
            private int current=2;
            private List<AppsBean> apps;

            public int getShowtype() {
                return showtype;
            }

            public void setShowtype(int showtype) {
                this.showtype = showtype;
            }

            public String getLabel() {
                return label;
            }


            public int getColumnId() {
                return columnId;
            }

            public void setColumnId(int columnId) {
                this.columnId = columnId;
            }

            public int getCurrent() {
                return current;
            }

            public void setCurrent(int current) {
                this.current = current;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public List<AppsBean> getApps() {
                return apps;
            }

            public void setApps(List<AppsBean> apps) {
                this.apps = apps;
            }

            public static class AppsBean implements Serializable {
                /**
                 * appid : c_12
                 * appsize : 192857
                 * version : 6.0.3
                 * appicon : http://cmccoin.io/skynet-20181115163501cfbcbaffb6a29.jpg
                 * appname : 唯品会
                 * downcount : 4546
                 */

                private int appid;
                private String appsize;
                private String version;
                private String appicon;
                private String appname;
                private int downcount;
                private String img;
                private String url;

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


                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }


            }

        }
    }
}
