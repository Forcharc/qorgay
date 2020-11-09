package kz.kmg.qorgau.di;

import android.app.Application;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import kz.kmg.qorgau.data.local.LocalStorage;
import kz.kmg.qorgau.data.local.SharedPrefStorage;

@Module
public class AppModule {

    @Provides
    @Singleton
    public LocalStorage provideLocalStorage(Application context) {
        return new SharedPrefStorage(context);
    }

}
