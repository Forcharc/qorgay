package kz.kmg.qorgau.ui.questionnaire;

import androidx.annotation.NonNull;
import androidx.paging.rxjava2.RxPagingSource;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import kz.kmg.qorgau.data.model.observations.driving.DrivingObservationModel;
import kz.kmg.qorgau.data.model.paging.PagingModel;
import kz.kmg.qorgau.data.model.questionnaires.QuestionnaireModel;
import kz.kmg.qorgau.data.network.api.DrivingObservationsApi;
import kz.kmg.qorgau.data.network.api.QuestionnaireApi;

public class QuestionnairesPagingSource extends RxPagingSource<Integer, QuestionnaireModel> {
    QuestionnaireApi api;
    String cookie;

    Integer key;
    int page;

    public QuestionnairesPagingSource(QuestionnaireApi api, String cookie) {
        this.api = api;
        this.cookie = cookie;
    }

    @NotNull
    @Override
    public Single<LoadResult<Integer, QuestionnaireModel>> loadSingle(@NotNull LoadParams<Integer> loadParams) {
        key = loadParams.getKey();
        page = 1;
        if (key != null) page = key;

        return api.getQuestionnaires(cookie, String.valueOf(page)).subscribeOn(Schedulers.io())
                .map(this::toLoadResult)
                .onErrorReturn(LoadResult.Error::new);
    }

    private LoadResult<Integer, QuestionnaireModel> toLoadResult(
            @NonNull PagingModel<QuestionnaireModel> response) {
        return new LoadResult.Page<>(
                response.getList(),
                (page <= 1) ? null : page - 1,
                (response.getList().size() == 0) ? null : page + 1,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }
}
