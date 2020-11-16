package kz.kmg.qorgau.domain.interactors;


import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.QorgayModel;
import kz.kmg.qorgau.data.model.create.CreateQorgayModel;
import kz.kmg.qorgau.data.model.create.DepartmentModel;
import kz.kmg.qorgau.data.model.create.ObservationCategoryModel;
import kz.kmg.qorgau.data.model.create.ObservationTypeModel;
import kz.kmg.qorgau.data.model.create.OrganizationModel;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.data.network.base.ApiError;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.utils.error_handling.ErrorHelper;
import kz.kmg.qorgau.utils.files.FileUtil;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class QorgayInteractor {

    public static @NotNull
    Flowable<Resource<CreateQorgayModel>> addQorgay(QorgayApi qorgayApi,
                                         QorgayModel qorgay
    ) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        addNotNull(builder, "DictKorgauObservationTypeId", qorgay.getDictKorgauObservationTypeId());
        addNotNull(builder, "FullName", qorgay.getFullName());
        addNotNull(builder, "Phone", qorgay.getPhone());
        addNotNull(builder, "IncidentDateTime", qorgay.getIncidentDateTime());
        addNotNull(builder, "OrganizationId", qorgay.getOrganizationId());
        addNotNull(builder, "OrganizationDepartmentId", qorgay.getOrganizationDepartmentId());
        addNotNull(builder, "SupervisedOrganizationId", qorgay.getSupervisedOrganizationId());
        addNotNull(builder, "SupervisedObject", qorgay.getSupervisedObject());
        Set<Integer> obsCategories = qorgay.getDictKorgauObservationCategories();
        if (obsCategories != null) {
            Iterator<Integer> obsIterator = obsCategories.iterator();
            int j = 0;
            while (obsIterator.hasNext()) {
                addNotNull(builder, "DictKorgauObservationCategories[" + j + "]", obsIterator.next().toString());
                j++;
            }
        }
        addNotNull(builder, "Suggestion", qorgay.getSuggestion());


        for (int i = 0; qorgay.getFiles() != null && i < qorgay.getFiles().size(); i++) {
            File file = qorgay.getFiles().get(i);
            String mimeType = FileUtil.getMimeType(file.getAbsolutePath());
            RequestBody fileRequestBody = RequestBody.create(file, MediaType.get(mimeType));
            builder.addFormDataPart("Files[" + i + "]", qorgay.getFiles().get(i).getName(), fileRequestBody);
        }

        addNotNull(builder, "PossibleConsequence", qorgay.getPossibleConsequence());
        addNotNull(builder, "Measure", qorgay.getMeasure());
        addNotNull(builder, "ActionToEncourage", qorgay.getActionToEncourage());
        addNotNull(builder, "IsDiscussed", qorgay.isDiscussed());
        addNotNull(builder, "IsInformed", qorgay.isInformed());
        addNotNull(builder, "InformTo", qorgay.getInformTo());
        addNotNull(builder, "IsEliminated", qorgay.isEliminated());

        try {
            return qorgayApi.addQorgay(builder.build())
                    .onErrorReturn(ErrorHelper::getErrorResponse)
                    .map(response -> {
                        if (response.isSuccessful()) {
                            return Resource.success(response.body());
                        } else {
                            return ErrorHelper.<CreateQorgayModel>getQorgayApiError(response);
                        }
                    })
                    .subscribeOn(Schedulers.io());
        } catch (IllegalStateException e) {
            return Flowable.just(Resource.error(new ApiError("Specify data")));
        }
    }

    private static void addNotNull(MultipartBody.Builder builder, String field, Object object) {
        if (object != null && !object.toString().equals("")) {
            builder.addFormDataPart(field, object.toString());
        }
    }


    public static Flowable<Resource<List<ObservationTypeModel>>> getObservationTypes(QorgayApi qorgayApi) {

        return qorgayApi.getObservationTypes()
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<List<ObservationTypeModel>>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public static Flowable<Resource<List<OrganizationModel>>> getOrganizations(QorgayApi qorgayApi) {

        return qorgayApi.getOrganizations()
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<List<OrganizationModel>>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public static Flowable<Resource<List<DepartmentModel>>> getDepartments(QorgayApi qorgayApi, int organizationId) {

        return qorgayApi.getDepartments(organizationId)
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<List<DepartmentModel>>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public static Flowable<Resource<List<ObservationCategoryModel>>> getObservationCategories(QorgayApi qorgayApi) {

        return qorgayApi.getObservationCategories()
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<List<ObservationCategoryModel>>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());
    }
}
