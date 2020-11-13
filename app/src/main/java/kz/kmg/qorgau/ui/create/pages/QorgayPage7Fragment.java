package kz.kmg.qorgau.ui.create.pages;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import butterknife.BindView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.create.OrganizationModel;

public class QorgayPage7Fragment extends BaseQorgayPageFragment {

    @BindView(R.id.tv_supervised_organization)
    TextView supervisedOrganizationTextView;

    @BindView(R.id.et_object)
    EditText objectEditText;

    View.OnClickListener onRetryButtonClickListener;

    public QorgayPage7Fragment() {
        super(7, R.string.supervised_organization);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        onRetryButtonClickListener = v -> {
            initSupervisedOrganizations();
        };


        initSupervisedOrganizations();

        objectEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setPage7Object(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

    }

    private void initSupervisedOrganizations() {
        viewModel.getOrganizations().removeObservers(getViewLifecycleOwner());
        viewModel.getOrganizations().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {
                case ERROR:
                    showRetryButton(onRetryButtonClickListener);
                    onToast(listResource.apiError.getMessage());
                    supervisedOrganizationTextView.setVisibility(View.GONE);
                    objectEditText.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    hideStatusViews();
                    supervisedOrganizationTextView.setVisibility(View.VISIBLE);

                    restoreState(listResource.data);

                    supervisedOrganizationTextView.setOnClickListener(v -> {
                        showOrganizationsChooser(listResource.data);
                    });
                    break;
                case LOADING:
                    showProgress();
                    supervisedOrganizationTextView.setVisibility(View.GONE);
                    objectEditText.setVisibility(View.GONE);
                    break;
            }
        });
    }

    void restoreState(List<OrganizationModel> data) {
        Integer page7SupervisedOrganizationId = viewModel.getPage7SupervisedOrganizationId();
        if (page7SupervisedOrganizationId != null) {
            for (OrganizationModel item :
                    data) {
                if (item.getId() == page7SupervisedOrganizationId) {
                    supervisedOrganizationTextView.setText(item.getName());
                }
            }
            objectEditText.setText(viewModel.getPage7Object());
            objectEditText.setVisibility(View.VISIBLE);
        } else {
            objectEditText.setVisibility(View.GONE);
        }
    }

    void showOrganizationsChooser(List<OrganizationModel> data) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());

        String[] organizationNames = new String[data.size()];

        for (int i = 0; i < data.size(); i++) {
            organizationNames[i] = data.get(i).getName();
        }

        dialogBuilder.setItems(organizationNames, (dialog, which) -> {
            viewModel.setPage7SupervisedOrganizationId(data.get(which).getId());
            supervisedOrganizationTextView.setText(data.get(which).getName());
            objectEditText.setVisibility(View.VISIBLE);
        });


        dialogBuilder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            dialog.dismiss();
        });

        dialogBuilder.create().show();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page7;
    }
}
