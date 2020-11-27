package kz.kmg.qorgau.ui.observations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.observations.BaseObservationModel;
import kz.kmg.qorgau.data.model.observations.work.WorkObservationModel;

public class ObservationListAdapter<T extends BaseObservationModel> extends PagingDataAdapter<T, ObservationListAdapter<T>.ObservationViewHolder> {

    DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
    ObservationListListener listener;

    public ObservationListAdapter(@NotNull DiffUtil.ItemCallback<T> diffCallback, ObservationListListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ObservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_observation, parent, false);
        return new ObservationViewHolder(root);
    }




    @Override
    public void onBindViewHolder(@NonNull ObservationViewHolder holder, int position) {
        BaseObservationModel currentItem = getItem(position);
        if (currentItem != null) {
            holder.bind(currentItem);
        }
    }

    public class ObservationViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_obs_id)
        public TextView obsIdTextView;

        @BindView(R.id.tv_org_name)
        public TextView orgNameTextView;

        @BindView(R.id.tv_date)
        public TextView dateTextView;

        @BindView(R.id.iv_delete)
        ImageView deleteImageView;

        @BindView(R.id.iv_edit)
        ImageView editImageView;

        @BindView(R.id.cl_foreground)
        ConstraintLayout foregroundLayout;

        public ObservationViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(BaseObservationModel currentItem) {
            String date = dateFormat.format(new Date(currentItem.getDate()));

            foregroundLayout.setOnClickListener(v -> {
                listener.navigateToObservationId(currentItem.getId());
            });
            dateTextView.setText(date);

            orgNameTextView.setText(currentItem.getOrgName());

            obsIdTextView.setText(currentItem.getId().toString());

            editImageView.setOnClickListener(v -> {
                listener.navigateToObservationId(currentItem.getId());
            });

            deleteImageView.setOnClickListener(v -> {
                listener.deleteObservationById(currentItem.getId(), getBindingAdapterPosition());
            });
        }
    }

    public ConcatAdapter getWithLoadStateHeaderAndFooter(
            LoadStateAdapter header,
            LoadStateAdapter footer
    ) {
        addLoadStateListener(loadStates -> {
            if (getItemCount() == 0) {
                header.setLoadState((!loadStates.getRefresh().getClass().equals(LoadState.NotLoading.class)) ? loadStates.getRefresh() : loadStates.getAppend());
                footer.setLoadState(loadStates.getPrepend());
            } else {
                header.setLoadState(loadStates.getPrepend());
                footer.setLoadState((!loadStates.getRefresh().getClass().equals(LoadState.NotLoading.class)) ? loadStates.getRefresh() : loadStates.getAppend());
            }
            return null;
        });

        return new ConcatAdapter(header, this, footer);
    }

    public interface ObservationListListener {
        void navigateToObservationId(int workId);

        void deleteObservationById(Integer workId, int position);
    }

}