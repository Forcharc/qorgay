package kz.kmg.qorgau.ui.observations.work;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import java.util.List;

import kz.kmg.qorgau.data.model.create.DepartmentModel;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.create.OrganizationModel;
import kz.kmg.qorgau.data.model.observations.work.WorkObservationModel;
import kz.kmg.qorgau.data.model.observations.work.PlaceItemModel;
import kz.kmg.qorgau.data.model.observations.work.PlaceModel;
import kz.kmg.qorgau.data.model.observations.work.WorkObservationFormModel;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.data.network.api.WorkObservationsApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.domain.interactors.QorgayInteractor;
import kz.kmg.qorgau.domain.interactors.WorkObservationsInteractor;

public class WorkObservationViewModel extends ViewModel {
    QorgayApi qorgayApi;
    WorkObservationsApi workApi;

    MediatorLiveData<Resource<WorkObservationFormModel>> workLiveData = new MediatorLiveData<>();
    MediatorLiveData<Resource<IsSuccessResponse>> removeWorkByIdResultLiveData = new MediatorLiveData<>();
    MediatorLiveData<Resource<List<PlaceModel>>> placesByOrgIdLiveData = new MediatorLiveData<>();
    MediatorLiveData<Resource<List<PlaceItemModel>>> placeItemsByPlaceIdLiveData = new MediatorLiveData<>();
    MediatorLiveData<Resource<IsSuccessResponse>> saveWorkResultLiveData = new MediatorLiveData<>();
    MediatorLiveData<Resource<List<OrganizationModel>>> organizationsLiveData = new MediatorLiveData<>();
    MediatorLiveData<Resource<List<DepartmentModel>>> departmentsByOrgIdAuthorLiveData = new MediatorLiveData<>();
    MediatorLiveData<Resource<List<DepartmentModel>>> departmentsByOrgIdLiveData = new MediatorLiveData<>();

    public void init(QorgayApi qorgayApi, WorkObservationsApi workApi) {
        this.qorgayApi = qorgayApi;
        this.workApi = workApi;
    }

    public LiveData<PagingData<WorkObservationModel>> getObservations(WorkObservationsApi api, String cookie) {
        return LiveDataReactiveStreams.fromPublisher(WorkObservationsInteractor.getObservations(api, cookie));
    }

    public LiveData<Resource<WorkObservationFormModel>> getWorkLiveData() {
        return workLiveData;
    }

    public void loadForm(String cookie) {
        workLiveData.setValue(Resource.loading());
        final LiveData<Resource<WorkObservationFormModel>> response = LiveDataReactiveStreams.fromPublisher(WorkObservationsInteractor.getForm(workApi, cookie));

        workLiveData.addSource(response, res -> {
            workLiveData.setValue(res);
            workLiveData.removeSource(response);
        });
    }

    public  LiveData<Resource<WorkObservationFormModel>> getWorkByIdLiveData() {
        return workLiveData;
    }

    public void loadWorkById(String cookie, int id) {
        workLiveData.setValue(Resource.loading());
        final LiveData<Resource<WorkObservationFormModel>> response = LiveDataReactiveStreams.fromPublisher(WorkObservationsInteractor.getWorkById(workApi, cookie, id));

        workLiveData.addSource(response, res -> {
            workLiveData.setValue(res);
            workLiveData.removeSource(response);
        });
    }

    public  LiveData<Resource<IsSuccessResponse>> removeWorkByIdResultLiveData() {
        return removeWorkByIdResultLiveData;
    }

    public  void removeWorkById(String cookie, int id) {
        removeWorkByIdResultLiveData.setValue(Resource.loading());
        final LiveData<Resource<IsSuccessResponse>> response = LiveDataReactiveStreams.fromPublisher(WorkObservationsInteractor.removeWorkById(workApi, cookie, id));

        removeWorkByIdResultLiveData.addSource(response, res -> {
            removeWorkByIdResultLiveData.setValue(res);
            removeWorkByIdResultLiveData.removeSource(response);
        });
    }

    public  LiveData<Resource<List<PlaceModel>>> getPlacesByOrgIdLiveData() {
        return placesByOrgIdLiveData;
    }

    public  void loadPlacesByOrgId(String cookie, int id) {
        placesByOrgIdLiveData.setValue(Resource.loading());
        final LiveData<Resource<List<PlaceModel>>> response = LiveDataReactiveStreams.fromPublisher(WorkObservationsInteractor.getPlacesByOrgId(workApi, cookie, id));

        placesByOrgIdLiveData.addSource(response, res -> {
            placesByOrgIdLiveData.setValue(res);
            placesByOrgIdLiveData.removeSource(response);
        });
    }

    public   LiveData<Resource<List<PlaceItemModel>>> getPlaceItemsByPlaceIdLiveData() {
        return placeItemsByPlaceIdLiveData;
    }

    public   void loadPlaceItemsByPlaceId(String cookie, int id) {
        placeItemsByPlaceIdLiveData.setValue(Resource.loading());
        final LiveData<Resource<List<PlaceItemModel>>> response = LiveDataReactiveStreams.fromPublisher(WorkObservationsInteractor.getPlaceItemsByPlaceId(workApi, cookie, id));

        placeItemsByPlaceIdLiveData.addSource(response, res -> {
            placeItemsByPlaceIdLiveData.setValue(res);
            placeItemsByPlaceIdLiveData.removeSource(response);
        });
    }

    public  LiveData<Resource<IsSuccessResponse>> getSaveWorkResultLiveData() {
        return saveWorkResultLiveData;
    }

    public  void saveWork(String cookie, WorkObservationFormModel work) {
        saveWorkResultLiveData.setValue(Resource.loading());
        final LiveData<Resource<IsSuccessResponse>> response = LiveDataReactiveStreams.fromPublisher(WorkObservationsInteractor.saveWork(workApi, cookie, work));

        saveWorkResultLiveData.addSource(response, res -> {
            saveWorkResultLiveData.setValue(res);
            saveWorkResultLiveData.removeSource(response);
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
