package kz.kmg.qorgau.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import kz.kmg.qorgau.data.model.home.NewsModel;
import kz.kmg.qorgau.data.model.home.NewsTopModel;
import kz.kmg.qorgau.data.network.api.NewsApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.domain.interactors.NewsInteractor;

public class HomeViewModel extends ViewModel {

    NewsApi newsApi;

    MediatorLiveData<Resource<List<NewsModel>>> newsLiveData = new MediatorLiveData<>();
    MediatorLiveData<Resource<List<NewsTopModel>>> newsTopLiveData = new MediatorLiveData<>();

    public void init(NewsApi newsApi) {
        this.newsApi = newsApi;
    }

    public void loadNews() {
        if (newsLiveData.getValue() == null || newsLiveData.getValue().status.equals(Resource.Status.ERROR)) {
            newsLiveData.setValue(Resource.loading());
            final LiveData<Resource<List<NewsModel>>> response = LiveDataReactiveStreams.fromPublisher(NewsInteractor.getNews(newsApi));

            newsLiveData.addSource(response, res -> {
                newsLiveData.setValue(res);
                newsLiveData.removeSource(response);
            });
        }
    }

    public LiveData<Resource<List<NewsModel>>> getNewsLiveData() {
        return newsLiveData;
    }

    public void loadNewsTop() {
        if (newsTopLiveData.getValue() == null || newsTopLiveData.getValue().status.equals(Resource.Status.ERROR)) {
            newsTopLiveData.setValue(Resource.loading());
            final LiveData<Resource<List<NewsTopModel>>> response = LiveDataReactiveStreams.fromPublisher(NewsInteractor.getNewsTop(newsApi));

            newsTopLiveData.addSource(response, res -> {
                newsTopLiveData.setValue(res);
                newsTopLiveData.removeSource(response);
            });
        }
    }

    public LiveData<Resource<List<NewsTopModel>>> getNewsTopLiveData() {
        return newsTopLiveData;
    }
}
