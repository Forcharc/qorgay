package kz.kmg.qorgau.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import butterknife.BindView;
import kz.kmg.qorgau.QorgauApp;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.home.NewsTopModel;
import kz.kmg.qorgau.data.network.api.NewsApi;
import kz.kmg.qorgau.ui.base.adapter.OnItemClickListener;
import kz.kmg.qorgau.ui.base.fragment.BaseFragment;

public class HomeFragment extends BaseFragment implements OnItemClickListener {

    private static final String TAG = "HomeFragment";

    @BindView(R.id.rv_promo_cards)
    RecyclerView rvNewsTop;

    @BindView(R.id.rv_news)
    RecyclerView rvNews;

    @BindView(R.id.b_create)
    ImageButton createQorgayButton;

    @BindView(R.id.ib_work)
    ImageButton workButton;

    @BindView(R.id.ib_driving)
    ImageButton drivingButton;

    @BindView(R.id.ib_questionnaire)
    ImageButton questionnaireButton;

    @BindView(R.id.tv_news)
    TextView newsTextView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.b_retry)
    Button retryButton;

    NavController navController;
    private HomeViewModel viewModel;

    private NewsTopAdapter newsTopAdapter;
    private NewsAdapter newsAdapter;

    private final View.OnClickListener onRetryNewsLoading = v -> {
        viewModel.loadNews();
    };

    private final View.OnClickListener onRetryNewsTopLoading = v -> {
        viewModel.loadNewsTop();
    };

    public HomeFragment() {
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        QorgauApp qorgauApp = (QorgauApp) getActivity().getApplication();
        NewsApi newsApi = qorgauApp.newsApi;
        RequestManager glide = qorgauApp.requestManager;
        viewModel.init(newsApi);

        newsTopAdapter = new NewsTopAdapter(glide);
        newsAdapter = new NewsAdapter(glide);

        navController = NavHostFragment.findNavController(this);

        showNewsTop();
        showNews();

        viewModel.loadNewsTop();

        initListeners();

        initObservers();
    }

    private void initObservers() {
        viewModel.getNewsTopLiveData().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {
                case ERROR:
                    showRetryButton(onRetryNewsTopLoading);
                    onToast(listResource.apiError.getMessage());
                    break;
                case SUCCESS:
                    hideStatusViews();
                    newsTopAdapter.submitList(listResource.data);
                    viewModel.loadNews();
                    break;
                case LOADING:
                    showProgress();
                    break;
            }
        });

        viewModel.getNewsLiveData().observe(getViewLifecycleOwner(), listResource -> {
            switch (listResource.status) {
                case ERROR:
                    showRetryButton(onRetryNewsLoading);
                    onToast(listResource.apiError.getMessage());
                    break;
                case SUCCESS:
                    hideStatusViews();
                    newsAdapter.submitList(listResource.data);
                    break;
                case LOADING:
                    showProgress();
                    break;
            }
        });
    }

    private void initListeners() {
        createQorgayButton.setOnClickListener(v -> {
            navController.navigate(R.id.navigation_create);
        });

        workButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_workObservationFragment);
        });

        drivingButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_drivingObservationFragment);
        });

        questionnaireButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_navigation_home_to_questionnaireListFragment);
        });
    }



    private void showNews() {
        rvNews.setNestedScrollingEnabled(false);
        rvNews.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvNews.setHasFixedSize(false);
        rvNews.setAdapter(newsAdapter);
    }

    private void showNewsTop() {
        newsTopAdapter.onItemClickListener = this;


        rvNewsTop.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
                lp.width = (int) ((double) getWidth() * 0.8);
                lp.height = (int) ((double) lp.width / 1.3);
                return true;
            }
        });

        rvNewsTop.setAdapter(newsTopAdapter);
        rvNewsTop.setHasFixedSize(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        navController = null;
    }

    @Override
    public void onItemClick(Object object) {
        onToast(((NewsTopModel) object).toString());
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
}