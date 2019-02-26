package android.com.bitchat.utils;

import com.cjwsc.idcm.Utils.ACacheUtil;
import com.cjwsc.idcm.base.application.BaseApplication;
import com.cjwsc.idcm.constant.AcacheKeys;
import com.cjwsc.idcm.language.LanguageSettingBean;
import com.orhanobut.logger.Logger;

public class AppLanguageUtils {


    public static String getCurrentLang(){

        LanguageSettingBean languageSettingBean = (LanguageSettingBean) ACacheUtil.get(BaseApplication.getContext()).getAsObject(AcacheKeys.LANGUAGELOCALE);
        if(languageSettingBean!=null){
            languageSettingBean.getLocale();
            Logger.d("------languageSettingBean.getLocale()---->"+languageSettingBean.getLocale()+"  "+languageSettingBean.getName());
        }

        String lang=languageSettingBean.getLocale().toString();
        if(lang.contains("_")){
            lang=lang.substring(0,lang.indexOf("_"));
        }

        return lang;

    }
}
