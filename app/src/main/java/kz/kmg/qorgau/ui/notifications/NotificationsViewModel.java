package kz.kmg.qorgau.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.datatransport.runtime.scheduling.Scheduler;

import java.util.List;

import io.reactivex.schedulers.Schedulers;
import kz.kmg.qorgau.data.local.LocalStorage;
import kz.kmg.qorgau.data.model.notifications.NotificationModel;
import kz.kmg.qorgau.data.model.user.ProfileModel;
import kz.kmg.qorgau.data.network.api.NotificationsApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.domain.interactors.NotificationsInteractor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsViewModel extends ViewModel {
    private LocalStorage localStorage;
    private NotificationsApi notificationsApi;

    private final MediatorLiveData<Resource<List<NotificationModel>>> notifications = new MediatorLiveData<>();

    void init(LocalStorage localStorage, NotificationsApi notificationsApi) {
        this.notificationsApi = notificationsApi;
        this.localStorage = localStorage;
    }


    public void loadNotifications() {

        notifications.setValue(Resource.loading());
        final LiveData<Resource<List<NotificationModel>>> response = LiveDataReactiveStreams.fromPublisher(NotificationsInteractor.getNotifications(notificationsApi, localStorage.getNotificationToken()));

        notifications.addSource(response, res -> {
            notifications.setValue(res);
            notifications.removeSource(response);
        });
    }

    public void setNotificationIsRead(int notificationId) {
        NotificationsInteractor.setNotificationIsRead(notificationsApi, notificationId).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                loadNotifications();
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                loadNotifications();
            }
        });
    }

    public LiveData<Resource<List<NotificationModel>>> getNotificationsLiveData() {
        return notifications;
    }
}
