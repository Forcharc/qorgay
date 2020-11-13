package kz.kmg.qorgau.ui.create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kz.kmg.qorgau.data.model.QorgayModel;
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


    public LiveData<Resource<CreateQorgayModel>> createQorgay() {
        File[] files = {page10Files};
        QorgayModel qorgayModel = new QorgayModel(
                page1ObservationTypeId,
                page2FullName,
                page3PhoneNumber,
                page4Date + " " + page4Time,
                page5OrganizationId,
                page5Contractor != null && page5Contractor.length() > 0,
                page5Contractor,
                page6OrganizationDepartmentId,
                page7SupervisedOrganizationId,
                page7Object,
                page8ObservationCategories.toArray(new Integer[page8ObservationCategories.size()]),
                page9Suggestion,
                files,
                page11PossibleConsequence,
                page12Measure,
                page13ActionToEncourage,
                page14IsDiscussed,
                page15IsInformed,
                page16InformTo,
                page17IsEliminated
        );
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
        page6OrganizationDepartmentId = null;

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
        return page1ObservationTypeId;
    }

    public void setPage1ObservationTypeId(int page1ObservationTypeId) {
        this.page1ObservationTypeId = page1ObservationTypeId;
    }

    public String getPage2FullName() {
        return page2FullName;
    }

    public void setPage2FullName(String page2FullName) {
        this.page2FullName = page2FullName;
    }

    public String getPage3PhoneNumber() {
        return page3PhoneNumber;
    }

    public void setPage3PhoneNumber(String page3PhoneNumber) {
        this.page3PhoneNumber = page3PhoneNumber;
    }


    public Integer getPage5OrganizationId() {
        return page5OrganizationId;
    }

    public void setPage5OrganizationId(Integer organizationId) {
        this.loadDepartments(organizationId);
        this.page5OrganizationId = organizationId;
    }

    public String getPage5Contractor() {
        return page5Contractor;
    }

    public void setPage5Contractor(String page5Contractor) {
        this.page5Contractor = page5Contractor;
    }

    public Integer getPage6OrganizationDepartmentId() {
        return page6OrganizationDepartmentId;
    }

    public void setPage6OrganizationDepartmentId(Integer page6OrganizationDepartmentId) {
        this.page6OrganizationDepartmentId = page6OrganizationDepartmentId;
    }

    public Integer getPage7SupervisedOrganizationId() {
        return page7SupervisedOrganizationId;
    }

    public void setPage7SupervisedOrganizationId(Integer page7SupervisedOrganizationId) {
        this.page7SupervisedOrganizationId = page7SupervisedOrganizationId;
    }

    public String getPage7Object() {
        return page7Object;
    }

    public void setPage7Object(String page7Object) {
        this.page7Object = page7Object;
    }

    public Set<Integer> getPage8ObservationCategories() {
        return page8ObservationCategories;
    }

    public void setPage8ObservationCategories(Set<Integer> page8ObservationCategories) {
        this.page8ObservationCategories = page8ObservationCategories;
    }

    public String getPage9Suggestion() {
        return page9Suggestion;
    }

    public void setPage9Suggestion(String page9Suggestion) {
        this.page9Suggestion = page9Suggestion;
    }



    public String getPage4Date() {
        return page4Date;
    }

    public void setPage4Date(String page4Date) {
        this.page4Date = page4Date;
    }

    public String getPage4Time() {
        return page4Time;
    }

    public void setPage4Time(String page4Time) {
        this.page4Time = page4Time;
    }

    public String getPage11PossibleConsequence() {
        return page11PossibleConsequence;
    }

    public void setPage11PossibleConsequence(String page11PossibleConsequence) {
        this.page11PossibleConsequence = page11PossibleConsequence;
    }

    public String getPage12Measure() {
        return page12Measure;
    }

    public void setPage12Measure(String page12Measure) {
        this.page12Measure = page12Measure;
    }

    public String getPage13ActionToEncourage() {
        return page13ActionToEncourage;
    }

    public void setPage13ActionToEncourage(String page13ActionToEncourage) {
        this.page13ActionToEncourage = page13ActionToEncourage;
    }

    public Boolean isPage14IsDiscussed() {
        return page14IsDiscussed;
    }

    public void setPage14IsDiscussed(Boolean page14IsDiscussed) {
        this.page14IsDiscussed = page14IsDiscussed;
    }

    public Boolean isPage15IsInformed() {
        return page15IsInformed;
    }

    public void setPage15IsInformed(Boolean page15IsInformed) {
        this.page15IsInformed = page15IsInformed;
    }

    public String getPage16InformTo() {
        return page16InformTo;
    }

    public void setPage16InformTo(String page16InformTo) {
        this.page16InformTo = page16InformTo;
    }

    public Boolean isPage17IsEliminated() {
        return page17IsEliminated;
    }

    public void setPage17IsEliminated(Boolean page17IsEliminated) {
        this.page17IsEliminated = page17IsEliminated;
    }

    public File getPage10Files() {
        return page10Files;
    }

    public void setPage10Files(File page10Files) {
        this.page10Files = page10Files;
    }

}
