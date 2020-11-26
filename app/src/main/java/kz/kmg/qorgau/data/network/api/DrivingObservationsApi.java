package kz.kmg.qorgau.data.network.api;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.observations.driving.DrivingObservationFormModel;
import kz.kmg.qorgau.data.model.observations.driving.DrivingObservationModel;
import kz.kmg.qorgau.data.model.observations.work.PlaceItemModel;
import kz.kmg.qorgau.data.model.observations.work.PlaceModel;
import kz.kmg.qorgau.data.model.observations.work.WorkObservationFormModel;
import kz.kmg.qorgau.data.model.observations.work.WorkObservationModel;
import kz.kmg.qorgau.data.model.paging.PagingModel;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DrivingObservationsApi {

    @NonNull
    @GET("pnb-driving/list")
    Single<PagingModel<DrivingObservationModel>> getDrivingObservations(@Header("Cookie") String cookie, @Query("page") int page);

    @NonNull
    @GET("pnb-driving/get-form")
    Flowable<Response<DrivingObservationFormModel>> getForm(@Header("Cookie") String cookie);

    @NonNull
    @GET("pnb-driving/get/{id}")
    Flowable<Response<DrivingObservationFormModel>> getDrivingById(@Header("Cookie") String cookie, @Path("id") String id);

    @NonNull
    @DELETE("pnb-driving/remove/{id}")
    Flowable<Response<IsSuccessResponse>> removeDrivingById(@Header("Cookie") String cookie, @Path("id") String id);

    @NonNull
    @POST("pnb-driving/save")
    Flowable<Response<IsSuccessResponse>> saveDriving(@Header("Cookie") String cookie, @Body DrivingObservationFormModel driving);

}
