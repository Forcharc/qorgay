package kz.kmg.qorgau.ui.observations.driving;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
import butterknife.BindView;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import kz.kmg.qorgau.QorgauApp;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.local.LocalStorage;
import kz.kmg.qorgau.data.model.SearchableId;
import kz.kmg.qorgau.data.model.create.DepartmentModel;
import kz.kmg.qorgau.data.model.create.OrganizationModel;
import kz.kmg.qorgau.data.model.observations.AnswerCategoriesItem;
import kz.kmg.qorgau.data.model.observations.ChildrenItem;
import kz.kmg.qorgau.data.model.observations.driving.DrivingObservationFormModel;
import kz.kmg.qorgau.data.model.observations.work.PlaceItemModel;
import kz.kmg.qorgau.data.model.observations.work.PlaceModel;
import kz.kmg.qorgau.data.network.api.DrivingObservationsApi;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;
import kz.kmg.qorgau.ui.dialogs.QorgayCreatedDialog;
import kz.kmg.qorgau.ui.observations.ObservationCommentsAdapter;

public class EditDrivingObservationFragment extends BaseFragment  {

    public static final String PARAM_DRIVING_ID = "param_driving_id";

    private static final String TAG = "EditDrivingObservationF";

    private DrivingObservationViewModel viewModel;

