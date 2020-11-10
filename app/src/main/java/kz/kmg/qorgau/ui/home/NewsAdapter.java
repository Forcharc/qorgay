package kz.kmg.qorgau.ui.home;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.DiffUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.home.NewsModel;
import kz.kmg.qorgau.data.model.home.PromoModel;
import kz.kmg.qorgau.ui.base.adapter.BaseAdapter;
import kz.kmg.qorgau.ui.base.adapter.BaseViewHolder;

public class NewsAdapter extends BaseAdapter<NewsModel> {

    public NewsAdapter() {
        super();
    }

    @NonNull
    @Override
    public DiffUtil.ItemCallback<NewsModel> getDiffCallback() {
        return null;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ((NewsViewHolder) holder).setData(getItem(position));
    }

    class NewsViewHolder extends BaseViewHolder<NewsModel> {

        @BindView(R.id.iv_photo)
        AppCompatImageView userPhotoImageView;

        @BindView(R.id.iv_news_image)
        AppCompatImageView newsImageView;

        @BindView(R.id.tv_news_content)
        TextView contentTextView;

        @BindView(R.id.tv_name_surname)
        TextView nameSurnameTextView;

        @BindView(R.id.tv_time_date)
        TextView timeDateTextView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(NewsModel item) {
            // TODO use glide to load photo
            //userPhotoImageView

            // TODO use glide to load image
            //newsImageView

            contentTextView.setText(item.getContent());

            nameSurnameTextView.setText(item.getUser().getName() + " " + item.getUser().getSurname());

            Date postTimeDate = new Date(item.getDateTimeInMillis());
            String timeString = SimpleDateFormat.getTimeInstance(SimpleDateFormat.SHORT, Locale.getDefault()).format(postTimeDate);
            String dateString = SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT, Locale.getDefault()).format(postTimeDate);
            timeDateTextView.setText(timeString + " " + dateString);
        }
    }
}
