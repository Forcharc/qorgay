package kz.kmg.qorgau.ui.create.pages;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Set;

import butterknife.BindView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.create.ObservationCategoryModel;

public class QorgayPage8Fragment extends BaseQorgayPageFragment {

    @BindView(R.id.ll_observation_categories)
    LinearLayout categoriesLayout;

    CompoundButton.OnCheckedChangeListener onOptionItemCheckedListener;
    View.OnClickListener onRetryButtonClickListener;

    public QorgayPage8Fragment() {
        super(8, R.string.observation_category);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        onOptionItemCheckedListener = (buttonView, isChecked) -> {
            if (isChecked) {
                viewModel.getPage8ObservationCategories().add(buttonView.getId());
            } else {
                viewModel.getPage8ObservationCategories().remove(buttonView.getId());
            }
        };

        onRetryButtonClickListener = v -> {
            initObservationTypes();
        };

        initObservationTypes();
    }


    private void initObservationTypes() {
        viewModel.getObservationCategories().observe(getViewLifecycleOwner(), listResource -> {
            categoriesLayout.removeAllViews();
            switch (listResource.status) {
                case ERROR:
                    showRetryButton(onRetryButtonClickListener);
                    onToast(listResource.apiError.getMessage());
                    break;
                case SUCCESS:
                    hideStatusViews();
                    loadOptions(listResource.data, viewModel.getPage8ObservationCategories());
                    break;
                case LOADING:
                    showProgress();
                    break;
            }
        });
    }

    private void loadOptions(List<ObservationCategoryModel> options, Set<Integer> checkedIds) {
        categoriesLayout.removeAllViews();
        for (ObservationCategoryModel item :
                options) {
            addOption(item, checkedIds);
        }
    }

    private void addOption(ObservationCategoryModel option, Set<Integer> checkedIds) {
        CheckBox checkBox = new CheckBox(requireContext());
        checkBox.setText(option.getName());
        checkBox.setId(option.getId());
        checkBox.setOnCheckedChangeListener(onOptionItemCheckedListener);

        if (checkedIds != null && checkedIds.contains(option.getId())) {
            checkBox.setChecked(true);
        }

        categoriesLayout.addView(checkBox);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page8;
    }
}
