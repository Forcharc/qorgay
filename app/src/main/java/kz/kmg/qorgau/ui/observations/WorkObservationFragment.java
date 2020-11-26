package kz.kmg.qorgau.ui.observations;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import butterknife.BindView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;
import kz.kmg.qorgau.ui.main.MainActivity;
import kz.kmg.qorgau.ui.observations.list.ObservationListFragment;
import kz.kmg.qorgau.ui.observations.list.ObservationReportsFragment;

public class WorkObservationFragment extends BaseFragment {

    @BindView(R.id.tab_layout)
    public TabLayout tabLayout;

    @BindView(R.id.pager)
    ViewPager2 viewPager;

    private NavController navController;

    @Override
    public int getContentView() {
        return R.layout.fragment_work_observation;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = NavHostFragment.findNavController(this);

        ImageView addMenuButton = new ImageView(requireContext());
        addMenuButton.setOnClickListener(v -> {
            navController.navigate(R.id.addWorkObservationFragment);
        });
        addMenuButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_add, null));
        ((MainActivity) requireActivity()).addIcon(addMenuButton);

        viewPager.setAdapter(new PagerAdapter(this));
        viewPager.setUserInputEnabled(false);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText(R.string.list);
                            break;
                        default:
                            tab.setText(R.string.my_reports);
                            break;
                    }
                }
        ).attach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        tabLayout = null;
        viewPager = null;
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
                    return new ObservationReportsFragment();
                default:
                    return new ObservationListFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
