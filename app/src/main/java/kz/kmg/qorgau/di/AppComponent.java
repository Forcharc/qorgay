package kz.kmg.qorgau.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import kz.kmg.qorgau.QorgauApp;
import kz.kmg.qorgau.data.local.LocalStorage;
import kz.kmg.qorgau.data.network.RetrofitModule;
import kz.kmg.qorgau.di.util.ViewModelFactoryModule;
import kz.kmg.qorgau.domain.services.glide.GlideModule;


@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class,
                GlideModule.class,
                RetrofitModule.class
        }
)
public interface AppComponent extends AndroidInjector<QorgauApp> {

    LocalStorage localStorage();


    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
