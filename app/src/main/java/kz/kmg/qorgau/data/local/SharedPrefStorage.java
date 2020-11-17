package kz.kmg.qorgau.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import javax.inject.Singleton;


@Singleton
public class SharedPrefStorage implements LocalStorage {

    SharedPreferences preferences;

    private static final String PREF_NAME = "KMG_QORGAU_APP";

    private static final String KEY_NOTIFICATIONS_TOKEN = "KEY_NOTIFICATIONS_TOKEN";

    public SharedPrefStorage(Context context) {
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void setNotificationToken(String notificationToken) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NOTIFICATIONS_TOKEN, notificationToken);
        editor.apply();
    }

    @Override
    public String getNotificationToken() {
        return preferences.getString(KEY_NOTIFICATIONS_TOKEN, null);
    }
}
