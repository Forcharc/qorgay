package kz.kmg.qorgau.ui.create;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;

public class CreateFragment extends BaseFragment implements OnStepClickListener {

    static final int PAGES_COUNT = 10;

    @BindView(R.id.pager)
    ViewPager2 pager;

    @BindView(R.id.b_next)
    Button nextButton;

    private PollAdapter pollAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_create;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StepPickerFragment stepPickerFragment = new StepPickerFragment();
        Bundle stepPickerArguments = new Bundle();
        stepPickerArguments.putInt(StepPickerFragment.ARGUMENT_STEP_COUNT, PAGES_COUNT);
        stepPickerFragment.setArguments(stepPickerArguments);
        stepPickerFragment.setOnStepClickListener(this);
        getChildFragmentManager().beginTransaction().replace(R.id.fl_step_picker, stepPickerFragment).commit();

        pollAdapter = new PollAdapter(this);
        pager.setAdapter(pollAdapter);

        nextButton.setOnClickListener(v -> {
            if (stepPickerFragment.nextStepButton != null) {
                stepPickerFragment.nextStepButton.performClick();
            }
        });
    }

    @Override
    public void onStepChosen(int stepNumber) {
        pager.setCurrentItem(stepNumber, true);
    }

    class PollAdapter extends FragmentStateAdapter {
        public PollAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return new PollPage1Fragment();
        }

        @Override
        public int getItemCount() {
            return PAGES_COUNT;
        }
    }
}