package kz.kmg.qorgau.ui.create.pages;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class QorgayPage5Fragment extends BaseQorgayPageFragment {

    @BindView(R.id.tv_organization)
    TextView organizationTextView;

    @BindView(R.id.et_contractor)
    EditText contractorEditText;

    View.OnClickListener onRetryButtonClickListener;

    public QorgayPage5Fragment() {
        super(5, R.string.organization_contractor);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onRetryButtonClickListener = v -> {
            initOrganizations();
        };


        initOrganizations();

        contractorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setPage5Contractor(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }


    private void initOrganizations() {
        viewModel.getOrganizations().removeObservers(getViewLifecycleOwner());
        viewModel.getOrganizations().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {
                case ERROR:
                    showRetryButton(onRetryButtonClickListener);
                    onToast(listResource.apiError.getMessage());
                    organizationTextView.setVisibility(View.GONE);
                    contractorEditText.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    hideStatusViews();
                    organizationTextView.setVisibility(View.VISIBLE);

                    restoreState(listResource.data);

                    organizationTextView.setOnClickListener(v -> {
                        showOrganizationsChooser(listResource.data);
                    });
                    break;
                case LOADING:
                    showProgress();
                    organizationTextView.setVisibility(View.GONE);
                    contractorEditText.setVisibility(View.GONE);
                    break;
            }
        });
    }

    void restoreState(List<OrganizationModel> data) {
        Integer organizationId = viewModel.getPage5OrganizationId();
        if (organizationId != null) {
            for (OrganizationModel item :
                    data) {
                if (item.getId() == organizationId) {
                    organizationTextView.setText(item.getName());
                }
            }
            contractorEditText.setText(viewModel.getPage5Contractor());
            contractorEditText.setVisibility(View.VISIBLE);
        } else {
            contractorEditText.setVisibility(View.GONE);
        }
    }

    void showOrganizationsChooser(List<OrganizationModel> data) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());

        String[] organizationNames = new String[data.size()];

        for (int i = 0; i < data.size(); i++) {
            organizationNames[i] = data.get(i).getName();
        }

        dialogBuilder.setItems(organizationNames, (dialog, which) -> {
            viewModel.setPage5OrganizationId(data.get(which).getId());
            organizationTextView.setText(data.get(which).getName());
            contractorEditText.setVisibility(View.VISIBLE);
        });


        dialogBuilder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            dialog.dismiss();
        });

        dialogBuilder.create().show();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page5;
    }
}
