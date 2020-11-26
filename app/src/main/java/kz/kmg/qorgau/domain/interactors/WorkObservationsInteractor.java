package kz.kmg.qorgau.domain.interactors;


import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava2.PagingRx;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.observations.work.WorkObservationModel;
import kz.kmg.qorgau.data.model.observations.work.PlaceItemModel;
import kz.kmg.qorgau.data.model.observations.work.PlaceModel;
import kz.kmg.qorgau.data.model.observations.work.WorkObservationFormModel;
import kz.kmg.qorgau.data.network.api.WorkObservationsApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.ui.observations.work.WorkObservationsPagingSource;
import kz.kmg.qorgau.utils.error_handling.ErrorHelper;

public class WorkObservationsInteractor {

    public static @NotNull
    Flowable<PagingData<WorkObservationModel>> getObservations(WorkObservationsApi api, String cookie) {
        return PagingRx.getFlowable(
                new Pager<>(
                        new PagingConfig(10),
                        () -> new WorkObservationsPagingSource(api, cookie)
                ));
    }


    public static @NotNull
    Flowable<Resource<WorkObservationFormModel>> getForm(WorkObservationsApi api, String cookie) {
        return api.getForm(cookie)
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<WorkObservationFormModel>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());

    }

    public static @NotNull
    Flowable<Resource<WorkObservationFormModel>> getWorkById(WorkObservationsApi api, String cookie, int id) {
        return api.getWorkById(cookie, String.valueOf(id))
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<WorkObservationFormModel>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public static @NotNull
    Flowable<Resource<IsSuccessResponse>> removeWorkById(WorkObservationsApi api, String cookie, int id) {
        return api.removeWorkById(cookie, String.valueOf(id))
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

    public static @NotNull
    Flowable<Resource<List<PlaceModel>>> getPlacesByOrgId(WorkObservationsApi api, String cookie, int id) {
        return api.getPlacesByOrgId(cookie, id)
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<List<PlaceModel>>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public static @NotNull
    Flowable<Resource<List<PlaceItemModel>>> getPlaceItemsByPlaceId(WorkObservationsApi api, String cookie, int id) {
        return api.getPlaceItemsByPlaceId(cookie, id)
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<List<PlaceItemModel>>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public static @NotNull
    Flowable<Resource<IsSuccessResponse>> saveWork(WorkObservationsApi api, String cookie, WorkObservationFormModel work) {
        return api.saveWork(cookie, work)
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
