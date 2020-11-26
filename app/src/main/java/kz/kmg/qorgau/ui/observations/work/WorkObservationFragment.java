package kz.kmg.qorgau.ui.observations.work;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import kz.kmg.qorgau.ui.observations.BaseObservationFragment;

public class WorkObservationFragment extends BaseObservationFragment {
    @Override
    public FragmentStateAdapter getPagerAdapter(Fragment fragment) {
        return new PagerAdapter(fragment);
    }

    class PagerAdapter extends FragmentStateAdapter {
        public PagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 1:
                    return new WorkObservationReportsFragment();
                default:
                    return new WorkObservationListFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
