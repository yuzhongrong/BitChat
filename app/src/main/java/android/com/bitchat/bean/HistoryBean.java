package android.com.bitchat.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

public class HistoryBean extends LitePalSupport implements Serializable {

    private String showtype;



    private List<SearchHistory> datas;


    public HistoryBean(String showtype,List<SearchHistory> datas) {
        this.showtype = showtype;
        this.datas=datas;
    }

    public String getShowtype() {
        return showtype;
    }

    public void setShowtype(String showtype) {
        this.showtype = showtype;
    }

    public List<SearchHistory> getDatas() {
        return datas;
    }

    public void setDatas(List<SearchHistory> datas) {
        this.datas = datas;
    }
}
