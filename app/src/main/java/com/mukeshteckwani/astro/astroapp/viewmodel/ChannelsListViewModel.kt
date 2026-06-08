package com.mukeshteckwani.astro.astroapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel
import com.mukeshteckwani.astro.astroapp.repository.ChannelsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChannelsListViewModel @Inject constructor(
    private val repository: ChannelsRepository
) : ViewModel() {

    val favouriteChannels: StateFlow<List<ChannelsListModel.Channel>> = repository.favouriteChannels
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val sortOrder: StateFlow<Int> = repository.sortOrder
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)

    val allChannels: StateFlow<List<ChannelsListModel.Channel>> = repository.getAllChannels()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val channelPagingData: Flow<PagingData<ChannelsListModel.Channel>> = sortOrder
        .flatMapLatest { order ->
            repository.getChannelList(order)
        }
        .cachedIn(viewModelScope)

    fun writeOrRemoveChannelsData(channel: ChannelsListModel.Channel) {
        viewModelScope.launch {
            repository.writeOrRemoveChannelsData(channel)
        }
    }

    fun setSortOrder(sortOrder: Int) {
        viewModelScope.launch {
            repository.setSortOrder(sortOrder)
        }
    }
}
