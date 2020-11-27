package kz.kmg.qorgau.ui.observations.driving;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import kz.kmg.qorgau.R;
import kz.kmg.qorgau.ui.observations.BaseObservationFragment;
import kz.kmg.qorgau.ui.observations.work.WorkObservationReportsFragment;

public class DrivingObservationFragment extends BaseObservationFragment {
    @Override
    public FragmentStateAdapter getPagerAdapter(Fragment fragment) {
        return new PagerAdapter(fragment);
    }

    @Override
    public void navigateToAddNewObservation() {
        navController.navigate(R.id.editDrivingObservationFragment);
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
                    return new DrivingObservationReportsFragment();
                default:
                    return new DrivingObservationListFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
