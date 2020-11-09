package kz.kmg.qorgau.ui.main.modules;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import kz.kmg.qorgau.di.util.ViewModelKey;
import kz.kmg.qorgau.ui.main.MainViewModel;


@Module
public abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindMainViewModel(MainViewModel mainViewModel);

}
