package kz.kmg.qorgau.ui.home;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.DiffUtil;

import com.bumptech.glide.RequestManager;

import butterknife.BindView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.home.NewsTopModel;
import kz.kmg.qorgau.domain.services.glide.GlideModule;
import kz.kmg.qorgau.ui.base.adapter.BaseAdapter;
import kz.kmg.qorgau.ui.base.adapter.BaseViewHolder;
import kz.kmg.qorgau.ui.base.adapter.OnItemClickListener;

public class NewsTopAdapter extends BaseAdapter<NewsTopModel> {

    OnItemClickListener onItemClickListener;
    RequestManager glide;

    public NewsTopAdapter(RequestManager glide) {
        super();
        this.glide = glide;
    }

    @NonNull
    @Override
    public DiffUtil.ItemCallback<NewsTopModel> getDiffCallback() {
        return new DiffUtil.ItemCallback<NewsTopModel>() {
            @Override
            public boolean areItemsTheSame(@NonNull NewsTopModel oldItem, @NonNull NewsTopModel newItem) {
                return oldItem.getName().equals(newItem.getName()) && oldItem.getUrl().equals(newItem.getUrl());
            }

            @Override
            public boolean areContentsTheSame(@NonNull NewsTopModel oldItem, @NonNull NewsTopModel newItem) {
                return oldItem.getName().equals(newItem.getName()) && oldItem.getUrl().equals(newItem.getUrl());
            }
        };
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promo_card, parent, false);
        return new PromoCardViewHolder(root);
    }

    class PromoCardViewHolder extends BaseViewHolder<NewsTopModel> {
        @BindView(R.id.iv_background)
        AppCompatImageView backgroundImageView;

        @BindView(R.id.tv_title)
        TextView titleTextView;

        public PromoCardViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(NewsTopModel item) {

            glide.load(item.getUrl()).into(backgroundImageView);

            ImageViewCompat.setImageTintList(
                    backgroundImageView,
                    ColorStateList.valueOf(Color.parseColor("#9912207B"))
            );

            titleTextView.setText(item.getName());
        }
    }
}
