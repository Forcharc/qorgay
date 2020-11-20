package kz.kmg.qorgau.data.network.api;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Flowable;
import kz.kmg.qorgau.data.model.notifications.NotificationModel;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface NotificationsApi {

    @NonNull
    @GET("api/notification/listByPhoneId")
    Flowable<Response<List<NotificationModel>>> getNotifications(@Query("id") String phoneUid);

    @NonNull
    @PUT("api/notification/UpdatePushNotification")
    Call<Object> setNotificationIsRead(@Query("id") String notificationId);


}
