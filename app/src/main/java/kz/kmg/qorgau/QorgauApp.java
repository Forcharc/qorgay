package kz.kmg.qorgau;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import kz.kmg.qorgau.di.DaggerAppComponent;

public class QorgauApp extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

}