    @BindView(R.id.sv_driving)
    ScrollView workScrollView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.b_retry)
    Button retryButton;

    @BindView(R.id.tv_name)
    TextView nameTextView;

    @BindView(R.id.tv_company)
    TextView companyTextView;

    @BindView(R.id.tv_division)
    TextView divisionTextView;

    @BindView(R.id.tv_obs_company)
    TextView obsCompanyTextView;

    @BindView(R.id.tv_obs_company_division)
    TextView obsCompanyDivisionTextView;

    @BindView(R.id.et_exp_years)
    EditText experienceEditText;

    @BindView(R.id.et_exp_years_driver)
    EditText experienceDriverEditText;

    @BindView(R.id.tv_date_start)
    TextView startDateTextView;

    @BindView(R.id.tv_date_end)
    TextView endDateTextView;

    @BindView(R.id.tv_time_start)
    TextView startTimeTextView;

    @BindView(R.id.tv_time_end)
    TextView endTimeTextView;

    @BindView(R.id.rv)
    RecyclerView recycler;

    @BindView(R.id.et_area)
    EditText areaEditText;

    @BindView(R.id.b_save)
    CircularProgressButton saveButton;

    String cookie;

    DrivingObservationFormModel driving;

    Calendar nowCalendar = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    String startDateString;
    String startTimeString;
    String endDateString;
    String endTimeString;

    private final View.OnClickListener onRetryLoadForm = v -> {
        loadDriving();
    };

    private final View.OnClickListener onRetryLoadOrganizations = v -> {
        viewModel.loadOrganizations();
    };

    @Override
    public int getContentView() {
        return R.layout.fragment_edit_driving_observation;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DrivingObservationViewModel.class);
        QorgauApp app = (QorgauApp) getActivity().getApplication();
        LocalStorage localStorage = app.prefStorage;
        cookie = localStorage.getCookie();
        QorgayApi qorgayApi = app.qorgayApi;
        DrivingObservationsApi drivingApi = app.drivingObservationsApi;
        viewModel.init(qorgayApi, drivingApi);

        initObservers();
        initListeners();

        loadDriving();
    }

    private void loadDriving() {
        int drivingId = -1;
        if (getArguments() != null) {
            drivingId = getArguments().getInt(PARAM_DRIVING_ID, -1);
        }
        if (drivingId == -1) {
            viewModel.loadForm(cookie);
        } else {
            viewModel.loadDrivingById(cookie, drivingId);
        }
    }

    private void initObservers() {
        viewModel.getDrivingLiveData().observe(getViewLifecycleOwner(), workObservationFormModelResource -> {
            switch (workObservationFormModelResource.status) {
                case ERROR:
                    workScrollView.setVisibility(View.GONE);
                    showRetryButton(onRetryLoadForm);
                    onToast(workObservationFormModelResource.apiError.getMessage());
                    break;
                case SUCCESS:
                    driving = workObservationFormModelResource.data;
                    parseCommentsAndCategories();
                    viewModel.loadOrganizations();
                    break;
                case LOADING:
                    workScrollView.setVisibility(View.GONE);
                    showProgress();
                    break;
            }
        });

        viewModel.getOrganizationsLiveData().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case ERROR:
                    workScrollView.setVisibility(View.GONE);
                    showRetryButton(onRetryLoadOrganizations);
                    onToast(resource.apiError.getMessage());
                    break;
                case SUCCESS:
                    workScrollView.setVisibility(View.VISIBLE);
                    hideStatusViews();
                    initDateTime(driving.getStartDate(), driving.getEndDate());
                    showForm(driving, resource.data);
                    break;
                case LOADING:
                    workScrollView.setVisibility(View.GONE);
                    showProgress();
                    break;
            }
        });

        viewModel.getDepartmentsByOrgIdAuthorLiveData().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case ERROR:
                    hideStatusViews();
                    onToast(resource.apiError.getMessage());
                    break;
                case SUCCESS:
                    hideStatusViews();
                    if (driving.getAuthorOrganizationDepartmentId() == null) {
                        showChooser(getString(R.string.department), resource.data, (dialog, item, position) -> {
                            driving.setAuthorOrganizationDepartmentId(item.getId());
                            driving.setAuthorOrganizationDepartmentName(item.getTitle());
                            divisionTextView.setText(item.getTitle());
                            dialog.dismiss();
                        });
                    } else {
                        DepartmentModel dep = findDepartment(resource.data, driving.getAuthorOrganizationDepartmentId());
                        setTextIfNotNull(divisionTextView, dep.getNameRu());
                    }
                    break;
                case LOADING:
                    showProgress();
                    break;
            }
        });

        viewModel.getDepartmentsByOrgIdLiveData().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case ERROR:
                    hideStatusViews();
                    onToast(resource.apiError.getMessage());
                    break;
                case SUCCESS:
                    hideStatusViews();
                    if (driving.getOrganizationDepartmentId() == null) {
                        if (resource.data != null && resource.data.size() > 0) {
                            showChooser(getString(R.string.department), resource.data, (dialog, item, position) -> {
                                driving.setOrganizationDepartmentId(item.getId());
                                obsCompanyDivisionTextView.setText(item.getTitle());
                                dialog.dismiss();
                            });
                        } else {
                            onToast(getString(R.string.no_departments));
                        }
                    } else {
                        DepartmentModel dep = findDepartment(resource.data, driving.getOrganizationDepartmentId());
                        setTextIfNotNull(obsCompanyDivisionTextView, dep.getNameRu());
                    }
                    break;
                case LOADING:
                    showProgress();
                    break;
            }
        });

        viewModel.getSaveDrivingResultLiveData().observe(getViewLifecycleOwner(), isSuccessResponseResource -> {
            switch (isSuccessResponseResource.status) {
                case ERROR:
                    saveButton.setEnabled(true);
                    saveButton.revertAnimation();
                    onToast(isSuccessResponseResource.apiError.getMessage());
                    break;
                case SUCCESS:
                    saveButton.setEnabled(true);
                    saveButton.revertAnimation();
                    Boolean isSuccess = isSuccessResponseResource.data.getIsSuccess();
                    if (isSuccess) {
                        QorgayCreatedDialog dialog = new QorgayCreatedDialog();

                        dialog.listener = () -> NavHostFragment.findNavController(EditDrivingObservationFragment.this).popBackStack();
                        dialog.show(getChildFragmentManager(), null);
                    } else {
                        onToast(getString(R.string.fill_empty_fields));
                    }
                    break;
                case LOADING:
                    saveButton.setEnabled(false);
                    saveButton.startAnimation();
                    break;
            }
        });
    }


    private void initDateTime(String startDate, String endDate) {
        if (startDate != null) {
            String[] dateTime = startDate.split(" ");
            startDateString = dateTime[0];
            startTimeString = dateTime[1];
        }

        if (endDate != null) {
            String[] dateTime = endDate.split(" ");
            endDateString = dateTime[0];
            endTimeString = dateTime[1];
        }
    }

    void showForm(DrivingObservationFormModel data, List<OrganizationModel> organizationModels) {
        setTextIfNotNull(nameTextView, data.getAuthorFullname());
        setTextIfNotNull(companyTextView, data.getAuthorOrganizationName());
        setTextIfNotNull(divisionTextView, data.getAuthorOrganizationDepartmentName());

        String startDate = data.getStartDate();
        if (startDate != null) {
            String[] dateTime = startDate.split(" ");
            if (dateTime.length == 2) {
                setTextIfNotNull(startDateTextView, dateTime[0]);
                setTextIfNotNull(startTimeTextView, dateTime[1]);
            }
        }

        String endDate = data.getEndDate();
        if (endDate != null) {
            String[] dateTime = endDate.split(" ");
            if (dateTime.length == 2) {
                setTextIfNotNull(endDateTextView, dateTime[0]);
                setTextIfNotNull(endTimeTextView, dateTime[1]);
            }
        }

        if (data.getAuthorDrivingExperience() != null)
            setTextIfNotNull(experienceEditText, String.valueOf(data.getAuthorDrivingExperience()));

        if (data.getDrivingExperience() != null)
            setTextIfNotNull(experienceDriverEditText, String.valueOf(data.getDrivingExperience()));

        setTextIfNotNull(areaEditText, data.getLocation());

        if (data.getOrganizationId() != null) {
            OrganizationModel organization = findOrganization(organizationModels, data.getOrganizationId());
            if (organization != null) {
                setTextIfNotNull(obsCompanyTextView, organization.getName());
            }
        }

        if (data.getOrganizationDepartmentId() != null) {
            viewModel.loadDepartmentsByOrgId(data.getOrganizationId(), false);
        }

        showComments(data.getAnswerCategories());
    }

    private void showComments(List<AnswerCategoriesItem> answerCategories) {
        ConcatAdapter concatAdapter = new ConcatAdapter();
        for (AnswerCategoriesItem item : answerCategories) {
            ObservationCommentsAdapter commentsAdapter = new ObservationCommentsAdapter(item);
            concatAdapter.addAdapter(commentsAdapter);
        }
        recycler.setAdapter(concatAdapter);
        recycler.setHasFixedSize(false);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.setNestedScrollingEnabled(false);
    }

    void initListeners() {
        divisionTextView.setOnClickListener(v -> {
            divisionTextView.setText("");
            driving.setAuthorOrganizationDepartmentId(null);
            driving.setAuthorOrganizationDepartmentName(null);
            Integer orgId = driving.getAuthorOrganizationId();
            if (orgId != null) {
                viewModel.loadDepartmentsByOrgId(orgId, true);
            } else {
                onToast(getString(R.string.choose_organization));
            }
        });

        startDateTextView.setOnClickListener(v -> {
            new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
                Calendar date = Calendar.getInstance();
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                startDateString = dateFormat.format(date.getTime());
                startDateTextView.setText(startDateString);
                driving.setStartDate(startDateString+ " " + startTimeString);
            },
                    nowCalendar.get(Calendar.YEAR),
                    nowCalendar.get(Calendar.MONTH),
                    nowCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        startTimeTextView.setOnClickListener(v -> {
            new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {

                Calendar time = Calendar.getInstance();
                time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                time.set(Calendar.MINUTE, minute);

                startTimeString = timeFormat.format(time.getTime());
                startTimeTextView.setText(startTimeString);
                driving.setStartDate(startDateString + " " + startTimeString);
            },
                    nowCalendar.get(Calendar.HOUR_OF_DAY),
                    nowCalendar.get(Calendar.MINUTE), true).show();
        });

        endDateTextView.setOnClickListener(v -> {
            new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
                Calendar date = Calendar.getInstance();
                date.set(Calendar.YEAR, year);
                date.set(Calendar.MONTH, month);
                date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                endDateString = dateFormat.format(date.getTime());
                endDateTextView.setText(endDateString);
                driving.setStartDate(endDateString+ " " + endTimeString);
            },
                    nowCalendar.get(Calendar.YEAR),
                    nowCalendar.get(Calendar.MONTH),
                    nowCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        endTimeTextView.setOnClickListener(v -> {
            new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {

                Calendar time = Calendar.getInstance();
                time.set(Calendar.HOUR_OF_DAY, hourOfDay);
                time.set(Calendar.MINUTE, minute);

                endTimeString = timeFormat.format(time.getTime());
                endTimeTextView.setText(endTimeString);
                driving.setStartDate(endDateString + " " + endTimeString);
            },
                    nowCalendar.get(Calendar.HOUR_OF_DAY),
                    nowCalendar.get(Calendar.MINUTE), true).show();
        });

        experienceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int years = 0;
                try {
                    years = Integer.parseInt(s.toString());
                } catch (Exception e) {
                }
                driving.setAuthorDrivingExperience(years);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        experienceDriverEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int years = 0;
                try {
                    years = Integer.parseInt(s.toString());
                } catch (Exception e) {
                }
                driving.setDrivingExperience(years);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        areaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                driving.setLocation(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        obsCompanyTextView.setOnClickListener(v -> {
            onClearCompany();
            showChooser(getString(R.string.department), viewModel.getOrganizationsLiveData().getValue().data, (dialog, item, position) -> {
                driving.setOrganizationId(item.getId());
                obsCompanyTextView.setText(item.getTitle());
                dialog.dismiss();
            });
        });

        obsCompanyDivisionTextView.setOnClickListener(v -> {
            onClearCompanyDivision();
            if (driving.getOrganizationId() != null && driving.getOrganizationId() != 0) {
                viewModel.loadDepartmentsByOrgId(driving.getOrganizationId(), false);
            } else {
                onToast(getString(R.string.choose_organization));
            }
        });




        saveButton.setOnClickListener(v -> {
            driving.setStartDate(startDateString);
            driving.setStartTime(startTimeString);
            driving.setEndDate(endDateString);
            driving.setEndTime(endTimeString);
            writeCommentsAndCategories();
            viewModel.saveDriving(cookie, driving);
        });
    }

    private void parseCommentsAndCategories() {
        int j = 0;
        for (int i = 0; i < driving.getAnswerCategories().size(); i++) {
            AnswerCategoriesItem categoriesItem = driving.getAnswerCategories().get(i);
            for (ChildrenItem item :
                    categoriesItem.getChildren()) {
                j++;
                switch (j) {
                    case 1:
                        item.setCategory(driving.getCategory1());
                        item.setComment(driving.getComment1());
                        break;
                    case 2:
                        item.setCategory(driving.getCategory2());
                        item.setComment(driving.getComment2());
                        break;
                    case 3:

                        item.setCategory(driving.getCategory3());
                        item.setComment(driving.getComment3());
                        break;
                    case 4:

                        item.setCategory(driving.getCategory4());
                        item.setComment(driving.getComment4());
                        break;
                    case 5:
                        item.setCategory(driving.getCategory5());
                        item.setComment(driving.getComment5());
                        break;
                    case 6:
                        item.setCategory(driving.getCategory6());
                        item.setComment(driving.getComment6());
                        break;
                    case 7:
                        item.setCategory(driving.getCategory7());
                        item.setComment(driving.getComment7());
                        break;
                    case 8:
                        item.setCategory(driving.getCategory8());
                        item.setComment(driving.getComment8());
                        break;
                    case 9:
                        item.setCategory(driving.getCategory9());
                        item.setComment(driving.getComment9());
                        break;
                    case 10:
                        item.setCategory(driving.getCategory10());
                        item.setComment(driving.getComment10());
                        break;
                    case 11:
                        item.setCategory(driving.getCategory11());
                        item.setComment(driving.getComment11());
                        break;
                    case 12:
                        item.setCategory(driving.getCategory12());
                        item.setComment(driving.getComment12());
                        break;
                    case 13:
                        item.setCategory(driving.getCategory13());
                        item.setComment(driving.getComment13());
                        break;
                    case 14:
                        item.setCategory(driving.getCategory14());
                        item.setComment(driving.getComment14());
                        break;
                    case 15:
                        item.setCategory(driving.getCategory15());
                        item.setComment(driving.getComment15());
                        break;
                    case 16:
                        item.setCategory(driving.getCategory16());
                        item.setComment(driving.getComment16());
                        break;
                    case 17:
                        item.setCategory(driving.getCategory17());
                        item.setComment(driving.getComment17());
                        break;
                    case 18:
                        item.setCategory(driving.getCategory18());
                        item.setComment(driving.getComment18());
                        break;
                    case 19:
                        item.setCategory(driving.getCategory19());
                        item.setComment(driving.getComment19());
                        break;

                }
            }
        }
    }

    private void writeCommentsAndCategories() {
        int j = 0;
        for (int i = 0; i < driving.getAnswerCategories().size(); i++) {
            AnswerCategoriesItem categoriesItem = driving.getAnswerCategories().get(i);
            for (ChildrenItem item :
                    categoriesItem.getChildren()) {
                j++;
                switch (j) {
                    case 1:
                        driving.setCategory1(item.getCategory());
                        driving.setComment1(item.getComment());
                        break;
                    case 2:
                        driving.setCategory2(item.getCategory());
                        driving.setComment2(item.getComment());
                        break;
                    case 3:
                        driving.setCategory3(item.getCategory());
                        driving.setComment3(item.getComment());
                        break;
                    case 4:
                        driving.setCategory4(item.getCategory());
                        driving.setComment4(item.getComment());
                        break;
                    case 5:
                        driving.setCategory5(item.getCategory());
                        driving.setComment5(item.getComment());
                        break;
                    case 6:
                        driving.setCategory6(item.getCategory());
                        driving.setComment6(item.getComment());
                        break;
                    case 7:
                        driving.setCategory7(item.getCategory());
                        driving.setComment7(item.getComment());
                        break;
                    case 8:
                        driving.setCategory8(item.getCategory());
                        driving.setComment8(item.getComment());
                        break;
                    case 9:
                        driving.setCategory9(item.getCategory());
                        driving.setComment9(item.getComment());
                        break;
                    case 10:
                        driving.setCategory10(item.getCategory());
                        driving.setComment10(item.getComment());
                        break;
                    case 11:
                        driving.setCategory11(item.getCategory());
                        driving.setComment11(item.getComment());
                        break;
                    case 12:
                        driving.setCategory12(item.getCategory());
                        driving.setComment12(item.getComment());
                        break;
                    case 13:
                        driving.setCategory13(item.getCategory());
                        driving.setComment13(item.getComment());
                        break;
                    case 14:
                        driving.setCategory14(item.getCategory());
                        driving.setComment14(item.getComment());
                        break;
                    case 15:
                        driving.setCategory15(item.getCategory());
                        driving.setComment15(item.getComment());
                        break;
                    case 16:
                        driving.setCategory16(item.getCategory());
                        driving.setComment16(item.getComment());
                        break;
                    case 17:
                        driving.setCategory17(item.getCategory());
                        driving.setComment17(item.getComment());
                        break;
                    case 18:
                        driving.setCategory18(item.getCategory());
                        driving.setComment18(item.getComment());
                        break;
                    case 19:
                        driving.setCategory19(item.getCategory());
                        driving.setComment19(item.getComment());
                        break;
                }
            }
        }
    }

    private void setTextIfNotNull(TextView textView, String string) {
        if (string != null && textView != null) {
            textView.setText(string);
        }
    }


    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
    }

    public void showRetryButton(View.OnClickListener onRetryButtonClick) {
        retryButton.setOnClickListener(onRetryButtonClick);
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
    }

    public void hideStatusViews() {
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    private <T extends SearchableId> void showChooser(String title, List<T> data, SearchResultListener<SearchableId> listener) {
        ArrayList<SearchableId> arrayList = new ArrayList<>(data);
        new SimpleSearchDialogCompat<>(requireContext(), title, "", null, arrayList, listener).show();
    }



    OrganizationModel findOrganization(List<OrganizationModel> orgs, int orgId) {
        return orgs.stream().filter(organizationModel -> organizationModel.getId() == orgId).findAny().orElse(null);
    }

    DepartmentModel findDepartment(List<DepartmentModel> deps, int depId) {
        return deps.stream().filter(dep -> dep.getId() == depId).findAny().orElse(null);
    }

    private void onClearCompany() {
        obsCompanyTextView.setText("");
        driving.setOrganizationId(null);
        onClearCompanyDivision();
    }

    private void onClearCompanyDivision() {
        obsCompanyDivisionTextView.setText("");
        driving.setOrganizationDepartmentId(null);
    }


}
