package kz.kmg.qorgau.ui.observations.driving;

import androidx.annotation.NonNull;
import androidx.paging.rxjava2.RxPagingSource;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import kz.kmg.qorgau.data.model.observations.driving.DrivingObservationModel;
import kz.kmg.qorgau.data.model.paging.PagingModel;
import kz.kmg.qorgau.data.network.api.DrivingObservationsApi;

public class DrivingObservationsPagingSource extends RxPagingSource<Integer, DrivingObservationModel> {
    DrivingObservationsApi api;
    String cookie;

    Integer key;
    int page;

    public DrivingObservationsPagingSource(DrivingObservationsApi api, String cookie) {
        this.api = api;
        this.cookie = cookie;
    }


    @NotNull
    @Override
    public Single<LoadResult<Integer, DrivingObservationModel>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
        key = loadParams.getKey();
        page = 1;
        if (key != null) page = key;

        return api.getDrivingObservations(cookie, page).subscribeOn(Schedulers.io())
                .map(this::toLoadResult)
                .onErrorReturn(LoadResult.Error::new);
    }

    private LoadResult<Integer, DrivingObservationModel> toLoadResult(
            @NonNull PagingModel<DrivingObservationModel> response) {
        return new LoadResult.Page<>(
                response.getList(),
                (page <= 1) ? null : page - 1,
                (response.getList().size() == 0) ? null : page + 1,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }
}
