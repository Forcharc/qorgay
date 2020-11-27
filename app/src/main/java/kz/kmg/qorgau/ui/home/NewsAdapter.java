package kz.kmg.qorgau.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.RequestManager;

import butterknife.BindView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.home.NewsModel;
import kz.kmg.qorgau.ui.base.adapter.BaseAdapter;
import kz.kmg.qorgau.ui.base.adapter.BaseViewHolder;

public class NewsAdapter extends BaseAdapter<NewsModel> {

    RequestManager glide;

    public NewsAdapter(RequestManager glide) {
        super();
        this.glide = glide;
    }

    @NonNull
    @Override
    public DiffUtil.ItemCallback<NewsModel> getDiffCallback() {
        return new DiffUtil.ItemCallback<NewsModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull NewsModel oldItem, @NonNull NewsModel newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull NewsModel oldItem, @NonNull NewsModel newItem) {
                return oldItem.getPublishDate().equals(newItem.getPublishDate()) && oldItem.getName().equals(newItem.getName()) && oldItem.getText().equals(newItem.getText()) && oldItem.getUrl().equals(newItem.getUrl()) && oldItem.getInsertDate().equals(newItem.getInsertDate());
            }
        };
    }

    @NonNull
    @Override
    public BaseViewHolder<NewsModel> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(root);
    }

    class NewsViewHolder extends BaseViewHolder<NewsModel> {

        @BindView(R.id.iv_news_image)
        AppCompatImageView newsImageView;

        @BindView(R.id.tv_news_content)
        TextView contentTextView;

        @BindView(R.id.tv_title)
        TextView titleTextView;

        @BindView(R.id.tv_time_date)
        TextView timeDateTextView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(NewsModel item) {
            glide.load(item.getUrl()).into(newsImageView);

            contentTextView.setText(item.getText());

            titleTextView.setText(item.getName());

            timeDateTextView.setText(item.getPublishDate());
        }
    }
}
