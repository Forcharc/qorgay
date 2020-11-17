package kz.kmg.qorgau.ui.create.pages;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.SearchableIdModel;
import kz.kmg.qorgau.data.model.create.DepartmentModel;
import kz.kmg.qorgau.data.model.create.OrganizationModel;

public class QorgayPage6Fragment extends BaseQorgayPageFragment {
    private static final String TAG = "QorgayPage6Fragment";


    @BindView(R.id.tv_choose_organization)
    TextView chooseOrganizationTextView;

    @BindView(R.id.tv_department)
    TextView departmentTextView;

    @BindView(R.id.tv_no_departments)
    TextView noDepartmentsTextView;

    View.OnClickListener onRetryButtonClickListener;

    public QorgayPage6Fragment() {
        super(6, R.string.department);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Integer organizationId = viewModel.getPage5OrganizationId();

        onRetryButtonClickListener = v -> {
            viewModel.loadDepartments(viewModel.getPage5OrganizationId());
        };

        if (organizationId == null) {
            chooseOrganizationTextView.setVisibility(View.VISIBLE);
            departmentTextView.setVisibility(View.GONE);
        } else {
            departmentTextView.setVisibility(View.VISIBLE);
            chooseOrganizationTextView.setVisibility(View.GONE);
        }

        initDepartments();

    }

    private void initDepartments() {
        viewModel.getDepartments().removeObservers(getViewLifecycleOwner());
        viewModel.getDepartments().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {
                case ERROR:
                    showRetryButton(onRetryButtonClickListener);
                    onToast(listResource.apiError.getMessage());
                    departmentTextView.setVisibility(View.GONE);
                    chooseOrganizationTextView.setVisibility(View.GONE);
                    noDepartmentsTextView.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    hideStatusViews();
                    chooseOrganizationTextView.setVisibility(View.GONE);

                    restoreState(listResource.data);

                    if (listResource.data.size() == 0) {
                        noDepartmentsTextView.setVisibility(View.VISIBLE);
                        departmentTextView.setVisibility(View.GONE);
                    } else {
                        noDepartmentsTextView.setVisibility(View.GONE);
                        departmentTextView.setVisibility(View.VISIBLE);
                        departmentTextView.setOnClickListener(v -> {
                            showDepartmentChooser(listResource.data);
                        });
                    }

                    break;
                case LOADING:
                    showProgress();
                    departmentTextView.setVisibility(View.GONE);
                    noDepartmentsTextView.setVisibility(View.GONE);
                    chooseOrganizationTextView.setVisibility(View.GONE);
                    break;
            }
        });
    }

    void showDepartmentChooser(List<DepartmentModel> data) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireContext());

        if (data.size() == 0) {
            onToast(getString(R.string.no_departments));
        } else {
            ArrayList<SearchableIdModel> searchableDepartments = new ArrayList<SearchableIdModel>(data.size());
            DepartmentModel currentDepartment;
            for (int i = 0; i < data.size(); i++) {
                currentDepartment = data.get(i);
                searchableDepartments.add(new SearchableIdModel(currentDepartment.getId(), currentDepartment.getNameRu()));
            }

            new SimpleSearchDialogCompat<SearchableIdModel>(requireContext(), getString(R.string.department), "", null, searchableDepartments, (dialog, item, position) -> {
                viewModel.setPage6OrganizationDepartmentId(item.getId());
                departmentTextView.setText(item.getTitle());
                dialog.dismiss();
            }).show();
        }
    }

    void restoreState(List<DepartmentModel> data) {
        Integer departmentId = viewModel.getPage6OrganizationDepartmentId();
        if (departmentId != null) {
            for (DepartmentModel item :
                    data) {
                if (item.getId() == departmentId) {
                    departmentTextView.setText(item.getNameRu());
                }
            }
        } else {
            departmentTextView.setText("");
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_qorgay_page6;
    }
}
