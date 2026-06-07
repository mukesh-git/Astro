package com.mukeshteckwani.astro.astroapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mukeshteckwani.astro.astroapp.model.TvGuideModel;
import com.mukeshteckwani.astro.astroapp.repository.TvGuideRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * Created by mukeshteckwani on 21/12/17.
 */

@HiltViewModel
public class TvGuideViewModel extends ViewModel {

    private final TvGuideRepository repository;

    @Inject
    public TvGuideViewModel(TvGuideRepository repository) {
        this.repository = repository;
    }

    public LiveData<TvGuideModel> getTvGuide(String periodStart, String periodEnd, String channelIds) {
        return repository.getTvGuide(periodStart, periodEnd, channelIds);
    }

    public String getLastFetchedTime() {
        return repository.getLastFetchedTime();
    }

    public void setLastFetchedTime(String lastFetchedTime) {
        repository.setLastFetchedTime(lastFetchedTime);
    }

    public String getChannelIdsString() {
        return repository.getChannelIdsString();
    }

    public String getStartTime() {
        return repository.getStartTime();
    }

    public String getEndTime() {
        return repository.getEndTime();
    }

    public void incrementCurrentPage() {
        repository.incrementCurrentPage();
    }

    public void setChannelIds(ArrayList<Integer> channelIds) {
        repository.setChannelIds(channelIds);
    }

    public int getPageNumber() {
        return repository.getPageNumber();
    }

    public int getItemsCount() {
        return repository.getItemsCount();
    }

    public int getSortOrder() {
        return repository.getSortOrder();
    }

    public void setSortOrder(int sortOrder) {
        repository.setSortOrder(sortOrder);
    }
}
