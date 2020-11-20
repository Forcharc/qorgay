package kz.kmg.qorgau.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kz.kmg.qorgau.data.local.LocalStorage;
import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.user.ProfileModel;
import kz.kmg.qorgau.data.network.api.ProfileApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.domain.interactors.ProfileInteractor;
import kz.kmg.qorgau.domain.interactors.QorgayInteractor;

public class ProfileViewModel extends ViewModel {
    private LocalStorage localStorage;
    private ProfileApi profileApi;

    private final MutableLiveData<String> cookie = new MutableLiveData<>();
    private final MediatorLiveData<Resource<IsSuccessResponse>> isLoginSuccessful = new MediatorLiveData<>();
    private final MediatorLiveData<Resource<ProfileModel>> profile = new MediatorLiveData<>();

    void init(LocalStorage localStorage, ProfileApi profileApi) {
        this.localStorage = localStorage;
        this.profileApi = profileApi;

        cookie.postValue(this.localStorage.getCookie());
    }

    public LiveData<String> getCookieLiveData() {
        return cookie;
    }

    public LiveData<Resource<IsSuccessResponse>> login(String login, String password) {
        isLoginSuccessful.setValue(Resource.loading());

        final LiveData<Resource<IsSuccessResponse>> response = LiveDataReactiveStreams.fromPublisher(ProfileInteractor.login(profileApi, login, password));

        isLoginSuccessful.addSource(response, res -> {
            if (res.data != null && res.data.getIsSuccess() != null && res.data.getIsSuccess() && res.cookie != null) {
                cookie.postValue(res.cookie);
                localStorage.setCookie(res.cookie);
            } else {
                localStorage.setCookie(null);
                cookie.postValue(null);
            }
            isLoginSuccessful.setValue(res);
            isLoginSuccessful.removeSource(response);
        });

        return isLoginSuccessful;
    }

    public LiveData<Resource<ProfileModel>> getProfile(String cookie) {
        if (profile.getValue() == null || profile.getValue().status == Resource.Status.ERROR) {

            profile.setValue(Resource.loading());
            final LiveData<Resource<ProfileModel>> response = LiveDataReactiveStreams.fromPublisher(ProfileInteractor.getProfile(profileApi, cookie));

            profile.addSource(response, res -> {
                profile.setValue(res);
                profile.removeSource(response);
            });
        }
        return profile;
    }

    public void exit() {
        localStorage.setCookie(null);
        cookie.setValue(null);
    }
}
