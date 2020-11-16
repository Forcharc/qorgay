package kz.kmg.qorgau.ui.create;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;
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

public class CreateFragment extends BaseFragment implements OnStepClickListener {

    CreateQorgayViewModel viewModel;

    static final int PAGES_COUNT = 17;

    @BindView(R.id.pager)
    ViewPager2 pager;

    @BindView(R.id.b_next)
    Button nextButton;

    PageNumberPickerFragment pageNumberPickerFragment = new PageNumberPickerFragment();

    private CreateQorgayPagesAdapter createQorgayPagesAdapter;

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

        viewModel = new ViewModelProvider(getActivity()).get(CreateQorgayViewModel.class);


        Bundle stepPickerArguments = new Bundle();
        stepPickerArguments.putInt(PageNumberPickerFragment.ARGUMENT_STEP_COUNT, PAGES_COUNT);
        pageNumberPickerFragment.setArguments(stepPickerArguments);
        pageNumberPickerFragment.setOnPageNumberClickListener(this);
        getChildFragmentManager().beginTransaction().replace(R.id.fl_step_picker, pageNumberPickerFragment).commit();

        createQorgayPagesAdapter = new CreateQorgayPagesAdapter(this);
        pager.setAdapter(createQorgayPagesAdapter);

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                checkIfLastStep(position);
                pageNumberPickerFragment.stepsAdapter.setCurrentStep(position);
            }
        });

    }

    @Override
    public void onPageChosen(int stepNumber) {
        checkIfLastStep(stepNumber);
        pager.setCurrentItem(stepNumber, true);
    }

    private void checkIfLastStep(int stepNumber) {
        if (stepNumber == (PAGES_COUNT - 1)) {
            nextButton.setText(R.string.done);
            nextButton.setOnClickListener(v -> {
                viewModel.createQorgay().observe(getViewLifecycleOwner(), createQorgayModelResource -> {
                    switch (createQorgayModelResource.status) {
                        case ERROR:
                            nextButton.setEnabled(true);
                            onToast(createQorgayModelResource.apiError.getMessage());
                            break;
                        case SUCCESS:
                            nextButton.setEnabled(true);
                            Boolean isSuccess = createQorgayModelResource.data.getIsSuccess();
                            if (isSuccess) {


                                viewModel.clearQorgay();
                                CreateQorgaySuccessDialog dialog = new CreateQorgaySuccessDialog();
                                dialog.listener = new CreateQorgaySuccessDialog.CreateQorgaySuccessDialogListener() {
                                    @Override
                                    public void onDialogClosed() {
                                        NavHostFragment.findNavController(CreateFragment.this).navigate(R.id.navigation_create);
                                    }
                                };
                                dialog.show(getChildFragmentManager(), null);
                            } else {
                                onToast(getString(R.string.fill_empty_fields));
                            }
                            break;
                        case LOADING:
                            nextButton.setEnabled(false);
                            break;
                    }
                });
            });
        } else {
            nextButton.setText(R.string.next);
            nextButton.setOnClickListener(v -> {
                if (pageNumberPickerFragment.nextStepButton != null) {
                    pageNumberPickerFragment.nextStepButton.performClick();
                }
            });
        }
    }

    class CreateQorgayPagesAdapter extends FragmentStateAdapter {

        public CreateQorgayPagesAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0: {
                    return new QorgayPage1Fragment();
                }
                case 1: {
                    return new QorgayPage2Fragment();
                }
                case 2: {
                    return new QorgayPage3Fragment();
                }
                case 3: {
                    return new QorgayPage4Fragment();
                }
                case 4: {
                    return new QorgayPage5Fragment();
                }
                case 5: {
                    return new QorgayPage6Fragment();
                }
                case 6: {
                    return new QorgayPage7Fragment();
                }
                case 7: {
                    return new QorgayPage8Fragment();
                }
                case 8: {
                    return new QorgayPage9Fragment();
                }
                case 9: {
                    return new QorgayPage10Fragment();
                }
                case 10: {
                    return new QorgayPage11Fragment();
                }
                case 11: {
                    return new QorgayPage12Fragment();
                }
                case 12: {
                    return new QorgayPage13Fragment();
                }
                case 13: {
                    return new QorgayPage14Fragment();
                }
                case 14: {
                    return new QorgayPage15Fragment();
                }
                case 15: {
                    return new QorgayPage16Fragment();
                }
                case 16: {
                    return new QorgayPage17Fragment();
                }
            }
            return new QorgayPage1Fragment();
        }

        @Override
        public int getItemCount() {
            return PAGES_COUNT;
        }
    }
}