package kz.kmg.qorgau.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import javax.inject.Singleton;


@Singleton
public class SharedPrefStorage implements LocalStorage {

    SharedPreferences preferences;

    private static final String PREF_NAME = "KMG_QORGAU_APP";


    public SharedPrefStorage(Context context) {
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
