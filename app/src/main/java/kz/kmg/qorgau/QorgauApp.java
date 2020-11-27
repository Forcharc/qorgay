package kz.kmg.qorgau;

import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import kz.kmg.qorgau.data.local.LocalStorage;
import kz.kmg.qorgau.data.local.SharedPrefStorage;
import kz.kmg.qorgau.data.network.api.DrivingObservationsApi;
import kz.kmg.qorgau.data.network.api.NewsApi;
import kz.kmg.qorgau.data.network.api.NotificationsApi;
import kz.kmg.qorgau.data.network.api.ProfileApi;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.data.network.api.WorkObservationsApi;
import kz.kmg.qorgau.di.DaggerAppComponent;
import retrofit2.Retrofit;

public class QorgauApp extends DaggerApplication {

    public LocalStorage prefStorage;

    @Inject
    Retrofit retrofit;

    @Inject
    public RequestManager requestManager;

    public QorgayApi qorgayApi;
    public NewsApi newsApi;
    public ProfileApi profileApi;
    public NotificationsApi notificationsApi;
    public WorkObservationsApi workObservationsApi;
    public DrivingObservationsApi drivingObservationsApi;

    @Override
    public void onCreate() {
        super.onCreate();
        qorgayApi = retrofit.create(QorgayApi.class);
        profileApi = retrofit.create(ProfileApi.class);
        notificationsApi = retrofit.create(NotificationsApi.class);
        workObservationsApi = retrofit.create(WorkObservationsApi.class);
        drivingObservationsApi = retrofit.create(DrivingObservationsApi.class);
        newsApi = retrofit.create(NewsApi.class);
        prefStorage = new SharedPrefStorage(this);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

}