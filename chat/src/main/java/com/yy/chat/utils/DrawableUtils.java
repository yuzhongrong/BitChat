package com.yy.chat.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DrawableRes;

import com.cjwsc.idcm.base.application.BaseApplication;

public class DrawableUtils {


    public static String getResourcesUri(@DrawableRes int id) {
        Resources resources = BaseApplication.getContext().getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }

}
