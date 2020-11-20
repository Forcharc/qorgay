package kz.kmg.qorgau.ui.create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kz.kmg.qorgau.data.model.create.CreateQorgayModel;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.create.DepartmentModel;
import kz.kmg.qorgau.data.model.create.ObservationCategoryModel;
import kz.kmg.qorgau.data.model.create.ObservationTypeModel;
import kz.kmg.qorgau.data.model.create.OrganizationModel;
import kz.kmg.qorgau.data.model.list.QorgayModel;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.domain.interactors.QorgayInteractor;
import kz.kmg.qorgau.ui.base.view_model.BaseViewModel;

public class QorgayViewModel extends BaseViewModel {
    private QorgayApi qorgayApi;

    private final MediatorLiveData<Resource<List<ObservationTypeModel>>> observationTypes = new MediatorLiveData<>();
    private final MediatorLiveData<Resource<List<OrganizationModel>>> organizations = new MediatorLiveData<>();
    private final MediatorLiveData<Resource<List<DepartmentModel>>> departments = new MediatorLiveData<>();
    private final MediatorLiveData<Resource<List<ObservationCategoryModel>>> observationCategories = new MediatorLiveData<>();

    private final MediatorLiveData<Resource<IsSuccessResponse>> createQorgayResponse = new MediatorLiveData<>();
    private final MediatorLiveData<Resource<List<QorgayModel>>> qorgayList = new MediatorLiveData<>();

    private CreateQorgayModel createQorgayModel = new CreateQorgayModel();
/*
    private Integer page1ObservationTypeId;
    private String page2FullName;
    private String page3PhoneNumber;
    private String page4Date;

    private String page4Time;
    private Integer page5OrganizationId;
    private String page5Contractor;
    private Integer page6OrganizationDepartmentId;
    private Integer page7SupervisedOrganizationId;
    private String page7Object;
    private Set<Integer> page8ObservationCategories = new HashSet<>();
    private String page9Suggestion;

    private File page10Files;

    private String page11PossibleConsequence;
    private String page12Measure;
    private String page13ActionToEncourage;
    private Boolean page14IsDiscussed;
    private Boolean page15IsInformed;
    private String page16InformTo;
    private Boolean page17IsEliminated;
*/


    public LiveData<Resource<List<QorgayModel>>> getQorgayListLiveData(String phoneUid) {
        if (qorgayList.getValue() == null || qorgayList.getValue().status == Resource.Status.ERROR) {
            loadQorgayList(phoneUid);
        }
        return qorgayList;
    }

    public void loadQorgayList(String phoneUid) {
        qorgayList.setValue(Resource.loading());

        final LiveData<Resource<List<QorgayModel>>> sourse = LiveDataReactiveStreams.fromPublisher(QorgayInteractor.getQorgayList(qorgayApi, phoneUid));

        qorgayList.addSource(sourse, response -> {
            qorgayList.setValue(response);
            qorgayList.removeSource(sourse);
        });
    }


    public LiveData<Resource<IsSuccessResponse>> createQorgay(String notificationToken) {
        createQorgayResponse.setValue(Resource.loading());

        final LiveData<Resource<IsSuccessResponse>> response = LiveDataReactiveStreams.fromPublisher(QorgayInteractor.addQorgay(qorgayApi, createQorgayModel, notificationToken));

        createQorgayResponse.addSource(response, res -> {
            createQorgayResponse.setValue(res);
            createQorgayResponse.removeSource(response);
        });

        return createQorgayResponse;
    }

    public LiveData<Resource<List<ObservationTypeModel>>> getObservationTypes() {
        if (observationTypes.getValue() == null || observationTypes.getValue().status == Resource.Status.ERROR) {
            observationTypes.setValue(Resource.loading());

            final LiveData<Resource<List<ObservationTypeModel>>> responseObservationTypes = LiveDataReactiveStreams.fromPublisher(QorgayInteractor.getObservationTypes(qorgayApi));

            observationTypes.addSource(responseObservationTypes, res -> {
                observationTypes.setValue(res);
                observationTypes.removeSource(responseObservationTypes);
            });
        }
        return observationTypes;
    }

    public LiveData<Resource<List<OrganizationModel>>> getOrganizations() {
        if (organizations.getValue() == null || organizations.getValue().status == Resource.Status.ERROR) {
            organizations.setValue(Resource.loading());

            final LiveData<Resource<List<OrganizationModel>>> sourse = LiveDataReactiveStreams.fromPublisher(QorgayInteractor.getOrganizations(qorgayApi));

            organizations.addSource(sourse, response -> {
                organizations.setValue(response);
                organizations.removeSource(sourse);
            });
        }
        return organizations;
    }

    public LiveData<Resource<List<ObservationCategoryModel>>> getObservationCategories() {
        if (observationCategories.getValue() == null || observationCategories.getValue().status == Resource.Status.ERROR) {
            observationCategories.setValue(Resource.loading());

            final LiveData<Resource<List<ObservationCategoryModel>>> source = LiveDataReactiveStreams.fromPublisher(QorgayInteractor.getObservationCategories(qorgayApi));

            observationCategories.addSource(source, response -> {
                observationCategories.setValue(response);
                observationCategories.removeSource(source);
            });
        }
        return observationCategories;
    }

    public MediatorLiveData<Resource<List<DepartmentModel>>> getDepartments() {
        return departments;
    }


