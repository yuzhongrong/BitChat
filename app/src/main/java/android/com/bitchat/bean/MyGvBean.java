package android.com.bitchat.bean;

public class MyGvBean {

    private String txt;
    private int icon;

    public MyGvBean( int icon) {
        this.icon=icon;
    }


    public MyGvBean(String text, int icon) {
        this.icon=icon;
        this.txt=text;
    }
    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
