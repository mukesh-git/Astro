package com.mukeshteckwani.astro.astroapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel
import com.mukeshteckwani.astro.astroapp.repository.ChannelsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ChannelsListViewModel @Inject constructor(
    private val repository: ChannelsRepository
) : ViewModel() {

    val channelList: StateFlow<ChannelsListModel?> = repository.channelList
        .asFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val favouriteChannels: StateFlow<List<ChannelsListModel.Channel>> = repository.favouriteChannels
        .asFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val sortOrder: StateFlow<Int> = repository.sortOrder
        .asFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    fun writeOrRemoveChannelsData(channel: ChannelsListModel.Channel) {
        repository.writeOrRemoveChannelsData(channel)
    }

    fun setSortOrder(sortOrder: Int) {
        repository.setSortOrder(sortOrder)
    }
}
