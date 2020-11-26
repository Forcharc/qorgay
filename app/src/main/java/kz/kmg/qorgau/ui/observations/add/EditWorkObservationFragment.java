package kz.kmg.qorgau.ui.observations.add;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import kz.kmg.qorgau.data.model.work_observations.AnswerCategoriesItem;
import kz.kmg.qorgau.data.model.work_observations.ChildrenItem;
import kz.kmg.qorgau.data.model.work_observations.PlaceItemModel;
import kz.kmg.qorgau.data.model.work_observations.PlaceModel;
import kz.kmg.qorgau.data.model.work_observations.WorkObservationFormModel;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.data.network.api.WorkObservationsApi;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;
import kz.kmg.qorgau.ui.dialogs.QorgayCreatedDialog;
import kz.kmg.qorgau.ui.observations.WorkObservationViewModel;

public class EditWorkObservationFragment extends BaseFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String PARAM_WORK_ID = "param_work_id";

    private WorkObservationViewModel viewModel;

    @BindView(R.id.sv_work)
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

    @BindView(R.id.tv_date)
    TextView dateTextView;

    @BindView(R.id.tv_time)
    TextView timeTextView;

    @BindView(R.id.et_observables_count)
    EditText observablesCountEditText;

    @BindView(R.id.et_task)
    EditText taskEditText;

    @BindView(R.id.tv_obs_company)
    TextView obsCompanyTextView;

    @BindView(R.id.tv_obs_company_division)
    TextView obsCompanyDivisionTextView;

    @BindView(R.id.tv_observation_place)
    TextView obsPlaceTextView;

    @BindView(R.id.tv_observation_place_specific)
    TextView obsPlaceItemTextView;

    @BindView(R.id.rv)
    RecyclerView recycler;

    @BindView(R.id.b_save)
    CircularProgressButton saveButton;

    String cookie;

    WorkObservationFormModel work;

    Calendar nowCalendar = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    String dateString;
    String timeString;

    private final View.OnClickListener onRetryLoadForm = v -> {
        loadWork();
    };

    private final View.OnClickListener onRetryLoadOrganizations = v -> {
        viewModel.loadOrganizations();
    };

    @Override
    public int getContentView() {
        return R.layout.fragment_edit_work_observation;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WorkObservationViewModel.class);
        QorgauApp app = (QorgauApp) getActivity().getApplication();
        LocalStorage localStorage = app.prefStorage;
        cookie = localStorage.getCookie();
        QorgayApi qorgayApi = app.qorgayApi;
        WorkObservationsApi workApi = app.workObservationsApi;
        viewModel.init(qorgayApi, workApi);

        initObservers();
        initListeners();

        loadWork();
    }

    private void loadWork() {
        int workId = -1;
        if (getArguments() != null) {
            workId = getArguments().getInt(PARAM_WORK_ID, -1);
        }
        if (workId == -1) {
            viewModel.loadForm(cookie);
        } else {
            viewModel.loadWorkById(cookie, workId);
        }
    }

    private void initObservers() {
        viewModel.getWorkLiveData().observe(getViewLifecycleOwner(), workObservationFormModelResource -> {
            switch (workObservationFormModelResource.status) {
                case ERROR:
                    workScrollView.setVisibility(View.GONE);
                    showRetryButton(onRetryLoadForm);
                    onToast(workObservationFormModelResource.apiError.getMessage());
                    break;
                case SUCCESS:
                    work = workObservationFormModelResource.data;
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
                    initDateTime(work.getDateObservation());
                    showForm(work, resource.data);
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
                    if (work.getAuthorOrganizationDepartmentId() == null) {
                        showChooser(getString(R.string.department), resource.data, (dialog, item, position) -> {
                            work.setAuthorOrganizationDepartmentId(item.getId());
                            work.setAuthorOrganizationDepartmentName(item.getTitle());
                            divisionTextView.setText(item.getTitle());
                            dialog.dismiss();
                        });
                    } else {
                        DepartmentModel dep = findDepartment(resource.data, work.getAuthorOrganizationDepartmentId());
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
                    if (work.getOrganizationDepartmentId() == null) {
                        if (resource.data != null && resource.data.size() > 0) {
                            showChooser(getString(R.string.department), resource.data, (dialog, item, position) -> {
                                work.setOrganizationDepartmentId(item.getId());
                                obsCompanyDivisionTextView.setText(item.getTitle());
                                dialog.dismiss();
                            });
                        } else {
                            onToast(getString(R.string.no_departments));
                        }
                    } else {
                        DepartmentModel dep = findDepartment(resource.data, work.getOrganizationDepartmentId());
                        setTextIfNotNull(obsCompanyDivisionTextView, dep.getNameRu());
                    }
                    break;
                case LOADING:
                    showProgress();
                    break;
            }
        });

        viewModel.getPlacesByOrgIdLiveData().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case ERROR:
                    hideStatusViews();
                    onToast(resource.apiError.getMessage());
                    break;
                case SUCCESS:
                    hideStatusViews();
                    if (work.getPlaceId() == null) {
                        if (resource.data != null && resource.data.size() > 0) {
                            showChooser(getString(R.string.observation_place), resource.data, (dialog, item, position) -> {
                                work.setPlaceId(item.getId());
                                obsPlaceTextView.setText(item.getTitle());
                                dialog.dismiss();
                            });
                        } else {
                            onToast(getString(R.string.no_places));
                        }
                    } else {
                        PlaceModel place = findPlace(resource.data, work.getPlaceId());
                        setTextIfNotNull(obsPlaceTextView, place.getNameRu());
                    }
                    break;
                case LOADING:
                    showProgress();
                    break;
            }
        });

        viewModel.getPlaceItemsByPlaceIdLiveData().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case ERROR:
                    hideStatusViews();
                    onToast(resource.apiError.getMessage());
                    break;
                case SUCCESS:
                    hideStatusViews();
                    if (work.getPlaceItemId() == null) {
                        if (resource.data != null && resource.data.size() > 0) {
                            showChooser(getString(R.string.observation_place_specific), resource.data, (dialog, item, position) -> {
                                work.setPlaceItemId(item.getId());
                                obsPlaceItemTextView.setText(item.getTitle());
                                dialog.dismiss();
                            });
                        } else {
                            onToast(getString(R.string.no_place_items));
                        }
                    } else {
                        PlaceItemModel place = findPlaceItem(resource.data, work.getPlaceItemId());
                        if (place != null) {
                            setTextIfNotNull(obsPlaceItemTextView, place.getNameRu());
                        }
                    }
                    break;
                case LOADING:
                    showProgress();
                    break;
            }
        });

        viewModel.getSaveWorkResultLiveData().observe(getViewLifecycleOwner(), isSuccessResponseResource -> {
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

                        dialog.listener = () -> NavHostFragment.findNavController(EditWorkObservationFragment.this).popBackStack();
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


    private void initDateTime(String dateObservation) {
        if (dateObservation != null) {
            String[] dateTime = dateObservation.split(" ");
            dateString = dateTime[0];
            timeString = dateTime[1];
        }
    }

    void showForm(WorkObservationFormModel data, List<OrganizationModel> organizationModels) {
        setTextIfNotNull(nameTextView, data.getAuthorFullname());
        setTextIfNotNull(companyTextView, data.getAuthorOrganizationName());
        setTextIfNotNull(divisionTextView, data.getAuthorOrganizationDepartmentName());
        String date = data.getDateObservation();
        if (date != null) {
            String[] dateTime = date.split(" ");
            if (dateTime.length == 2) {
                setTextIfNotNull(dateTextView, dateTime[0]);
                setTextIfNotNull(timeTextView, dateTime[1]);
            }
        }
        if (data.getPeopleCount() != null)
            setTextIfNotNull(observablesCountEditText, String.valueOf(data.getPeopleCount()));

        setTextIfNotNull(taskEditText, data.getTask());

        if (data.getOrganizationId() != null) {
            OrganizationModel organization = findOrganization(organizationModels, data.getOrganizationId());
            if (organization != null) {
                setTextIfNotNull(obsCompanyTextView, organization.getName());
            }
        }

        if (data.getOrganizationDepartmentId() != null) {
            viewModel.loadDepartmentsByOrgId(data.getOrganizationId(), false);
        }

        if (data.getPlaceId() != null) {
            viewModel.loadPlacesByOrgId(cookie, data.getOrganizationId());

            if (data.getPlaceId() != null) {
                viewModel.loadPlaceItemsByPlaceId(cookie, data.getPlaceId());
            }
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
            work.setAuthorOrganizationDepartmentId(null);
            work.setAuthorOrganizationDepartmentName(null);
            Integer orgId = work.getAuthorOrganizationId();
            if (orgId != null) {
                viewModel.loadDepartmentsByOrgId(orgId, true);
            } else {
                onToast(getString(R.string.choose_organization));
            }
        });

        dateTextView.setOnClickListener(v -> {
            new DatePickerDialog(requireContext(), this,
                    nowCalendar.get(Calendar.YEAR),
                    nowCalendar.get(Calendar.MONTH),
                    nowCalendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        timeTextView.setOnClickListener(v -> {
            new TimePickerDialog(requireContext(),
                    this,
                    nowCalendar.get(Calendar.HOUR_OF_DAY),
                    nowCalendar.get(Calendar.MINUTE), true).show();
        });

        observablesCountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int peopleCount = 0;
                try {
                    peopleCount = Integer.parseInt(s.toString());
                } catch (Exception e) {
                }
                work.setPeopleCount(peopleCount);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        taskEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                work.setTask(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        obsCompanyTextView.setOnClickListener(v -> {
            onClearCompany();
            showChooser(getString(R.string.department), viewModel.getOrganizationsLiveData().getValue().data, (dialog, item, position) -> {
                work.setOrganizationId(item.getId());
                obsCompanyTextView.setText(item.getTitle());
                dialog.dismiss();
            });
        });

        obsCompanyDivisionTextView.setOnClickListener(v -> {
            onClearCompanyDivision();
            if (work.getOrganizationId() != null && work.getOrganizationId() != 0) {
                viewModel.loadDepartmentsByOrgId(work.getOrganizationId(), false);
            } else {
                onToast(getString(R.string.choose_organization));
            }
        });


        obsPlaceTextView.setOnClickListener(v -> {
            onClearPlace();
            if (work.getOrganizationId() != null) {
                viewModel.loadPlacesByOrgId(cookie, work.getOrganizationId());
            } else {
                onToast(getString(R.string.choose_organization));
            }
        });

        obsPlaceItemTextView.setOnClickListener(v -> {
            onClearPlaceItem();
            if (work.getPlaceId() != null) {
                viewModel.loadPlaceItemsByPlaceId(cookie, work.getPlaceId());
            } else {
                onToast(getString(R.string.choose_place));
            }
        });

        saveButton.setOnClickListener(v -> {
            work.setDate(dateString);
            work.setTime(timeString);
            writeCommentsAndCategories();
            viewModel.saveWork(cookie, work);
        });
    }

    private void parseCommentsAndCategories() {
        int j = 0;
        for (int i = 0; i < work.getAnswerCategories().size(); i++) {
            AnswerCategoriesItem categoriesItem = work.getAnswerCategories().get(i);
            for (ChildrenItem item :
                    categoriesItem.getChildren()) {
                j++;
                switch (j) {
                    case 1:
                        item.setCategory(work.getCategory1());
                        item.setComment(work.getComment1());
                        break;
                    case 2:
                        item.setCategory(work.getCategory2());
                        item.setComment(work.getComment2());
                        break;
                    case 3:

                        item.setCategory(work.getCategory3());
                        item.setComment(work.getComment3());
                        break;
                    case 4:

                        item.setCategory(work.getCategory4());
                        item.setComment(work.getComment4());
                        break;
                    case 5:
                        item.setCategory(work.getCategory5());
                        item.setComment(work.getComment5());
                        break;
                    case 6:
                        item.setCategory(work.getCategory6());
                        item.setComment(work.getComment6());
                        break;
                    case 7:
                        item.setCategory(work.getCategory7());
                        item.setComment(work.getComment7());
                        break;
                    case 8:
                        item.setCategory(work.getCategory8());
                        item.setComment(work.getComment8());
                        break;
                    case 9:
                        item.setCategory(work.getCategory9());
                        item.setComment(work.getComment9());
                        break;
                    case 10:
                        item.setCategory(work.getCategory10());
                        item.setComment(work.getComment10());
                        break;
                    case 11:
                        item.setCategory(work.getCategory11());
                        item.setComment(work.getComment11());
                        break;
                    case 12:
                        item.setCategory(work.getCategory12());
                        item.setComment(work.getComment12());
                        break;
                    case 13:
                        item.setCategory(work.getCategory13());
                        item.setComment(work.getComment13());
                        break;
                    case 14:
                        item.setCategory(work.getCategory14());
                        item.setComment(work.getComment14());
                        break;
                    case 15:
                        item.setCategory(work.getCategory15());
                        item.setComment(work.getComment15());
                        break;
                    case 16:
                        item.setCategory(work.getCategory16());
                        item.setComment(work.getComment16());
                        break;
                    case 17:
                        item.setCategory(work.getCategory17());
                        item.setComment(work.getComment17());
                        break;
                    case 18:
                        item.setCategory(work.getCategory18());
                        item.setComment(work.getComment18());
                        break;
                    case 19:
                        item.setCategory(work.getCategory19());
                        item.setComment(work.getComment19());
                        break;
                    case 20:
                        item.setCategory(work.getCategory20());
                        item.setComment(work.getComment20());
                        break;
                    case 21:
                        item.setCategory(work.getCategory21());
                        item.setComment(work.getComment21());
                        break;
                    case 22:
                        item.setCategory(work.getCategory22());
                        item.setComment(work.getComment22());
                        break;
                    case 23:
                        item.setCategory(work.getCategory23());
                        item.setComment(work.getComment23());
                        break;
                    case 24:
                        item.setCategory(work.getCategory24());
                        item.setComment(work.getComment24());
                        break;
                }
            }
        }
    }

    private void writeCommentsAndCategories() {
        int j = 0;
        for (int i = 0; i < work.getAnswerCategories().size(); i++) {
            AnswerCategoriesItem categoriesItem = work.getAnswerCategories().get(i);
            for (ChildrenItem item :
                    categoriesItem.getChildren()) {
                j++;
                switch (j) {
                    case 1:
                        work.setCategory1(item.getCategory());
                        work.setComment1(item.getComment());
                        break;
                    case 2:
                        work.setCategory2(item.getCategory());
                        work.setComment2(item.getComment());
                        break;
                    case 3:
                        work.setCategory3(item.getCategory());
                        work.setComment3(item.getComment());
                        break;
                    case 4:
                        work.setCategory4(item.getCategory());
                        work.setComment4(item.getComment());
                        break;
                    case 5:
                        work.setCategory5(item.getCategory());
                        work.setComment5(item.getComment());
                        break;
                    case 6:
                        work.setCategory6(item.getCategory());
                        work.setComment6(item.getComment());
                        break;
                    case 7:
                        work.setCategory7(item.getCategory());
                        work.setComment7(item.getComment());
                        break;
                    case 8:
                        work.setCategory8(item.getCategory());
                        work.setComment8(item.getComment());
                        break;
                    case 9:
                        work.setCategory9(item.getCategory());
                        work.setComment9(item.getComment());
                        break;
                    case 10:
                        work.setCategory10(item.getCategory());
                        work.setComment10(item.getComment());
                        break;
                    case 11:
                        work.setCategory11(item.getCategory());
                        work.setComment11(item.getComment());
                        break;
                    case 12:
                        work.setCategory12(item.getCategory());
                        work.setComment12(item.getComment());
                        break;
                    case 13:
                        work.setCategory13(item.getCategory());
                        work.setComment13(item.getComment());
                        break;
                    case 14:
                        work.setCategory14(item.getCategory());
                        work.setComment14(item.getComment());
                        break;
                    case 15:
                        work.setCategory15(item.getCategory());
                        work.setComment15(item.getComment());
                        break;
                    case 16:
                        work.setCategory16(item.getCategory());
                        work.setComment16(item.getComment());
                        break;
                    case 17:
                        work.setCategory17(item.getCategory());
                        work.setComment17(item.getComment());
                        break;
                    case 18:
                        work.setCategory18(item.getCategory());
                        work.setComment18(item.getComment());
                        break;
                    case 19:
                        work.setCategory19(item.getCategory());
                        work.setComment19(item.getComment());
                        break;
                    case 20:
                        work.setCategory20(item.getCategory());
                        work.setComment20(item.getComment());
                        break;
                    case 21:
                        work.setCategory21(item.getCategory());
                        work.setComment21(item.getComment());
                        break;
                    case 22:
                        work.setCategory22(item.getCategory());
                        work.setComment22(item.getComment());
                        break;
                    case 23:
                        work.setCategory23(item.getCategory());
                        work.setComment23(item.getComment());
                        break;
                    case 24:
                        work.setCategory24(item.getCategory());
                        work.setComment24(item.getComment());
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        dateString = dateFormat.format(date.getTime());
        dateTextView.setText(dateString);
        work.setDateObservation(dateString + " " + timeString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.HOUR_OF_DAY, hourOfDay);
        time.set(Calendar.MINUTE, minute);

        timeString = timeFormat.format(time.getTime());
        timeTextView.setText(timeString);
        work.setDateObservation(dateString + " " + timeString);
    }

    OrganizationModel findOrganization(List<OrganizationModel> orgs, int orgId) {
        return orgs.stream().filter(organizationModel -> organizationModel.getId() == orgId).findAny().orElse(null);
    }

    DepartmentModel findDepartment(List<DepartmentModel> deps, int depId) {
        return deps.stream().filter(dep -> dep.getId() == depId).findAny().orElse(null);
    }

    PlaceModel findPlace(List<PlaceModel> places, int placeId) {
        return places.stream().filter(dep -> dep.getId() == placeId).findAny().orElse(null);
    }

    PlaceItemModel findPlaceItem(List<PlaceItemModel> placeItems, int placeItemId) {
        return placeItems.stream().filter(dep -> dep.getId() == placeItemId).findAny().orElse(null);
    }

    private void onClearCompany() {
        obsCompanyTextView.setText("");
        work.setOrganizationId(null);
        onClearCompanyDivision();
    }

    private void onClearCompanyDivision() {
        obsCompanyDivisionTextView.setText("");
        work.setOrganizationDepartmentId(null);
    }

    private void onClearPlace() {
        obsPlaceTextView.setText("");
        work.setPlaceId(null);
        onClearPlaceItem();
    }

    private void onClearPlaceItem() {
        obsPlaceItemTextView.setText("");
        work.setPlaceItemId(null);
    }
}
