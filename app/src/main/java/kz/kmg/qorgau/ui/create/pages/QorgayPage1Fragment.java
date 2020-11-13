package kz.kmg.qorgau.ui.create.pages;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import butterknife.BindView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.create.ObservationTypeModel;

public class QorgayPage1Fragment extends BaseQorgayPageFragment {
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    View.OnClickListener onOptionItemClickListener;
    View.OnClickListener onRetryButtonClickListener;

    public QorgayPage1Fragment() {
        super(1, R.string.observation_type);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onOptionItemClickListener = v -> {
            viewModel.setPage1ObservationTypeId(v.getId());
        };

        onRetryButtonClickListener = v -> {
            initObservationTypes();
        };

        initObservationTypes();
    }

    private void initObservationTypes() {
        viewModel.getObservationTypes().removeObservers(getViewLifecycleOwner());
        viewModel.getObservationTypes().observe(getViewLifecycleOwner(), listResource -> {
            radioGroup.removeAllViews();
            switch (listResource.status) {
                case ERROR:
                    showRetryButton(onRetryButtonClickListener);
                    onToast(listResource.apiError.getMessage());
                    break;
                case SUCCESS:
                    hideStatusViews();
                    loadOptions(listResource.data, viewModel.getPage1ObservationTypeId());
                    break;
                case LOADING:
                    showProgress();
                    break;
            }
        });
    }

    private void loadOptions(List<ObservationTypeModel> options, Integer checkedId) {
        radioGroup.removeAllViews();
        for (ObservationTypeModel item :
                options) {
            addOption(item, checkedId);
        }
    }

    private void addOption(ObservationTypeModel option, Integer checkedId) {
        RadioButton radioButton = new RadioButton(requireContext());
        radioButton.setText(option.getName());
        radioButton.setId(option.getId());
        radioButton.setOnClickListener(onOptionItemClickListener);

        if (checkedId != null && option.getId() == checkedId) {
            radioButton.setChecked(true);
        }

        radioGroup.addView(radioButton);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page1;
    }
}
