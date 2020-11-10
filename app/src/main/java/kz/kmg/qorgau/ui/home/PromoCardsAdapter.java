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

import butterknife.BindView;
import kz.kmg.qorgau.R;
import kz.kmg.qorgau.data.model.home.PromoModel;
import kz.kmg.qorgau.ui.base.adapter.BaseAdapter;
import kz.kmg.qorgau.ui.base.adapter.BaseViewHolder;
import kz.kmg.qorgau.ui.base.adapter.OnItemClickListener;

public class PromoCardsAdapter extends BaseAdapter<PromoModel> {

    OnItemClickListener onItemClickListener;

    public PromoCardsAdapter() {
        super();
    }

    @NonNull
    @Override
    public DiffUtil.ItemCallback<PromoModel> getDiffCallback() {
        return null;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promo_card, parent, false);
        return new PromoCardViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        ((PromoCardViewHolder) holder).setData(getItem(position));
    }

    class PromoCardViewHolder extends BaseViewHolder<PromoModel> {
        @BindView(R.id.iv_background)
        AppCompatImageView backgroundImageView;

        @BindView(R.id.tv_title)
        TextView titleTextView;

        public PromoCardViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void setData(PromoModel item) {
            itemView.setOnClickListener(view -> {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(item);
                        }
                    }
            );

            backgroundImageView.setImageDrawable(item.getImage());
            ImageViewCompat.setImageTintList(
                    backgroundImageView,
                    ColorStateList.valueOf(item.getBackgroundTintColor())
            );

            titleTextView.setText(item.getText());
        }
    }
}
