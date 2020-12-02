package kz.kmg.qorgau.domain.interactors;


import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava2.PagingRx;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.home.NewsTopModel;
import kz.kmg.qorgau.data.model.observations.driving.DrivingObservationModel;
import kz.kmg.qorgau.data.model.paging.PagingModel;
import kz.kmg.qorgau.data.model.questionnaires.QuestionnaireFormModel;
import kz.kmg.qorgau.data.model.questionnaires.QuestionnaireModel;
import kz.kmg.qorgau.data.network.api.DrivingObservationsApi;
import kz.kmg.qorgau.data.network.api.QuestionnaireApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.ui.observations.driving.DrivingObservationsPagingSource;
import kz.kmg.qorgau.ui.questionnaire.QuestionnairesPagingSource;
import kz.kmg.qorgau.utils.error_handling.ErrorHelper;

public class QuestionnaireInteractor {

    public static @NotNull
    Flowable<PagingData<QuestionnaireModel>> getQuestionnaires(QuestionnaireApi api, String cookie) {
        return PagingRx.getFlowable(
                new Pager<>(
                        new PagingConfig(10),
                        () -> new QuestionnairesPagingSource(api, cookie)
                ));
    }

    public static @NotNull
    Flowable<Resource<QuestionnaireFormModel>> getQuestionnaireById(QuestionnaireApi api, int id) {
        return api.getQuestionnaireById(String.valueOf(id))
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<QuestionnaireFormModel>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());

    }

    public static @NotNull
    Flowable<Resource<IsSuccessResponse>> saveQuestionnaireAnswer(QuestionnaireApi api, String cookie, QuestionnaireFormModel questionnaireFormModel) {
        return api.saveQuestionnaireAnswer(cookie, questionnaireFormModel)
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<IsSuccessResponse>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

}
