package kz.kmg.qorgau.ui.questionnaire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.paging.LoadState;
import androidx.paging.LoadStateAdapter;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.questionnaires.QuestionnaireModel;

public class QuestionnaireListAdapter extends PagingDataAdapter<QuestionnaireModel, QuestionnaireListAdapter.QuestionnaireViewHolder> {

    private final QuestionnaireListListener listener;

    public QuestionnaireListAdapter(QuestionnaireListListener listener) {
        super(new DiffUtil.ItemCallback<QuestionnaireModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull QuestionnaireModel oldItem, @NonNull QuestionnaireModel newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull QuestionnaireModel oldItem, @NonNull QuestionnaireModel newItem) {
                return oldItem.getType().equals(newItem.getType()) && oldItem.getDescription().equals(newItem.getDescription()) && oldItem.getName().equals(newItem.getName());
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public QuestionnaireViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_questionnaire, parent, false);
        return new QuestionnaireViewHolder(root);
    }


    @Override
    public void onBindViewHolder(@NonNull QuestionnaireViewHolder holder, int position) {
        QuestionnaireModel currentItem = getItem(position);
        if (currentItem != null) {
            holder.bind(currentItem);
        }
    }

    public class QuestionnaireViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title)
        TextView titleTextView;

        @BindView(R.id.tv_date)
        TextView dateTextView;

        @BindView(R.id.card_type)
        CardView typeCardView;

        @BindView(R.id.tv_type)
        TextView typeTextView;

        Context context;

        public QuestionnaireViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        public void bind(QuestionnaireModel currentItem) {
            itemView.setOnClickListener(v -> {
                listener.navigateToQuestionnaire(currentItem);
            });

            titleTextView.setText(currentItem.getName());
            dateTextView.setText(currentItem.getInsertDate());

            switch (currentItem.getType()) {
                case "Special":
                    typeCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_quest_special));
                    typeTextView.setText(R.string.special);
                    break;
                case "Guest":
                    typeCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_quest_guest));
                    typeTextView.setText(R.string.guest);
                    break;
                case "Default":
                    typeCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_quest_default));
                    typeTextView.setText(R.string.default1);
                    break;
                case "Anonymous":
                    typeCardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.color_quest_anonymous));
                    typeTextView.setText(R.string.anonymous);
                    break;
            }
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

    public interface QuestionnaireListListener {
        void navigateToQuestionnaire(QuestionnaireModel questionnaire);
    }

}