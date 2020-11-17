package kz.kmg.qorgau.ui.create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kz.kmg.qorgau.data.model.create.QorgayModel;
import kz.kmg.qorgau.data.model.create.CreateQorgayModel;
import kz.kmg.qorgau.data.model.create.DepartmentModel;
import kz.kmg.qorgau.data.model.create.ObservationCategoryModel;
import kz.kmg.qorgau.data.model.create.ObservationTypeModel;
import kz.kmg.qorgau.data.model.create.OrganizationModel;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.domain.interactors.QorgayInteractor;
import kz.kmg.qorgau.ui.base.view_model.BaseViewModel;

public class CreateQorgayViewModel extends BaseViewModel {
    private QorgayApi qorgayApi;

    private final MediatorLiveData<Resource<List<ObservationTypeModel>>> observationTypes = new MediatorLiveData<>();
    private final MediatorLiveData<Resource<List<OrganizationModel>>> organizations = new MediatorLiveData<>();
    private final MediatorLiveData<Resource<List<DepartmentModel>>> departments = new MediatorLiveData<>();
    private final MediatorLiveData<Resource<List<ObservationCategoryModel>>> observationCategories = new MediatorLiveData<>();

    private QorgayModel qorgayModel = new QorgayModel();
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


    public LiveData<Resource<CreateQorgayModel>> createQorgay() {
        final LiveData<Resource<CreateQorgayModel>> responseObservationTypes = LiveDataReactiveStreams.fromPublisher(QorgayInteractor.addQorgay(qorgayApi, qorgayModel));

        return responseObservationTypes;
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
        qorgayModel.setOrganizationDepartmentId(null);

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
        return qorgayModel.getDictKorgauObservationTypeId();
    }

    public void setPage1ObservationTypeId(int page1ObservationTypeId) {
        qorgayModel.setDictKorgauObservationTypeId(
                page1ObservationTypeId
        );
    }

    public String getPage2FullName() {
        return qorgayModel.getFullName();
    }

    public void setPage2FullName(String page2FullName) {
        qorgayModel.setFullName(page2FullName);
    }

    public String getPage3PhoneNumber() {
        return qorgayModel.getPhone();
    }

    public void setPage3PhoneNumber(String page3PhoneNumber) {
        qorgayModel.setPhone(page3PhoneNumber);
    }


    public Integer getPage5OrganizationId() {
        return qorgayModel.getOrganizationId();
    }

    public void setPage5OrganizationId(Integer organizationId) {
        this.loadDepartments(organizationId);
        qorgayModel.setOrganizationId(organizationId);
    }

    public String getPage5Contractor() {
        return qorgayModel.getContractor();
    }

    public void setPage5Contractor(String page5Contractor) {
        qorgayModel.setContractor(page5Contractor);
    }

    public Integer getPage6OrganizationDepartmentId() {
        return qorgayModel.getOrganizationDepartmentId();
    }

    public void setPage6OrganizationDepartmentId(Integer page6OrganizationDepartmentId) {
        qorgayModel.setOrganizationDepartmentId(page6OrganizationDepartmentId);
    }

    public Integer getPage7SupervisedOrganizationId() {
        return qorgayModel.getSupervisedOrganizationId();
    }

    public void setPage7SupervisedOrganizationId(Integer page7SupervisedOrganizationId) {
        qorgayModel.setSupervisedOrganizationId(page7SupervisedOrganizationId);
    }

    public String getPage7Object() {
        return qorgayModel.getSupervisedObject();
    }

    public void setPage7Object(String page7Object) {
        qorgayModel.setSupervisedObject(page7Object);
    }

    public Set<Integer> getPage8ObservationCategories() {
        return qorgayModel.getDictKorgauObservationCategories();
    }

    public void setPage8ObservationCategories(Set<Integer> page8ObservationCategories) {
        qorgayModel.setDictKorgauObservationCategories(page8ObservationCategories);
    }

    public String getPage9Suggestion() {
        return qorgayModel.getSuggestion();
    }

    public void setPage9Suggestion(String page9Suggestion) {
        qorgayModel.setSuggestion(page9Suggestion);
    }


    public String getPage4Date() {
        return qorgayModel.getDate();
    }

    public void setPage4Date(String page4Date) {
        qorgayModel.setDate(page4Date);
    }

    public String getPage4Time() {
        return qorgayModel.getTime();
    }

    public void setPage4Time(String page4Time) {
        qorgayModel.setTime(page4Time);
    }

    public String getPage11PossibleConsequence() {
        return qorgayModel.getPossibleConsequence();
    }

    public void setPage11PossibleConsequence(String page11PossibleConsequence) {
        qorgayModel.setPossibleConsequence(page11PossibleConsequence);
    }

    public String getPage12Measure() {
        return qorgayModel.getMeasure();
    }

    public void setPage12Measure(String page12Measure) {
        qorgayModel.setMeasure(page12Measure);
    }

    public String getPage13ActionToEncourage() {
        return qorgayModel.getActionToEncourage();
    }

    public void setPage13ActionToEncourage(String page13ActionToEncourage) {
        qorgayModel.setActionToEncourage(page13ActionToEncourage);
    }

    public Boolean isPage14IsDiscussed() {
        return qorgayModel.isDiscussed();
    }

    public void setPage14IsDiscussed(Boolean page14IsDiscussed) {
        qorgayModel.setDiscussed(page14IsDiscussed);
    }

    public Boolean isPage15IsInformed() {
        return qorgayModel.getInformed();
    }

    public void setPage15IsInformed(Boolean page15IsInformed) {
        qorgayModel.setInformed(page15IsInformed);
    }

    public String getPage16InformTo() {
        return qorgayModel.getInformTo();
    }

    public void setPage16InformTo(String page16InformTo) {
        qorgayModel.setInformTo(page16InformTo);
    }

    public Boolean isPage17IsEliminated() {
        return qorgayModel.getEliminated();
    }

    public void setPage17IsEliminated(Boolean page17IsEliminated) {
        qorgayModel.setEliminated(page17IsEliminated);
    }

    public ArrayList<File> getPage10Files() {
        return qorgayModel.getFiles();
    }


    public void clearQorgay() {
        qorgayModel = new QorgayModel();
    }
}
