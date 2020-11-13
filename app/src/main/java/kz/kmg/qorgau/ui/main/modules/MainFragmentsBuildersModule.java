package kz.kmg.qorgau.ui.main.modules;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import kz.kmg.qorgau.ui.create.CreateFragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage10Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage11Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage12Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage13Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage14Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage15Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage16Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage17Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage1Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage2Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage3Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage4Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage5Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage6Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage7Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage8Fragment;
import kz.kmg.qorgau.ui.create.pages.QorgayPage9Fragment;
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

    @ContributesAndroidInjector
    abstract QorgayPage2Fragment qorgayPage2Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage3Fragment qorgayPage3Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage4Fragment qorgayPage4Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage5Fragment qorgayPage5Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage6Fragment qorgayPage6Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage7Fragment qorgayPage7Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage8Fragment qorgayPage8Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage9Fragment qorgayPage9Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage10Fragment qorgayPage10Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage11Fragment qorgayPage11Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage12Fragment qorgayPage12Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage13Fragment qorgayPage13Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage14Fragment qorgayPage14Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage15Fragment qorgayPage15Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage16Fragment qorgayPage16Fragment();

    @ContributesAndroidInjector
    abstract QorgayPage17Fragment qorgayPage17Fragment();
}
