package kz.kmg.qorgau.ui.questionnaire;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import kz.kmg.qorgau.QorgauApp;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.local.LocalStorage;
import kz.kmg.qorgau.data.model.observations.driving.DrivingObservationModel;
import kz.kmg.qorgau.data.model.questionnaires.QuestionnaireModel;
import kz.kmg.qorgau.data.network.api.QuestionnaireApi;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;
import kz.kmg.qorgau.utils.rv.QorgayLoadStateAdapter;

public class QuestionnaireListFragment extends BaseFragment implements QuestionnaireListAdapter.QuestionnaireListListener, QorgayLoadStateAdapter.LoadStateListener {
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.b_retry)
    Button retryButton;

    QuestionnaireViewModel viewModel;

    QuestionnaireApi questionnaireApi;
    LocalStorage prefStorage;

    QuestionnaireListAdapter adapter = new QuestionnaireListAdapter(this);

    @Override
    public int getContentView() {
        return R.layout.fragment_questionnaire_list;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QorgauApp qorgauApp = (QorgauApp) getActivity().getApplication();
        questionnaireApi = qorgauApp.questionnaireApi;
        prefStorage = qorgauApp.prefStorage;
        viewModel = new ViewModelProvider(this).get(QuestionnaireViewModel.class);
        viewModel.init(questionnaireApi, prefStorage.getCookie());

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter.getWithLoadStateHeaderAndFooter(new QorgayLoadStateAdapter(this), new QorgayLoadStateAdapter(this)));

        initPagingData();
    }



    public void initPagingData() {
        LiveData<PagingData<QuestionnaireModel>> pagingData = viewModel.getQuestionnaires();
        pagingData.removeObservers(getViewLifecycleOwner());
        pagingData.observe(getViewLifecycleOwner(), workObservationModelPagingData -> adapter.submitData(getViewLifecycleOwner().getLifecycle(), workObservationModelPagingData));
    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
    }

    public void showRetryButton(View.OnClickListener onRetryButtonClick) {
        retryButton.setOnClickListener(onRetryButtonClick);
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
    }

    public void hideStatusViews() {
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    @Override
    public void navigateToQuestionnaire(QuestionnaireModel questionnaire) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(QuestionnaireDescriptionFragment.PARAM_QUESTIONNAIRE, questionnaire);
        NavHostFragment.findNavController(this).navigate(R.id.action_questionnaireListFragment_to_questionnaireDescriptionFragment, bundle);
    }

    @Override
    public void onRetry() {
        adapter.retry();
    }
}
