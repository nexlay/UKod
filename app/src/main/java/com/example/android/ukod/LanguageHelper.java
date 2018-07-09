package com.example.android.ukod;

import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageHelper {
    public static void changeLocale (Resources res, String locale){
        Configuration configuration = new Configuration(res.getConfiguration());
        switch (locale){
            case "pl":
                configuration.locale = new Locale("pl");
                break;
            case "uk":
                configuration.locale = new Locale("uk");
                break;
                default:
                    configuration.locale = Locale.ENGLISH;
                    break;
        }
        res.updateConfiguration(configuration, res.getDisplayMetrics());

    }
}
