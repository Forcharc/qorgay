package kz.kmg.qorgau.ui.observations.work;

import androidx.annotation.NonNull;
import androidx.paging.rxjava2.RxPagingSource;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import kz.kmg.qorgau.data.model.observations.work.WorkObservationModel;
import kz.kmg.qorgau.data.model.paging.PagingModel;
import kz.kmg.qorgau.data.network.api.WorkObservationsApi;

public class WorkObservationsPagingSource extends RxPagingSource<Integer, WorkObservationModel> {
    WorkObservationsApi api;
    String cookie;

    Integer key;
    int page;

    public WorkObservationsPagingSource(WorkObservationsApi api, String cookie) {
        this.api = api;
        this.cookie = cookie;
    }


    @NotNull
    @Override
    public Single<LoadResult<Integer, WorkObservationModel>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
        key = loadParams.getKey();
        page = 1;
        if (key != null) page = key;

        return api.getWorkObservations(cookie, page).subscribeOn(Schedulers.io())
                .map(this::toLoadResult)
                .onErrorReturn(LoadResult.Error::new);

    }

    private LoadResult<Integer, WorkObservationModel> toLoadResult(
            @NonNull PagingModel<WorkObservationModel> response) {
        return new LoadResult.Page<>(
                response.getList(),
                (page <= 1) ? null : page - 1,
                (response.getList().size() == 0) ? null : page + 1,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }
}
