package kz.kmg.qorgau.ui.observations.work;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagingData;

import kz.kmg.qorgau.QorgauApp;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.observations.BaseObservationModel;
import kz.kmg.qorgau.data.model.observations.work.WorkObservationModel;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.data.network.api.WorkObservationsApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.ui.observations.BaseObservationListFragment;

import static kz.kmg.qorgau.ui.observations.work.EditWorkObservationFragment.PARAM_WORK_ID;


public class WorkObservationListFragment extends BaseObservationListFragment<WorkObservationModel> {

    WorkObservationViewModel viewModel;
    private WorkObservationsApi observationsApi;
    private QorgayApi qorgayApi;

    @Override
    public void navigateToObservationId(int workId) {
        Bundle bundle = new Bundle();
        bundle.putInt(PARAM_WORK_ID, workId);
        NavHostFragment.findNavController(this).navigate(R.id.addWorkObservationFragment, bundle);
    }

    @Override
    public void deleteObservationById(Integer observationId, int position) {
        viewModel.removeWorkById(cookie, observationId);
    }

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(WorkObservationViewModel.class);
        observationsApi = ((QorgauApp) getActivity().getApplication()).workObservationsApi;
        qorgayApi = ((QorgauApp) getActivity().getApplication()).qorgayApi;
        viewModel.init(qorgayApi, observationsApi);
    }

    public void initPagingData() {
        LiveData<PagingData<WorkObservationModel>> pagingData = viewModel.getObservations(observationsApi, cookie);
        pagingData.removeObservers(getViewLifecycleOwner());
        pagingData.observe(getViewLifecycleOwner(), workObservationModelPagingData -> {
            adapter.submitData(getViewLifecycleOwner().getLifecycle(), workObservationModelPagingData);
        });
    }

    public LiveData<Resource<IsSuccessResponse>> getObservationByIdResultLiveData() {
        return viewModel.removeWorkByIdResultLiveData();
    }
}
