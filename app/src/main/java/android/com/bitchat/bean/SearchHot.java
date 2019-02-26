package android.com.bitchat.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

public class SearchHot extends LitePalSupport implements Serializable {

    private String showtype;
    private List<HomeDataBean.ResultBean.DatasBean.AppsBean> datas;

    public SearchHot(String showtype, List<HomeDataBean.ResultBean.DatasBean.AppsBean> datas) {
        this.showtype = showtype;
        this.datas = datas;
    }

    public String getShowtype() {
        return showtype;
    }

    public void setShowtype(String showtype) {
        this.showtype = showtype;
    }

    public List<HomeDataBean.ResultBean.DatasBean.AppsBean> getData() {
        return datas;
    }

    public void setData(List<HomeDataBean.ResultBean.DatasBean.AppsBean> history) {
        this.datas = history;
    }
}
