package kz.kmg.qorgau.utils.rv;

import android.service.voice.VoiceInteractionService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kmg.qorgau.R;

public class QorgayLoadStateAdapter extends LoadStateAdapter<QorgayLoadStateAdapter.LoadStateViewHolder> {

    LoadStateListener listener;

    public QorgayLoadStateAdapter(LoadStateListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NotNull LoadStateViewHolder loadStateViewHolder, @NotNull LoadState loadState) {
        loadStateViewHolder.bind(loadState);
    }

    @NotNull
    @Override
    public LoadStateViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, @NotNull LoadState loadState) {
        View root = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_load_state, viewGroup, false);
        return new LoadStateViewHolder(root);
    }

    class LoadStateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.b_retry)
        Button retryButton;

        @BindView(R.id.tv_error)
        TextView errorTextView;

        @BindView(R.id.progress)
        ProgressBar progressBar;

        public LoadStateViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            retryButton.setOnClickListener(v -> {
                listener.onRetry();
            });
        }

        void bind(LoadState loadState)  {
            Class<? extends LoadState> loadStateClass = loadState.getClass();
            if (LoadState.NotLoading.class.equals(loadStateClass)) {
                progressBar.setVisibility(View.INVISIBLE);
                errorTextView.setVisibility(View.INVISIBLE);
                retryButton.setVisibility(View.INVISIBLE);
            } else if (LoadState.Loading.class.equals(loadStateClass)) {
                progressBar.setVisibility(View.VISIBLE);
                errorTextView.setVisibility(View.INVISIBLE);
                retryButton.setVisibility(View.INVISIBLE);
            } else if (LoadState.Error.class.equals(loadStateClass)) {
                progressBar.setVisibility(View.INVISIBLE);
                errorTextView.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.VISIBLE);
            }

        }
    }

    public interface LoadStateListener {
        void onRetry();
    }
}

