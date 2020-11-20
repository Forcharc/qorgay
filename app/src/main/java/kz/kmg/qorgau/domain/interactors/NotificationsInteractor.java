package kz.kmg.qorgau.domain.interactors;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import kz.kmg.qorgau.data.model.notifications.NotificationModel;
import kz.kmg.qorgau.data.network.api.NotificationsApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.utils.error_handling.ErrorHelper;
import retrofit2.Call;

public class NotificationsInteractor {


    public static @NotNull
    Flowable<Resource<List<NotificationModel>>> getNotifications(NotificationsApi api, String phoneUid) {
        return api.getNotifications(phoneUid)
                .onErrorReturn(ErrorHelper::getErrorResponse)
                .map(response -> {
                    if (response.isSuccessful()) {
                        return Resource.success(response.body());
                    } else {
                        return ErrorHelper.<List<NotificationModel>>getQorgayApiError(response);
                    }
                })
                .subscribeOn(Schedulers.io());

    }


    public static Call<Object> setNotificationIsRead(NotificationsApi api, int notificationId) {
        return api.setNotificationIsRead(String.valueOf(notificationId));
    }

}
