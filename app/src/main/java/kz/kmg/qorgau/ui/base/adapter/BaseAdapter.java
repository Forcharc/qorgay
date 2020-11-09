package kz.kmg.qorgau.ui.base.adapter;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public AsyncListDiffer<T> mDiffer;

    public BaseAdapter() {
        mDiffer = new AsyncListDiffer<>(this, getDiffCallback());
    }

    @NonNull
    public abstract DiffUtil.ItemCallback<T> getDiffCallback();

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    public void submitList(List<T> data) {
        mDiffer.submitList(data);
    }

    public T getItem(int position) {
        return mDiffer.getCurrentList().get(position);
    }

    @NonNull
    @Override
    public abstract BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.setData(getItem(position));
    }

}


