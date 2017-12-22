package com.mukeshteckwani.astro.astroapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mukeshteckwani.astro.astroapp.R;
import com.mukeshteckwani.astro.astroapp.databinding.ItemProgressBarBinding;
import com.mukeshteckwani.astro.astroapp.databinding.ItemTvGuideBinding;
import com.mukeshteckwani.astro.astroapp.model.TvGuideModel;

import java.util.List;

/**
 * Created by mukeshteckwani on 21/12/17.
 */

public class TvGuideAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<TvGuideModel.Getevent> listEvents;
    private Context mContext;
    private boolean hideLoader;

    public TvGuideAdapter(List<TvGuideModel.Getevent> getevent) {
        this.listEvents = getevent;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        if (viewType == R.layout.item_tv_guide) {
            ItemTvGuideBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), viewType, parent, false);
            return new TvGuideAdapter.TvGuideViewHolder(binding);
        } else if (viewType == R.layout.item_progress_bar) {
            ItemProgressBarBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), viewType, parent, false);
            return new TvGuideAdapter.TvProgressHolder(binding);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < listEvents.size()) {
            return R.layout.item_tv_guide;
        } else {
            return R.layout.item_progress_bar;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TvGuideViewHolder) {
            ((TvGuideViewHolder)holder).setItems(position);
        }
        else if (holder instanceof TvProgressHolder) {
            if (hideLoader) {
                ((TvProgressHolder) holder).binding.pb.setVisibility(View.GONE);
                ((TvProgressHolder) holder).binding.executePendingBindings();
            }
        }
    }

    @Override
    public int getItemCount() {
        return listEvents.size() + 1;
    }

    public void appendItems(List<TvGuideModel.Getevent> newTvEvents) {
        int oldSize = listEvents.size();
        listEvents.addAll(newTvEvents);
        notifyItemRangeInserted(oldSize, newTvEvents.size());
    }

    public void hideLoader() {
        notifyItemChanged(getItemCount()-1);
        hideLoader = true;
    }

    class TvGuideViewHolder extends RecyclerView.ViewHolder {
        private final ItemTvGuideBinding binding;

        TvGuideViewHolder(ItemTvGuideBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setItems(int position) {
            if (listEvents.size() > position)
                binding.setEventModel(listEvents.get(position));
        }
    }

    class TvProgressHolder extends RecyclerView.ViewHolder {
        private final ItemProgressBarBinding binding;
        TvProgressHolder(ItemProgressBarBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
