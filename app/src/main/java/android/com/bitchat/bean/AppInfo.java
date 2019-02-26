package android.com.bitchat.bean;

import java.io.Serializable;

public class AppInfo implements Serializable {

    private String app_id;
    private String appicon;
    private String appname;
    private String appversion;
    private String appsize;
    private String[] appintroduces;
    private String appcategary;


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

    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }

    public String getAppsize() {
        return appsize;
    }

    public void setAppsize(String appsize) {
        this.appsize = appsize;
    }

    public String[] getAppintroduces() {
        return appintroduces;
    }

    public void setAppintroduces(String[] appintroduces) {
        this.appintroduces = appintroduces;
    }

    public String getAppcategary() {
        return appcategary;
    }

    public void setAppcategary(String appcategary) {
        this.appcategary = appcategary;
    }
}
