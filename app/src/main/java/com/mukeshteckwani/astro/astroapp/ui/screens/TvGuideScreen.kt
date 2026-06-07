package com.mukeshteckwani.astro.astroapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mukeshteckwani.astro.astroapp.ui.components.TvGuideEventCard
import com.mukeshteckwani.astro.astroapp.ui.components.TvGuideLoadingItem
import com.mukeshteckwani.astro.astroapp.utils.sortTvEvents
import com.mukeshteckwani.astro.astroapp.viewmodel.TvGuideViewModel

@Composable
fun TvGuideScreen(
    channelIds: List<Int>,
    sortOrder: Int,
    viewModel: TvGuideViewModel = hiltViewModel()
) {
    val events by viewModel.events.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val hasMore by viewModel.hasMore.collectAsStateWithLifecycle()

    val sortedEvents = remember(events, sortOrder) {
        sortTvEvents(events, sortOrder)
    }

    LaunchedEffect(channelIds) {
        viewModel.initialize(channelIds)
    }

    LaunchedEffect(sortOrder) {
        viewModel.setSortOrder(sortOrder)
    }

    val gridState = rememberLazyGridState()
    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = gridState.layoutInfo
            val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisible >= layoutInfo.totalItemsCount - 3
        }
    }

    LaunchedEffect(shouldLoadMore, hasMore, isLoading) {
        if (shouldLoadMore && hasMore && !isLoading) {
            viewModel.loadNextPage()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = gridState,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp)
        ) {
            items(sortedEvents, key = { "${it.eventID}-${it.channelId}" }) { event ->
                TvGuideEventCard(event = event)
            }
            if (hasMore) {
                item(span = { GridItemSpan(2) }) {
                    TvGuideLoadingItem()
                }
            }
        }

        if (isLoading && sortedEvents.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
