package kz.kmg.qorgau.data.network.api;

import androidx.annotation.NonNull;

import io.reactivex.Flowable;
import io.reactivex.Single;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.paging.PagingModel;
import kz.kmg.qorgau.data.model.questionnaires.QuestionnaireFormModel;
import kz.kmg.qorgau.data.model.questionnaires.QuestionnaireModel;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface QuestionnaireApi {

    @NonNull
    @GET("questionnaire/list")
    Single<PagingModel<QuestionnaireModel>> getQuestionnaires(@Header("Cookie") String cookie, @Query("page") String page);

    @NonNull
    @GET("questionnaire/get/{id}")
    Flowable<Response<QuestionnaireFormModel>> getQuestionnaireById(@Path("id") String id);

    @NonNull
    @POST("questionnaire/save-answer")
    Flowable<Response<IsSuccessResponse>> saveQuestionnaireAnswer(@Header("Cookie") String cookie, @Body QuestionnaireFormModel questionnaireFormModel);
}
