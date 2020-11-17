package kz.kmg.qorgau.ui.create.pages;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import kz.kmg.qorgau.QorgauApp;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.ui.base.fragment.BaseActivityFragment;
import kz.kmg.qorgau.ui.create.CreateFragment;
import kz.kmg.qorgau.ui.create.CreateQorgayViewModel;
import kz.kmg.qorgau.ui.main.MainActivity;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static android.view.inputmethod.EditorInfo.IME_ACTION_NEXT;
import static android.view.inputmethod.EditorInfo.IME_ACTION_SEND;
import static android.view.inputmethod.EditorInfo.IME_NULL;


public abstract class BaseQorgayPageFragment extends BaseActivityFragment<MainActivity> {

    private static final String TAG = "BaseQorgayPageFragment";

    protected CreateQorgayViewModel viewModel;

    private OnNextPageRequestListener onNextPageRequestListener;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.b_retry)
    Button retryButton;

    @BindView(R.id.tv_step_number)
    TextView pageNumberTextView;

    @BindView(R.id.tv_quiz_title)
    TextView pageTitleTextView;

    final TextView.OnEditorActionListener onEditorActionNextPage = (v, actionId, event) -> {
        Log.d(TAG, "EditorAction: actionId - " + actionId + ", event - " + event);
        if (actionId == IME_ACTION_SEND || actionId == IME_ACTION_NEXT || actionId == IME_ACTION_DONE) {
            if (onNextPageRequestListener != null) {
                onNextPageRequestListener.onNextPage();
            }
        }
        return true;
    };

    int pageNumber;
    int pageTitleStringId;

    public BaseQorgayPageFragment(int pageNumber, int pageTitleStringId) {
        this.pageNumber = pageNumber;
        this.pageTitleStringId = pageTitleStringId;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onNextPageRequestListener = (OnNextPageRequestListener) getParentFragment();

        viewModel = new ViewModelProvider(getActivity()).get(CreateQorgayViewModel.class);
        viewModel.setQorgayApi(((QorgauApp) getActivity().getApplication()).qorgayApi);

        pageNumberTextView.setText(String.valueOf(pageNumber));
        pageTitleTextView.setText(pageTitleStringId);
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

    public interface OnNextPageRequestListener {
        void onNextPage();
    }

}
