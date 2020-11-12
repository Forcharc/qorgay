package kz.kmg.qorgau.ui.main.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import kz.kmg.qorgau.ui.create.CreateFragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage1Fragment;
import kz.kmg.qorgau.ui.home.HomeFragment;
import kz.kmg.qorgau.ui.list.ListFragment;
import kz.kmg.qorgau.ui.notifications.NotificationsFragment;
import kz.kmg.qorgau.ui.profile.ProfileFragment;


@Module
public abstract class MainFragmentsBuildersModule {

    @ContributesAndroidInjector
    abstract HomeFragment homeFragment();

    @ContributesAndroidInjector
    abstract ListFragment listFragment();

    @ContributesAndroidInjector
    abstract CreateFragment createFragment();

    @ContributesAndroidInjector
    abstract NotificationsFragment notificationsFragment();

    @ContributesAndroidInjector
    abstract ProfileFragment profileFragment();

    @ContributesAndroidInjector
    abstract QorgayPage1Fragment qorgayPage1Fragment();
}
