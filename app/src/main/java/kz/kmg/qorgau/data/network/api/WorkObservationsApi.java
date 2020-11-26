package kz.kmg.qorgau.data.network.api;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.observations.WorkObservationModel;
import kz.kmg.qorgau.data.model.paging.PagingModel;
import kz.kmg.qorgau.data.model.work_observations.PlaceItemModel;
import kz.kmg.qorgau.data.model.work_observations.PlaceModel;
import kz.kmg.qorgau.data.model.work_observations.WorkObservationFormModel;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WorkObservationsApi  {

    @NonNull
    @GET("pnb-work/list")
    Single<PagingModel<WorkObservationModel>> getWorkObservations(@Header("Cookie") String cookie, @Query("page") int page);

    @NonNull
    @GET("pnb-work/get-form")
    Flowable<Response<WorkObservationFormModel>> getForm(@Header("Cookie") String cookie);

    @NonNull
    @GET("pnb-work/get/{id}")
    Flowable<Response<WorkObservationFormModel>> getWorkById(@Header("Cookie") String cookie, @Path("id") String id);

    @NonNull
    @DELETE("pnb-work/remove/{id}")
    Flowable<Response<IsSuccessResponse>> removeWorkById(@Header("Cookie") String cookie, @Path("id") String id);

    @NonNull
    @GET("admin/Place/GetByOrganizationId")
    Flowable<Response<List<PlaceModel>>> getPlacesByOrgId(@Header("Cookie") String cookie, @Query("organizationId") int organizationId);

    @NonNull
    @GET("admin/PlaceItem/GetByPlaceId")
    Flowable<Response<List<PlaceItemModel>>> getPlaceItemsByPlaceId(@Header("Cookie") String cookie, @Query("placeId") int placeId);

    @NonNull
    @POST("pnb-work/save")
    Flowable<Response<IsSuccessResponse>> saveWork(@Header("Cookie") String cookie, @Body WorkObservationFormModel work);
}
