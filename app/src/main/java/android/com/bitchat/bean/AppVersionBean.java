package android.com.bitchat.bean;

import java.util.List;

public class AppVersionBean {


    /**
     * code : 0
     * info : {"packageName":"import android.com.bitchat","packageVersionName":"0.0.0.1","packageVersionCode":1,"applicationIcon":"res/mipmap-xxxhdpi-v4/ic_bitstore2.png","applicationIconUrl":"res/mipmap-xhdpi-v4/ic_bitstore2.png","applicationLabelEn":"BitStore","applicationLabelCn":"BitStore","sdkVersion":18,"targetSdkVersion":26,"usesPermission":["android.permission.ACCESS_COARSE_LOCATION","android.permission.ACCESS_FINE_LOCATION","android.permission.ACCESS_NETWORK_STATE","android.permission.ACCESS_WIFI_STATE","android.permission.BLUETOOTH","android.permission.BLUETOOTH_ADMIN","android.permission.CAMERA","android.permission.CHANGE_NETWORK_STATE","android.permission.CHANGE_WIFI_STATE","android.permission.GET_ACCOUNTS","android.permission.GET_TASKS","android.permission.INTERNET","android.permission.MANAGE_ACCOUNTS","android.permission.MODIFY_AUDIO_SETTINGS","android.permission.MOUNT_UNMOUNT_FILESYSTEMS","android.permission.READ_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE","android.permission.READ_PHONE_STATE","android.permission.RECEIVE_BOOT_COMPLETED","android.permission.RECORD_AUDIO","android.permission.RECORD_AUDIO","android.permission.REQUEST_INSTALL_PACKAGES","android.permission.VIBRATE","android.permission.WAKE_LOCK","android.permission.WRITE_EXTERNAL_STORAGE","android.permission.WRITE_SETTINGS"],"usesFeature":["android.hardware.bluetooth","android.hardware.camera","android.hardware.camera.autofocus","android.hardware.location","android.hardware.location.gps","android.hardware.location.network","android.hardware.microphone","android.hardware.screen.portrait","android.hardware.touchscreen","android.hardware.wifi"],"useImpliedFeature":["android.hardware.bluetooth,requested android.permission.BLUETOOTH or android.permission.BLUETOOTH_ADMIN permission and targetSdkVersion > 4","android.hardware.location,requested a location access permission","android.hardware.location.gps,requested android.permission.ACCESS_FINE_LOCATION permission","android.hardware.location.network,requested android.permission.ACCESS_COURSE_LOCATION permission","android.hardware.microphone,requested android.permission.RECORD_AUDIO permission","android.hardware.screen.portrait,one or more activities have specified a portrait orientation","android.hardware.touchscreen,assumed you require a touch screen unless explicitly made optional","android.hardware.wifi,requested android.permission.ACCESS_WIFI_STATE, android.permission.CHANGE_WIFI_STATE, or android.permission.CHANGE_WIFI_MULTICAST_STATE permission"]}
     * versioncode : 0.0.0.1
     * sizePackage : 1
     * downloadurl : https://cmccoin.io/bitstore/BitStore.apk
     * message : 成功
     */

    private int code;
    private InfoBean info;
    private String versioncode;
    private int sizePackage;
    private String downloadurl;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(String versioncode) {
        this.versioncode = versioncode;
    }

    public int getSizePackage() {
        return sizePackage;
    }

