package com.sudoku.logicmaths.Utils;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageUtil {

    public static void setloacle(Activity activity, String languageCode) {
        Locale locale=new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources=activity.getResources();
        Configuration config=resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
        SharedPrefrenceConfig.saveStringPreferance(activity.getApplicationContext(), Constance.languagecode,languageCode);
    }
}
