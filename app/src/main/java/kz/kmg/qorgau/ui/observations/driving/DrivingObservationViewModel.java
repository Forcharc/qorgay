package kz.kmg.qorgau.ui.observations.driving;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import java.util.List;

import kz.kmg.qorgau.data.model.create.DepartmentModel;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.create.OrganizationModel;
import kz.kmg.qorgau.data.model.observations.driving.DrivingObservationFormModel;
import kz.kmg.qorgau.data.model.observations.driving.DrivingObservationModel;
import kz.kmg.qorgau.data.network.api.DrivingObservationsApi;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.domain.interactors.DrivingObservationsInteractor;
import kz.kmg.qorgau.domain.interactors.QorgayInteractor;

public class DrivingObservationViewModel extends ViewModel {
    QorgayApi qorgayApi;
    DrivingObservationsApi drivingApi;

    MediatorLiveData<Resource<DrivingObservationFormModel>> drivingLiveData = new MediatorLiveData<>();
    MediatorLiveData<Resource<IsSuccessResponse>> removeDrivingByIdResultLiveData = new MediatorLiveData<>();
    MediatorLiveData<Resource<IsSuccessResponse>> saveDrivingResultLiveData = new MediatorLiveData<>();
    MediatorLiveData<Resource<List<OrganizationModel>>> organizationsLiveData = new MediatorLiveData<>();
    MediatorLiveData<Resource<List<DepartmentModel>>> departmentsByOrgIdAuthorLiveData = new MediatorLiveData<>();
    MediatorLiveData<Resource<List<DepartmentModel>>> departmentsByOrgIdLiveData = new MediatorLiveData<>();

    public void init(QorgayApi qorgayApi, DrivingObservationsApi drivingApi) {
        this.qorgayApi = qorgayApi;
        this.drivingApi = drivingApi;
    }

    public LiveData<PagingData<DrivingObservationModel>> getObservations(DrivingObservationsApi api, String cookie) {
        return LiveDataReactiveStreams.fromPublisher(DrivingObservationsInteractor.getObservations(api, cookie));
    }

    public LiveData<Resource<DrivingObservationFormModel>> getDrivingLiveData() {
        return drivingLiveData;
    }

    public void loadForm(String cookie) {
        drivingLiveData.setValue(Resource.loading());
        final LiveData<Resource<DrivingObservationFormModel>> response = LiveDataReactiveStreams.fromPublisher(DrivingObservationsInteractor.getForm(drivingApi, cookie));

        drivingLiveData.addSource(response, res -> {
            drivingLiveData.setValue(res);
            drivingLiveData.removeSource(response);
        });
    }

    public  LiveData<Resource<DrivingObservationFormModel>> getDrivingByIdLiveData() {
        return drivingLiveData;
    }

    public void loadDrivingById(String cookie, int id) {
        drivingLiveData.setValue(Resource.loading());
        final LiveData<Resource<DrivingObservationFormModel>> response = LiveDataReactiveStreams.fromPublisher(DrivingObservationsInteractor.getDrivingById(drivingApi, cookie, id));

        drivingLiveData.addSource(response, res -> {
            drivingLiveData.setValue(res);
            drivingLiveData.removeSource(response);
        });
    }

    public  LiveData<Resource<IsSuccessResponse>> removeDrivingByIdResultLiveData() {
        return removeDrivingByIdResultLiveData;
    }

    public  void removeDrivingById(String cookie, int id) {
        removeDrivingByIdResultLiveData.setValue(Resource.loading());
        final LiveData<Resource<IsSuccessResponse>> response = LiveDataReactiveStreams.fromPublisher(DrivingObservationsInteractor.removeDrivingById(drivingApi, cookie, id));

        removeDrivingByIdResultLiveData.addSource(response, res -> {
            removeDrivingByIdResultLiveData.setValue(res);
            removeDrivingByIdResultLiveData.removeSource(response);
        });
    }


    public  LiveData<Resource<IsSuccessResponse>> getSaveDrivingResultLiveData() {
        return saveDrivingResultLiveData;
    }

    public  void saveDriving(String cookie, DrivingObservationFormModel Driving) {
        saveDrivingResultLiveData.setValue(Resource.loading());
        final LiveData<Resource<IsSuccessResponse>> response = LiveDataReactiveStreams.fromPublisher(DrivingObservationsInteractor.saveDriving(drivingApi, cookie, Driving));

        saveDrivingResultLiveData.addSource(response, res -> {
            saveDrivingResultLiveData.setValue(res);
            saveDrivingResultLiveData.removeSource(response);
        });
    }

    public  LiveData<Resource<List<OrganizationModel>>> getOrganizationsLiveData() {
        return organizationsLiveData;
    }

    public void loadOrganizations() {
        organizationsLiveData.setValue(Resource.loading());
        final LiveData<Resource<List<OrganizationModel>>> response = LiveDataReactiveStreams.fromPublisher(QorgayInteractor.getOrganizations(qorgayApi));

        organizationsLiveData.addSource(response, res -> {
            organizationsLiveData.setValue(res);
            organizationsLiveData.removeSource(response);
        });
    }

    public LiveData<Resource<List<DepartmentModel>>> getDepartmentsByOrgIdAuthorLiveData() {
        return departmentsByOrgIdAuthorLiveData;
    }

    public LiveData<Resource<List<DepartmentModel>>> getDepartmentsByOrgIdLiveData() {
        return departmentsByOrgIdLiveData;
    }

    public  void loadDepartmentsByOrgId(int id, boolean isAuthor) {
        if (isAuthor) {
            departmentsByOrgIdAuthorLiveData.setValue(Resource.loading());
        } else {
            departmentsByOrgIdLiveData.setValue(Resource.loading());
        }
        final LiveData<Resource<List<DepartmentModel>>> response = LiveDataReactiveStreams.fromPublisher(QorgayInteractor.getDepartments(qorgayApi, id));

        if (isAuthor) {
            departmentsByOrgIdAuthorLiveData.addSource(response, res -> {
                departmentsByOrgIdAuthorLiveData.setValue(res);
                departmentsByOrgIdAuthorLiveData.removeSource(response);
            });
        } else {
            departmentsByOrgIdLiveData.addSource(response, res -> {
                departmentsByOrgIdLiveData.setValue(res);
                departmentsByOrgIdLiveData.removeSource(response);
            });
        }
    }

}
