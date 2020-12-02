package kz.kmg.qorgau.ui.questionnaire;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagingData;

import kz.kmg.qorgau.data.model.create.IsSuccessResponse;
import kz.kmg.qorgau.data.model.questionnaires.QuestionnaireFormModel;
import kz.kmg.qorgau.data.model.questionnaires.QuestionnaireModel;
import kz.kmg.qorgau.data.network.api.QuestionnaireApi;
import kz.kmg.qorgau.data.network.base.Resource;
import kz.kmg.qorgau.domain.interactors.QuestionnaireInteractor;

public class QuestionnaireViewModel extends ViewModel {
    QuestionnaireApi questionnaireApi;
    String cookie;

    private MediatorLiveData<Resource<QuestionnaireFormModel>> questionnaireFormModelLiveData = new MediatorLiveData<>();
    private MediatorLiveData<Resource<IsSuccessResponse>> questionnaireSavingResponseLiveData = new MediatorLiveData<>();

    public void init(QuestionnaireApi questionnaireApi, String cookie) {
        this.questionnaireApi = questionnaireApi;
        this.cookie = cookie;
    }

    public LiveData<PagingData<QuestionnaireModel>> getQuestionnaires() {
        return LiveDataReactiveStreams.fromPublisher(QuestionnaireInteractor.getQuestionnaires(questionnaireApi, cookie));
    }

    public void loadQuestionnaire(int id) {
        LiveData<Resource<QuestionnaireFormModel>> source = LiveDataReactiveStreams.fromPublisher(QuestionnaireInteractor.getQuestionnaireById(questionnaireApi, id));
        questionnaireFormModelLiveData.addSource(source, questionnaireFormModelResource -> {
            questionnaireFormModelLiveData.postValue(questionnaireFormModelResource);
            questionnaireFormModelLiveData.removeSource(source);
        });
    }

    public LiveData<Resource<QuestionnaireFormModel>> getQuestionnaireLiveData() {
        return questionnaireFormModelLiveData;
    }

    public void saveQuestionnaire(QuestionnaireFormModel questionnaireFormModel) {

        LiveData<Resource<IsSuccessResponse>> source = LiveDataReactiveStreams.fromPublisher(QuestionnaireInteractor.saveQuestionnaireAnswer(questionnaireApi, cookie, questionnaireFormModel));
        questionnaireSavingResponseLiveData.addSource(source, resource -> {
            questionnaireSavingResponseLiveData.postValue(resource);
            questionnaireSavingResponseLiveData.removeSource(source);
        });
    }

    public LiveData<Resource<IsSuccessResponse>> getSaveQuestionnaireResponseLiveData() {
        return questionnaireSavingResponseLiveData;
    }

}
