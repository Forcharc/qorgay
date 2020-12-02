package kz.kmg.qorgau.ui.observations.driving;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagingData;

import kz.kmg.qorgau.QorgauApp;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.observations.driving.DrivingObservationModel;
import kz.kmg.qorgau.data.network.api.DrivingObservationsApi;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.ui.observations.BaseObservationListFragment;

import static kz.kmg.qorgau.ui.observations.driving.EditDrivingObservationFragment.PARAM_DRIVING_ID;

public class DrivingObservationListFragment extends BaseObservationListFragment<DrivingObservationModel> {

    DrivingObservationViewModel viewModel;
    private DrivingObservationsApi observationsApi;
    private QorgayApi qorgayApi;

    @Override
    public void navigateToObservationId(int workId) {
        Bundle bundle = new Bundle();
        bundle.putInt(PARAM_DRIVING_ID, workId);
        NavHostFragment.findNavController(this).navigate(R.id.editDrivingObservationFragment, bundle);
    }

    @Override
    public void deleteObservationById(Integer observationId, int position) {
        viewModel.removeDrivingById(cookie, observationId);
    }

    @Override
    public void initViewModel() {
        viewModel = new ViewModelProvider(this).get(DrivingObservationViewModel.class);
        observationsApi = ((QorgauApp) getActivity().getApplication()).drivingObservationsApi;
        qorgayApi = ((QorgauApp) getActivity().getApplication()).qorgayApi;
        viewModel.init(qorgayApi, observationsApi);
    }

    public void initPagingData() {
        LiveData<PagingData<DrivingObservationModel>> pagingData = viewModel.getObservations(observationsApi, cookie);
        pagingData.removeObservers(getViewLifecycleOwner());
        pagingData.observe(getViewLifecycleOwner(), workObservationModelPagingData -> adapter.submitData(getViewLifecycleOwner().getLifecycle(), /*(PagingData<BaseObservationModel>) (PagingData)*/ workObservationModelPagingData));
    }

    public LiveData<Resource<IsSuccessResponse>> getObservationByIdResultLiveData() {
        return viewModel.removeDrivingByIdResultLiveData();
    }
}
