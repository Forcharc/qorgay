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
import kz.kmg.qorgau.data.model.observations.driving.DrivingObservationFormModel;
import kz.kmg.qorgau.data.model.observations.driving.DrivingObservationModel;
import kz.kmg.qorgau.data.model.observations.work.PlaceItemModel;
import kz.kmg.qorgau.data.model.observations.work.PlaceModel;
import kz.kmg.qorgau.data.network.api.DrivingObservationsApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.ui.observations.driving.DrivingObservationsPagingSource;
import kz.kmg.qorgau.utils.error_handling.ErrorHelper;

public class DrivingObservationsInteractor {

    public static @NotNull
    Flowable<PagingData<DrivingObservationModel>> getObservations(DrivingObservationsApi api, String cookie) {
        return PagingRx.getFlowable(
                new Pager<>(
                        new PagingConfig(10),
                        () -> new DrivingObservationsPagingSource(api, cookie)
                ));
    }


    public static @NotNull
    Flowable<Resource<DrivingObservationFormModel>> getForm(DrivingObservationsApi api, String cookie) {
        return api.getForm(cookie)
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<DrivingObservationFormModel>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());

    }

    public static @NotNull
    Flowable<Resource<DrivingObservationFormModel>> getDrivingById(DrivingObservationsApi api, String cookie, int id) {
        return api.getDrivingById(cookie, String.valueOf(id))
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<DrivingObservationFormModel>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());
    }

    public static @NotNull
    Flowable<Resource<IsSuccessResponse>> removeDrivingById(DrivingObservationsApi api, String cookie, int id) {
        return api.removeDrivingById(cookie, String.valueOf(id))
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
    Flowable<Resource<IsSuccessResponse>> saveDriving(DrivingObservationsApi api, String cookie, DrivingObservationFormModel work) {
        return api.saveDriving(cookie, work)
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