    public void setSizePackage(int sizePackage) {
        this.sizePackage = sizePackage;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class InfoBean {
        /**
         * packageName : import android.com.bitchat
         * packageVersionName : 0.0.0.1
         * packageVersionCode : 1
         * applicationIcon : res/mipmap-xxxhdpi-v4/ic_bitstore2.png
         * applicationIconUrl : res/mipmap-xhdpi-v4/ic_bitstore2.png
         * applicationLabelEn : BitStore
         * applicationLabelCn : BitStore
         * sdkVersion : 18
         * targetSdkVersion : 26
         * usesPermission : ["android.permission.ACCESS_COARSE_LOCATION","android.permission.ACCESS_FINE_LOCATION","android.permission.ACCESS_NETWORK_STATE","android.permission.ACCESS_WIFI_STATE","android.permission.BLUETOOTH","android.permission.BLUETOOTH_ADMIN","android.permission.CAMERA","android.permission.CHANGE_NETWORK_STATE","android.permission.CHANGE_WIFI_STATE","android.permission.GET_ACCOUNTS","android.permission.GET_TASKS","android.permission.INTERNET","android.permission.MANAGE_ACCOUNTS","android.permission.MODIFY_AUDIO_SETTINGS","android.permission.MOUNT_UNMOUNT_FILESYSTEMS","android.permission.READ_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE","android.permission.READ_PHONE_STATE","android.permission.RECEIVE_BOOT_COMPLETED","android.permission.RECORD_AUDIO","android.permission.RECORD_AUDIO","android.permission.REQUEST_INSTALL_PACKAGES","android.permission.VIBRATE","android.permission.WAKE_LOCK","android.permission.WRITE_EXTERNAL_STORAGE","android.permission.WRITE_SETTINGS"]
         * usesFeature : ["android.hardware.bluetooth","android.hardware.camera","android.hardware.camera.autofocus","android.hardware.location","android.hardware.location.gps","android.hardware.location.network","android.hardware.microphone","android.hardware.screen.portrait","android.hardware.touchscreen","android.hardware.wifi"]
         * useImpliedFeature : ["android.hardware.bluetooth,requested android.permission.BLUETOOTH or android.permission.BLUETOOTH_ADMIN permission and targetSdkVersion > 4","android.hardware.location,requested a location access permission","android.hardware.location.gps,requested android.permission.ACCESS_FINE_LOCATION permission","android.hardware.location.network,requested android.permission.ACCESS_COURSE_LOCATION permission","android.hardware.microphone,requested android.permission.RECORD_AUDIO permission","android.hardware.screen.portrait,one or more activities have specified a portrait orientation","android.hardware.touchscreen,assumed you require a touch screen unless explicitly made optional","android.hardware.wifi,requested android.permission.ACCESS_WIFI_STATE, android.permission.CHANGE_WIFI_STATE, or android.permission.CHANGE_WIFI_MULTICAST_STATE permission"]
         */

        private String packageName;
        private String packageVersionName;
        private int packageVersionCode;
        private String applicationIcon;
        private String applicationIconUrl;
        private String applicationLabelEn;
        private String applicationLabelCn;
        private int sdkVersion;
        private int targetSdkVersion;
        private List<String> usesPermission;
        private List<String> usesFeature;
        private List<String> useImpliedFeature;

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getPackageVersionName() {
            return packageVersionName;
        }

        public void setPackageVersionName(String packageVersionName) {
            this.packageVersionName = packageVersionName;
        }

        public int getPackageVersionCode() {
            return packageVersionCode;
        }

        public void setPackageVersionCode(int packageVersionCode) {
            this.packageVersionCode = packageVersionCode;
        }

        public String getApplicationIcon() {
            return applicationIcon;
        }

        public void setApplicationIcon(String applicationIcon) {
            this.applicationIcon = applicationIcon;
        }

        public String getApplicationIconUrl() {
            return applicationIconUrl;
        }

        public void setApplicationIconUrl(String applicationIconUrl) {
            this.applicationIconUrl = applicationIconUrl;
        }

        public String getApplicationLabelEn() {
            return applicationLabelEn;
        }

        public void setApplicationLabelEn(String applicationLabelEn) {
            this.applicationLabelEn = applicationLabelEn;
        }

        public String getApplicationLabelCn() {
            return applicationLabelCn;
        }

        public void setApplicationLabelCn(String applicationLabelCn) {
            this.applicationLabelCn = applicationLabelCn;
        }

        public int getSdkVersion() {
            return sdkVersion;
        }

        public void setSdkVersion(int sdkVersion) {
            this.sdkVersion = sdkVersion;
        }

        public int getTargetSdkVersion() {
            return targetSdkVersion;
        }

        public void setTargetSdkVersion(int targetSdkVersion) {
            this.targetSdkVersion = targetSdkVersion;
        }

        public List<String> getUsesPermission() {
            return usesPermission;
        }

        public void setUsesPermission(List<String> usesPermission) {
            this.usesPermission = usesPermission;
        }

        public List<String> getUsesFeature() {
            return usesFeature;
        }

        public void setUsesFeature(List<String> usesFeature) {
            this.usesFeature = usesFeature;
        }

        public List<String> getUseImpliedFeature() {
            return useImpliedFeature;
        }

        public void setUseImpliedFeature(List<String> useImpliedFeature) {
            this.useImpliedFeature = useImpliedFeature;
        }
    }
}
