package com.mukeshteckwani.astro.astroapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mukeshteckwani.astro.astroapp.R;
import com.mukeshteckwani.astro.astroapp.databinding.ItemChannelBinding;
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel;

import java.util.List;

/**
 * Created by mukeshteckwani on 17/12/17.
 */

public class ChannelsAdapter extends RecyclerView.Adapter<ChannelsAdapter.ChannelViewHolder> {
    private final List<ChannelsListModel.Channel> channels;
    private final ToggleFav listener;
    private Context mContext;

    public interface ToggleFav {
        void onToggleFav(ChannelsListModel.Channel channel);
    }

    public ChannelsAdapter(List<ChannelsListModel.Channel> channels, ToggleFav listener) {
        this.channels = channels;
        this.listener = listener;
    }

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        ItemChannelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_channel, parent, false);
        return new ChannelViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ChannelViewHolder holder, int position) {
        holder.bindChannelList();
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public class ChannelViewHolder extends RecyclerView.ViewHolder {

        private final ItemChannelBinding binding;

        public ChannelViewHolder(ItemChannelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindChannelList() {
            binding.setChannel(channels.get(getLayoutPosition()));
            binding.setCallback(listener);
        }
    }

}
