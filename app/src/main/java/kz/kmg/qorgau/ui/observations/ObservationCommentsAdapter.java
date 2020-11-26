package kz.kmg.qorgau.ui.observations;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import butterknife.BindView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.observations.AnswerCategoriesItem;
import kz.kmg.qorgau.data.model.observations.ChildrenItem;
import kz.kmg.qorgau.ui.base.adapter.BaseAdapter;
import kz.kmg.qorgau.ui.base.adapter.BaseViewHolder;

public class ObservationCommentsAdapter extends BaseAdapter<ChildrenItem> {

    private static final int VIEW_TYPE_TITLE = 1;
    private static final int VIEW_TYPE_COMMENT = 2;

    AnswerCategoriesItem answerCategoriesItem;

    public ObservationCommentsAdapter(AnswerCategoriesItem item) {
        this.answerCategoriesItem = item;
    }

    @NonNull
    @Override
    public DiffUtil.ItemCallback<ChildrenItem> getDiffCallback() {
        return new DiffUtil.ItemCallback<ChildrenItem>() {
            @Override
            public boolean areItemsTheSame(@NonNull ChildrenItem oldItem, @NonNull ChildrenItem newItem) {
                return oldItem.getCode().equals(newItem.getCode());
            }

            @Override
            public boolean areContentsTheSame(@NonNull ChildrenItem oldItem, @NonNull ChildrenItem newItem) {
                return oldItem.getName().equals(newItem.getName());
            }
        };
    }

    @Override
    public int getItemCount() {
        return answerCategoriesItem.getChildren().size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_TITLE;
        } else {
            return VIEW_TYPE_COMMENT;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (position == 0) {
            holder.setData(answerCategoriesItem);
        } else {
            holder.setData(answerCategoriesItem.getChildren().get(position - 1));
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_COMMENT) {
            root = inflater.inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(root);
        } else {
            root = inflater.inflate(R.layout.item_title, parent, false);
            return new TitleViewHolder(root);
        }
    }

    class CommentViewHolder extends BaseViewHolder<ChildrenItem> {

        @BindView(R.id.tv_title)
        TextView titleTextView;

        @BindView(R.id.b_positive)
        Button positiveButton;

        @BindView(R.id.b_negative)
        Button negativeButton;

        @BindView(R.id.b_neutral)
        Button neutralButton;

        @BindView(R.id.tv_comment)
        TextView commentTextView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(ChildrenItem item) {
            titleTextView.setText(item.getName());

            positiveButton.setAlpha(1.0f);
            negativeButton.setAlpha(1.0f);
            neutralButton.setAlpha(1.0f);

            switch (item.getCategory()) {
                case 0:
                    break;
                case 1:
                    negativeButton.setAlpha(0.5f);
                    neutralButton.setAlpha(0.5f);
                    break;
                case 2:
                    positiveButton.setAlpha(0.5f);
                    neutralButton.setAlpha(0.5f);
                    break;
                case 3:
                    negativeButton.setAlpha(0.5f);
                    positiveButton.setAlpha(0.5f);
                    break;
            }

            positiveButton.setOnClickListener(v -> {
                item.setCategory(1);
                v.setAlpha(1.0f);
                negativeButton.setAlpha(0.5f);
                neutralButton.setAlpha(0.5f);
            });

            negativeButton.setOnClickListener(v -> {
                item.setCategory(2);
                v.setAlpha(1.0f);
                positiveButton.setAlpha(0.5f);
                neutralButton.setAlpha(0.5f);
            });

            neutralButton.setOnClickListener(v -> {
                item.setCategory(3);
                v.setAlpha(1.0f);
                positiveButton.setAlpha(0.5f);
                negativeButton.setAlpha(0.5f);
            });

            commentTextView.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_edit_text, null, false);
                EditText editText = dialogView.findViewById(R.id.et);
                editText.setText(item.getComment());
                builder.setView(dialogView);
                builder.setPositiveButton(R.string.save, (dialog, which) -> {
                    item.setComment(editText.getText().toString());
                    dialog.dismiss();
                });
                builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.create().show();
            });
        }
    }

    class TitleViewHolder extends BaseViewHolder<AnswerCategoriesItem> {

        @BindView(R.id.tv_title)
        TextView titleTextView;

        public TitleViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(AnswerCategoriesItem item) {
            titleTextView.setText(item.getName());
        }
    }


}
