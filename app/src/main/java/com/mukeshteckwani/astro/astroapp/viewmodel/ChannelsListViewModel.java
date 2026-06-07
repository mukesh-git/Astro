package com.mukeshteckwani.astro.astroapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel;
import com.mukeshteckwani.astro.astroapp.repository.ChannelsRepository;

import java.util.List;

import dagger.hilt.android.lifecycle.HiltViewModel;

import javax.inject.Inject;

/**
 * Created by mukeshteckwani on 17/12/17.
 */

@HiltViewModel
public class ChannelsListViewModel extends ViewModel {
    
    private final ChannelsRepository repository;

    @Inject
    public ChannelsListViewModel(ChannelsRepository repository) {
        this.repository = repository;
    }

    public LiveData<ChannelsListModel> getChannelList() {
        return repository.getChannelList();
    }

    public LiveData<List<ChannelsListModel.Channel>> getFavouriteChannels() {
        return repository.getFavouriteChannels();
    }

    public void writeOrRemoveChannelsData(ChannelsListModel.Channel channel) {
        repository.writeOrRemoveChannelsData(channel);
    }

    public LiveData<Integer> getSortOrder() {
        return repository.getSortOrder();
    }

    public void setSortOrder(int sortOrder) {
        repository.setSortOrder(sortOrder);
    }
}
