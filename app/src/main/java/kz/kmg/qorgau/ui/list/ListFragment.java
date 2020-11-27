package kz.kmg.qorgau.ui.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import kz.kmg.qorgau.QorgauApp;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.local.LocalStorage;
import kz.kmg.qorgau.data.model.create.ObservationTypeModel;
import kz.kmg.qorgau.data.model.list.QorgayModel;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.ui.base.adapter.BaseAdapter;
import kz.kmg.qorgau.ui.base.adapter.BaseViewHolder;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;
import kz.kmg.qorgau.ui.create.QorgayViewModel;
import kz.kmg.qorgau.ui.dialogs.QorgayDetailsDialog;


public class ListFragment extends BaseFragment {

    private static final String TAG = "ListFragment";

    @BindView(R.id.rv_qorgay_list)
    RecyclerView qorgayListRecyclerview;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.b_retry)
    Button retryButton;

    @BindView(R.id.srl_list)
    SwipeRefreshLayout listSwipeRefreshLayout;


    @BindView(R.id.ll_no_items)
    LinearLayout noItemsLinearLayout;

    @BindView(R.id.b_add_item)
    Button addItemButton;

    QorgayViewModel viewModel;

    LocalStorage prefStorage;

    List<ObservationTypeModel> observationTypeList;

    View.OnClickListener onRetryQorgayListLoading = v -> {
        initList(observationTypeList);
    };

    View.OnClickListener onRetryTypesLoading = v -> {
        init();
    };


    SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM", Locale.getDefault());

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(QorgayViewModel.class);
        viewModel.setQorgayApi(((QorgauApp) getActivity().getApplication()).qorgayApi);

        prefStorage = ((QorgauApp) getActivity().getApplication()).prefStorage;

        addItemButton.setOnClickListener(v ->
                NavHostFragment.findNavController(this).navigate(R.id.navigation_create)
        );

        init();

        listSwipeRefreshLayout.setOnRefreshListener(() -> {
            listSwipeRefreshLayout.setRefreshing(false);
            viewModel.resetList();
            init();
        });

    }

    void init() {
        noItemsLinearLayout.setVisibility(View.GONE);

        LiveData<Resource<List<ObservationTypeModel>>> observationTypes = viewModel.getObservationTypes();
        observationTypes.removeObservers(getViewLifecycleOwner());
        observationTypes.observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {
                case ERROR:
                    showRetryButton(onRetryTypesLoading);
                    onToast(listResource.apiError.getMessage());
                    break;
                case SUCCESS:
                    hideStatusViews();
                    observationTypeList = listResource.data;
                    initList(observationTypeList);
                    break;
                case LOADING:
                    showProgress();
                    break;
            }
        });
    }

    void initList(List<ObservationTypeModel> observationTypeList) {

        noItemsLinearLayout.setVisibility(View.GONE);

        LiveData<Resource<List<QorgayModel>>> qorgayListLiveData = viewModel.getQorgayListLiveData(prefStorage.getNotificationToken());

        qorgayListLiveData.removeObservers(getViewLifecycleOwner());
        qorgayListLiveData.observe(getViewLifecycleOwner(), it -> {
            if (it != null) {
                switch (it.status) {
                    case ERROR:
                        showRetryButton(onRetryQorgayListLoading);
                        onToast(it.apiError.getMessage());
                        break;
                    case SUCCESS:
                        hideStatusViews();
                        initAdapter(it.data, observationTypeList);
                        break;
                    case LOADING:
                        showProgress();
                        break;
                }
            } else {
                showProgress();
                initAdapter(new ArrayList<QorgayModel>(0), observationTypeList);
            }
        });
    }

    void initAdapter(List<QorgayModel> data, List<ObservationTypeModel> observationTypeModels) {
        Collections.sort(
                data,
                (o1, o2) -> (int) (o2.getIncidentDateTimeLong() - o1.getIncidentDateTimeLong())
        );

        ConcatAdapter concatAdapter = new ConcatAdapter();

        ArrayList<QorgayModel> subData = new ArrayList<>();

        if (data.size() > 0) {
            noItemsLinearLayout.setVisibility(View.GONE);
            subData.add(data.get(0));
        } else {
            noItemsLinearLayout.setVisibility(View.VISIBLE);
        }

        long curDay;
        long prevDay;
        for (int i = 1; i < data.size(); i++) {
            curDay = data.get(i).getIncidentDateTimeLong();
            prevDay = data.get(i - 1).getIncidentDateTimeLong();
            if (isSameDay(curDay, prevDay)) {
                subData.add(data.get(i));
            } else {
                concatAdapter.addAdapter(new QorgayListAdapter(new ArrayList<>(subData), observationTypeModels));
                subData.clear();
                subData.add(data.get(i));
            }
        }

        if (subData.size() != 0) {
            concatAdapter.addAdapter(new QorgayListAdapter(new ArrayList<>(subData), observationTypeModels));
        }
        subData.clear();

        qorgayListRecyclerview.setAdapter(concatAdapter);
        qorgayListRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        qorgayListRecyclerview.setHasFixedSize(true);
    }

    private boolean isSameDay(long dayOne, long dayTwo) {
        LocalDate dateOne = new Date(dayOne).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateTwo = new Date(dayTwo).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return dateOne.isEqual(dateTwo);
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

    class QorgayListAdapter extends BaseAdapter<QorgayModel> {
        private static final int VIEW_TYPE_DATE = 1;
        private static final int VIEW_TYPE_QORGAY = 2;

        List<ObservationTypeModel> observationTypes;

        public QorgayListAdapter(List<QorgayModel> list, List<ObservationTypeModel> observationTypeModels) {
            this.observationTypes = observationTypeModels;

            submitList(list);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public DiffUtil.ItemCallback<QorgayModel> getDiffCallback() {
            return null;
        }

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            switch (viewType) {
                case VIEW_TYPE_DATE: {
                    View root = inflater.inflate(R.layout.item_date, parent, false);
                    return new DateViewHolder(root);
                }
                case VIEW_TYPE_QORGAY: {
                    View root = inflater.inflate(R.layout.item_qorgay, parent, false);
                    return new QorgayViewHolder(root);
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + viewType);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            if (getItemCount() != 0) {
                if (holder.getClass() == DateViewHolder.class) {
                    ((DateViewHolder) holder).setData(getItem(0).getIncidentDateTimeLong());
                } else {
                    ((QorgayViewHolder) holder).setData(getItem(position - 1));
                }
            }
        }

        @Override
        public int getItemCount() {
            return super.getItemCount() + 1;
        }


        @Override
        public int getItemViewType(int position) {
            if (position == 0) return VIEW_TYPE_DATE;
            else return VIEW_TYPE_QORGAY;
        }

        class DateViewHolder extends BaseViewHolder<Long> {

            @BindView(R.id.tv_date)
            TextView textView;

            public DateViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            @Override
            public void setData(Long item) {
                textView.setText(dateFormat.format(new Date(item)));
            }
        }

        class QorgayViewHolder extends BaseViewHolder<QorgayModel> {

            @BindView(R.id.tv_qorgay_id)
            TextView qorgayIdTextView;

            @BindView(R.id.i_status)
            ImageView statusImageView;

            @BindView(R.id.tv_obs_type)
            TextView obsTypeTextView;

            @BindView(R.id.cl_qorgay)
            ConstraintLayout qorgayConstraintLayout;

            public QorgayViewHolder(@NonNull View itemView) {
                super(itemView);
            }

            @Override
            public void setData(QorgayModel item) {

                String obsType = observationTypes.get(item.getDictKorgauObservationTypeId() - 1).getName();
                obsTypeTextView.setText(obsType);

                qorgayConstraintLayout.setOnClickListener(v -> {
                    new QorgayDetailsDialog(item, obsType).show(getChildFragmentManager(), null);
                });

                qorgayIdTextView.setText(String.valueOf(item.getId()));

                Log.d(TAG, "setData: status: " + item.getStatus());
                switch (item.getStatus()) {
                    case 1: {
                        statusImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_status_await, null));
                        break;
                    }
                    case 2: {
                        statusImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_status_accepted, null));
                        break;
                    }
                    case 3: {
                        statusImageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_status_rejected, null));
                        break;
                    }
                }
            }
        }
    }
}