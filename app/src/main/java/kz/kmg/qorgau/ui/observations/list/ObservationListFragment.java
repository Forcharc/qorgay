package kz.kmg.qorgau.ui.observations.list;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.CombinedLoadStates;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import kz.kmg.qorgau.QorgauApp;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.observations.WorkObservationModel;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.data.network.api.WorkObservationsApi;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;
import kz.kmg.qorgau.ui.dialogs.NeedsAuthorizationDialog;
import kz.kmg.qorgau.ui.observations.WorkObservationViewModel;
import kz.kmg.qorgau.utils.rv.QorgayLoadStateAdapter;
import kz.kmg.qorgau.utils.rv.RecyclerTouchListener;

import static kz.kmg.qorgau.ui.observations.add.EditWorkObservationFragment.PARAM_WORK_ID;

public class ObservationListFragment extends BaseFragment implements QorgayLoadStateAdapter.LoadStateListener, NeedsAuthorizationDialog.NeedsAuthorizationDialogListener, ObservationListAdapter.ObservationListListener {

    private static final String TAG = "ObservationListFragment";
    private NavController navController;

    @BindView(R.id.rv)
    public RecyclerView recyclerView;

    WorkObservationViewModel viewModel;

    private WorkObservationsApi observationsApi;
    private QorgayApi qorgayApi;

    String cookie;

    private final ObservationListAdapter adapter = new ObservationListAdapter(new DiffUtil.ItemCallback<WorkObservationModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull WorkObservationModel oldItem, @NonNull WorkObservationModel newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull WorkObservationModel oldItem, @NonNull WorkObservationModel newItem) {
            return oldItem.getDate().equals(newItem.getDate()) && oldItem.getOrgName().equals(newItem.getOrgName()) && oldItem.getId().equals(newItem.getId());
        }
    },
            this);

    @Override
    public int getContentView() {
        return R.layout.fragment_observation_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(WorkObservationViewModel.class);
        navController = NavHostFragment.findNavController(this);
        observationsApi = ((QorgauApp) getActivity().getApplication()).workObservationsApi;
        qorgayApi = ((QorgauApp) getActivity().getApplication()).qorgayApi;
        cookie = ((QorgauApp) getActivity().getApplication()).prefStorage.getCookie();
        viewModel.init(qorgayApi, observationsApi);

        if (cookie == null || cookie.length() == 0) {
            NeedsAuthorizationDialog noAuthDialog = new NeedsAuthorizationDialog();
            noAuthDialog.setListener(this);
            noAuthDialog.show(getChildFragmentManager(), null);
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter.getWithLoadStateHeaderAndFooter(new QorgayLoadStateAdapter(this), new QorgayLoadStateAdapter(this)));

            getPagingData();

            RecyclerTouchListener touchListener = new RecyclerTouchListener(getActivity(), recyclerView);
            touchListener.setSwipeOptionViews(R.id.iv_edit, R.id.iv_delete)//,R.id.edit_task)
                    .setSwipeable(R.id.cl_foreground, R.id.cl_background, (viewID, position) -> {});
            recyclerView.addOnItemTouchListener(touchListener);

            viewModel.removeWorkByIdResultLiveData().observe(getViewLifecycleOwner(), resource -> {
                switch (resource.status) {
                    case ERROR:
                        onToast(resource.apiError.getMessage());
                        break;
                    case SUCCESS:
                        getPagingData();
                        break;
                    case LOADING:
                        break;
                }
            });
        }
    }

    void getPagingData() {
        LiveData<PagingData<WorkObservationModel>> pagingData = viewModel.getObservations(observationsApi, cookie);
        pagingData.removeObservers(getViewLifecycleOwner());
        pagingData.observe(getViewLifecycleOwner(), workObservationModelPagingData -> {
            adapter.submitData(getViewLifecycleOwner().getLifecycle(), workObservationModelPagingData);
        });
    }


    @Override
    public void onRetry() {
        adapter.retry();
    }

    @Override
    public void onGoBack() {
        navController.popBackStack();
    }

    @Override
    public void onGoToAuth() {
        navController.navigate(R.id.navigation_profile);
    }

    @Override
    public void navigateToWorkId(int workId) {
        Bundle bundle = new Bundle();
        bundle.putInt(PARAM_WORK_ID, workId);
        navController.navigate(R.id.addWorkObservationFragment, bundle);
    }

    @Override
    public void deleteWorkById(Integer workId, int position) {
        viewModel.removeWorkById(cookie, workId);
    }
}
