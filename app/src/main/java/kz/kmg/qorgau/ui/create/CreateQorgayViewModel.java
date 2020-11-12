package kz.kmg.qorgau.ui.create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

import kz.kmg.qorgau.data.model.home.questionnaire.ObservationType;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.domain.interactors.QorgayInteractor;
import kz.kmg.qorgau.ui.base.view_model.BaseViewModel;

public class CreateQorgayViewModel extends BaseViewModel {
    private QorgayApi qorgayApi;
    private MediatorLiveData<Resource<List<ObservationType>>> observationTypes = new MediatorLiveData<>();

    private Integer page1ObservationTypeId;


    public LiveData<Resource<List<ObservationType>>> getObservationTypes() {
        if (observationTypes.getValue() == null  || observationTypes.getValue().status == Resource.Status.ERROR) {
            observationTypes.setValue(Resource.loading());

            final LiveData<Resource<List<ObservationType>>> responseObservationTypes = LiveDataReactiveStreams.fromPublisher(QorgayInteractor.getObservationTypes(qorgayApi));

            observationTypes.addSource(responseObservationTypes, res -> {
                observationTypes.setValue(res);
                observationTypes.removeSource(responseObservationTypes);
            });
        }
        return observationTypes;
    }

    public void setQorgayApi(QorgayApi qorgayApi) {
        this.qorgayApi = qorgayApi;
    }


    public int getPage1ObservationTypeId() {
        return page1ObservationTypeId;
    }

    public void setPage1ObservationTypeId(int page1ObservationTypeId) {
        this.page1ObservationTypeId = page1ObservationTypeId;
    }
}
