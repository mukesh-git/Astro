package com.mukeshteckwani.astro.astroapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mukeshteckwani.astro.astroapp.R;
import com.mukeshteckwani.astro.astroapp.databinding.ItemTvGuideBinding;
import com.mukeshteckwani.astro.astroapp.model.TvGuideModel;

import java.util.List;

/**
 * Created by mukeshteckwani on 21/12/17.
 */

public class TvGuideAdapter extends RecyclerView.Adapter<TvGuideAdapter.TvGuideViewHolder> {

    private final List<TvGuideModel.Getevent> listEvents;
    private Context mContext;

    public TvGuideAdapter(List<TvGuideModel.Getevent> getevent) {
        this.listEvents = getevent;
    }

    @Override
    public TvGuideAdapter.TvGuideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        ItemTvGuideBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_tv_guide, parent, false);
        return new TvGuideAdapter.TvGuideViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < listEvents.size()) {
            return R.layout.item_tv_guide;
        }
        return R.layout.item_progress_bar;
    }

    @Override
    public void onBindViewHolder(TvGuideAdapter.TvGuideViewHolder holder, int position) {
        holder.setItems(position);
    }

    @Override
    public int getItemCount() {
        return listEvents.size() + 1;
    }

     class TvGuideViewHolder extends RecyclerView.ViewHolder {
        private final ItemTvGuideBinding binding;

        TvGuideViewHolder(ItemTvGuideBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void setItems(int position) {
            binding.setEventModel(listEvents.get(position));
        }
    }
}
