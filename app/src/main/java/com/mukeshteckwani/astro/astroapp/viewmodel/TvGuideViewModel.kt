package com.mukeshteckwani.astro.astroapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukeshteckwani.astro.astroapp.model.TvGuideModel
import com.mukeshteckwani.astro.astroapp.repository.TvGuideRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TvGuideViewModel @Inject constructor(
    private val repository: TvGuideRepository
) : ViewModel() {

    private val _events = MutableStateFlow<List<TvGuideModel.Getevent>>(emptyList())
    val events: StateFlow<List<TvGuideModel.Getevent>> = _events.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _hasMore = MutableStateFlow(true)
    val hasMore: StateFlow<Boolean> = _hasMore.asStateFlow()

    private var sortOrder: Int = 0

    fun initialize(channelIds: List<Int>) {
        repository.setChannelIds(ArrayList(channelIds))
        _events.value = emptyList()
        _hasMore.value = true
        loadNextPage()
    }

    fun loadNextPage() {
        if (_isLoading.value || !_hasMore.value) return
        viewModelScope.launch {
            _isLoading.value = true
            val result = withContext(Dispatchers.IO) {
                try {
                    repository.fetchTvGuide(
                        repository.startTime,
                        repository.endTime,
                        repository.channelIdsString
                    )
                } catch (_: Exception) {
                    null
                }
            }
            if (result?.getevent != null) {
                _events.value = _events.value + result.getevent
                repository.incrementCurrentPage()
            } else {
                _hasMore.value = false
            }
            _isLoading.value = false
        }
    }

    fun setSortOrder(order: Int) {
        sortOrder = order
        repository.setSortOrder(order)
    }

    fun getSortOrder(): Int = sortOrder
}
