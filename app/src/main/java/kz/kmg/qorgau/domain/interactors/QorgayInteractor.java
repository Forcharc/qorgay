package kz.kmg.qorgau.domain.interactors;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import kz.kmg.qorgau.data.model.QorgayModel;
import kz.kmg.qorgau.data.model.home.questionnaire.ObservationType;
import kz.kmg.qorgau.data.network.api.QorgayApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.utils.error_handling.ErrorHelper;
import okhttp3.MultipartBody;

public class QorgayInteractor {

    public static @NotNull
    Flowable<Resource<Object>> addQorgay(QorgayApi qorgayApi,
                                         QorgayModel qorgay
    ) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("DictKorgauObservationTypeId", String.valueOf(qorgay.getDictKorgauObservationTypeId()));
        builder.addFormDataPart("FullName", qorgay.getFullName());
        builder.addFormDataPart("IncidentDateTime", qorgay.getIncidentDateTime());
        builder.addFormDataPart("OrganizationId", String.valueOf(qorgay.getOrganizationId()));
        builder.addFormDataPart("OrganizationDepartmentId", String.valueOf(qorgay.getOrganizationDepartmentId()));
        builder.addFormDataPart("SupervisedOrganizationId", String.valueOf(qorgay.getSupervisedOrganizationId()));

        builder.addFormDataPart("Suggestion", qorgay.getSuggestion());
        builder.addFormDataPart("PossibleConsequence", qorgay.getPossibleConsequence());
        builder.addFormDataPart("Measure", qorgay.getMeasure());
        builder.addFormDataPart("ActionToEncourage", qorgay.getActionToEncourage());
        builder.addFormDataPart("IsDiscussed", String.valueOf(qorgay.isDiscussed()));
        builder.addFormDataPart("IsInformed", String.valueOf(qorgay.isInformed()));
        builder.addFormDataPart("InformTo", String.valueOf(qorgay.getInformTo()));
        builder.addFormDataPart("IsEliminated", String.valueOf(qorgay.isEliminated()));

/*

        // phone uid
        builder.addFormDataPart("", String.valueOf()); // phone
        builder.addFormDataPart("Files", String.valueOf());
        builder.addFormDataPart("DictKorgauObservationCategories", qorgay.getDictKorgauObservationCategories());
*/

        return qorgayApi.addQorgay(builder.build())
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<Object>getCityApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());
    }


    public static Flowable<Resource<List<ObservationType>>> getObservationTypes(QorgayApi qorgayApi) {

        return qorgayApi.getObservationTypes()
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<List<ObservationType>>getCityApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

}
