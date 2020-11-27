package kz.kmg.qorgau.ui.observations;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import kz.kmg.qorgau.QorgauApp;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.observations.BaseObservationModel;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;
import kz.kmg.qorgau.ui.dialogs.NeedsAuthorizationDialog;
import kz.kmg.qorgau.utils.rv.QorgayLoadStateAdapter;
import kz.kmg.qorgau.utils.rv.RecyclerTouchListener;

public abstract class BaseObservationListFragment<T extends BaseObservationModel> extends BaseFragment implements QorgayLoadStateAdapter.LoadStateListener, NeedsAuthorizationDialog.NeedsAuthorizationDialogListener, ObservationListAdapter.ObservationListListener {

    private static final String TAG = "ObservationListFragment";
    private NavController navController;

    @BindView(R.id.rv)
    public RecyclerView recyclerView;



    protected String cookie;

    protected final ObservationListAdapter<T> adapter = new ObservationListAdapter<>(new DiffUtil.ItemCallback<T>() {
        @Override
        public boolean areItemsTheSame(@NonNull BaseObservationModel oldItem, @NonNull BaseObservationModel newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull BaseObservationModel oldItem, @NonNull BaseObservationModel newItem) {
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
        navController = NavHostFragment.findNavController(this);
        cookie = ((QorgauApp) getActivity().getApplication()).prefStorage.getCookie();
        initViewModel();

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

            getObservationByIdResultLiveData().observe(getViewLifecycleOwner(), resource -> {
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

    public abstract void getPagingData();

    public abstract void initViewModel();

    public abstract LiveData<Resource<IsSuccessResponse>> getObservationByIdResultLiveData();

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


}