    public void loadDepartments(int organizationId) {
        createQorgayModel.setOrganizationDepartmentId(null);

        departments.setValue(Resource.loading());

        final LiveData<Resource<List<DepartmentModel>>> sourse = LiveDataReactiveStreams.fromPublisher(QorgayInteractor.getDepartments(qorgayApi, organizationId));

        departments.addSource(sourse, response -> {
            departments.setValue(response);
            departments.removeSource(sourse);
        });
    }

    public void setQorgayApi(QorgayApi qorgayApi) {
        this.qorgayApi = qorgayApi;
    }


    public Integer getPage1ObservationTypeId() {
        return createQorgayModel.getDictKorgauObservationTypeId();
    }

    public void setPage1ObservationTypeId(int page1ObservationTypeId) {
        createQorgayModel.setDictKorgauObservationTypeId(
                page1ObservationTypeId
        );
    }

    public String getPage2FullName() {
        return createQorgayModel.getFullName();
    }

    public void setPage2FullName(String page2FullName) {
        createQorgayModel.setFullName(page2FullName);
    }

    public String getPage3PhoneNumber() {
        return createQorgayModel.getPhone();
    }

    public void setPage3PhoneNumber(String page3PhoneNumber) {
        createQorgayModel.setPhone(page3PhoneNumber);
    }


    public Integer getPage5OrganizationId() {
        return createQorgayModel.getOrganizationId();
    }

    public void setPage5OrganizationId(Integer organizationId) {
        this.loadDepartments(organizationId);
        createQorgayModel.setOrganizationId(organizationId);
    }

    public String getPage5Contractor() {
        return createQorgayModel.getContractor();
    }

    public void setPage5Contractor(String page5Contractor) {
        createQorgayModel.setContractor(page5Contractor);
    }

    public Integer getPage6OrganizationDepartmentId() {
        return createQorgayModel.getOrganizationDepartmentId();
    }

    public void setPage6OrganizationDepartmentId(Integer page6OrganizationDepartmentId) {
        createQorgayModel.setOrganizationDepartmentId(page6OrganizationDepartmentId);
    }

    public Integer getPage7SupervisedOrganizationId() {
        return createQorgayModel.getSupervisedOrganizationId();
    }

    public void setPage7SupervisedOrganizationId(Integer page7SupervisedOrganizationId) {
        createQorgayModel.setSupervisedOrganizationId(page7SupervisedOrganizationId);
    }

    public String getPage7Object() {
        return createQorgayModel.getSupervisedObject();
    }

    public void setPage7Object(String page7Object) {
        createQorgayModel.setSupervisedObject(page7Object);
    }

    public Set<Integer> getPage8ObservationCategories() {
        return createQorgayModel.getDictKorgauObservationCategories();
    }

    public void setPage8ObservationCategories(Set<Integer> page8ObservationCategories) {
        createQorgayModel.setDictKorgauObservationCategories(page8ObservationCategories);
    }

    public String getPage9Suggestion() {
        return createQorgayModel.getSuggestion();
    }

    public void setPage9Suggestion(String page9Suggestion) {
        createQorgayModel.setSuggestion(page9Suggestion);
    }


    public String getPage4Date() {
        return createQorgayModel.getDate();
    }

    public void setPage4Date(String page4Date) {
        createQorgayModel.setDate(page4Date);
    }

    public String getPage4Time() {
        return createQorgayModel.getTime();
    }

    public void setPage4Time(String page4Time) {
        createQorgayModel.setTime(page4Time);
    }

    public String getPage11PossibleConsequence() {
        return createQorgayModel.getPossibleConsequence();
    }

    public void setPage11PossibleConsequence(String page11PossibleConsequence) {
        createQorgayModel.setPossibleConsequence(page11PossibleConsequence);
    }

    public String getPage12Measure() {
        return createQorgayModel.getMeasure();
    }

    public void setPage12Measure(String page12Measure) {
        createQorgayModel.setMeasure(page12Measure);
    }

    public String getPage13ActionToEncourage() {
        return createQorgayModel.getActionToEncourage();
    }

    public void setPage13ActionToEncourage(String page13ActionToEncourage) {
        createQorgayModel.setActionToEncourage(page13ActionToEncourage);
    }

    public Boolean isPage14IsDiscussed() {
        return createQorgayModel.isDiscussed();
    }

    public void setPage14IsDiscussed(Boolean page14IsDiscussed) {
        createQorgayModel.setDiscussed(page14IsDiscussed);
    }

    public Boolean isPage15IsInformed() {
        return createQorgayModel.getInformed();
    }

    public void setPage15IsInformed(Boolean page15IsInformed) {
        createQorgayModel.setInformed(page15IsInformed);
    }

    public String getPage16InformTo() {
        return createQorgayModel.getInformTo();
    }

    public void setPage16InformTo(String page16InformTo) {
        createQorgayModel.setInformTo(page16InformTo);
    }

    public Boolean isPage17IsEliminated() {
        return createQorgayModel.getEliminated();
    }

    public void setPage17IsEliminated(Boolean page17IsEliminated) {
        createQorgayModel.setEliminated(page17IsEliminated);
    }

    public ArrayList<File> getPage10Files() {
        return createQorgayModel.getFiles();
    }


    public void clearQorgay() {
        qorgayList.setValue(null);
        createQorgayModel = new CreateQorgayModel();
    }

    public void resetList() {
        qorgayList.setValue(null);
    }
}
