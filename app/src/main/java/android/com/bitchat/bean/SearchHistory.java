package android.com.bitchat.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;
import java.util.List;

public class SearchHistory  extends LitePalSupport implements Serializable {

    private String showtype;
    private String history;
    private String address;



    private String lang;

    public SearchHistory(String address,String showtype,String history,String lang) {
        this.showtype = showtype;
        this.history=history;
        this.address=address;
        this.lang=lang;
    }

    public String getShowtype() {
        return showtype;
    }

    public void setShowtype(String showtype) {
        this.showtype = showtype;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
