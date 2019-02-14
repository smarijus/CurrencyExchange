package lt.currency.exchange.common;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class AppSharedPreferences {

    public static final String KEY_NAME = "key_preference";

    private final SharedPreferences pref;

    @Inject
    public AppSharedPreferences(SharedPreferences sharedPreferences) {
        pref = sharedPreferences;
    }

}
