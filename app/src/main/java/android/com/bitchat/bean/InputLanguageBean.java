package android.com.bitchat.bean;

import java.io.Serializable;

public class InputLanguageBean implements Serializable {



    private String lang;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
