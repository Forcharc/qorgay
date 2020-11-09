package kz.kmg.qorgau.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import kz.kmg.qorgau.ui.main.MainActivity;
import kz.kmg.qorgau.ui.main.modules.MainFragmentsBuildersModule;
import kz.kmg.qorgau.ui.main.modules.MainModule;
import kz.kmg.qorgau.ui.main.modules.MainViewModelModule;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = {
            MainModule.class,
            MainViewModelModule.class,
            MainFragmentsBuildersModule.class
    })
    abstract MainActivity contributeMainActivity();

}
