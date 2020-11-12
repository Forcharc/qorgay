package kz.kmg.qorgau;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.di.DaggerAppComponent;
import retrofit2.Retrofit;

public class QorgauApp extends DaggerApplication {

    @Inject
    Retrofit retrofit;

    public QorgayApi qorgayApi;

    @Override
    public void onCreate() {
        super.onCreate();
        qorgayApi = retrofit.create(QorgayApi.class);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

}