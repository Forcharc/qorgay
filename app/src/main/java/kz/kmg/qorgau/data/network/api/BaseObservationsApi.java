package kz.kmg.qorgau.data.network.api;

import kz.kmg.qorgau.data.model.observations.BaseObservationModel;
import kz.kmg.qorgau.data.model.paging.PagingModel;

public interface BaseObservationsApi {


    <T extends BaseObservationModel> PagingModel<T> getObservations(int page);

/*
    @NonNull
    @PUT("api/notification/UpdatePushNotification")
    Call<Object> setNotificationIsRead(@Query("id") String notificationId);
*/


}
