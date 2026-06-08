package com.mukeshteckwani.astro.astroapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel
import com.mukeshteckwani.astro.astroapp.ui.components.ChannelItem
import com.mukeshteckwani.astro.astroapp.ui.components.SectionHeader
import com.mukeshteckwani.astro.astroapp.utils.sortChannels
import com.mukeshteckwani.astro.astroapp.viewmodel.ChannelsListViewModel

@Composable
fun ChannelsListScreen(
    sortOrder: Int,
    viewModel: ChannelsListViewModel
) {
    val channelPagingItems = viewModel.channelPagingData.collectAsLazyPagingItems()
    val favouriteChannels by viewModel.favouriteChannels.collectAsStateWithLifecycle()

    val favourites = remember(favouriteChannels, sortOrder) {
        sortChannels(favouriteChannels, sortOrder)
    }
    val favouriteIds = remember(favouriteChannels) {
        favouriteChannels.mapNotNull { it.channelId }.toSet()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            if (favourites.isNotEmpty()) {
                item { SectionHeader(title = "Favourites") }
                items(favourites, key = { "fav-${it.channelId ?: "unknown"}" }) { channel ->
                    ChannelItem(
                        channel = channel,
                        onToggleFav = { toggled ->
                            viewModel.writeOrRemoveChannelsData(
                                toggled.copy(isChecked = !toggled.isChecked)
                            )
                        }
                    )
                }
            }

            item { SectionHeader(title = "All Channels") }
            items(
                count = channelPagingItems.itemCount,
                key = { index -> "all-${channelPagingItems[index]?.channelId ?: "index-$index"}" }
            ) { index ->
                channelPagingItems[index]?.let { channel ->
                    val displayChannel = channel.copy(
                        isChecked = favouriteIds.contains(channel.channelId)
                    )
                    ChannelItem(
                        channel = displayChannel,
                        onToggleFav = { toggled ->
                            viewModel.writeOrRemoveChannelsData(
                                toggled.copy(isChecked = !toggled.isChecked)
                            )
                        }
                    )
                }
            }

            when (val state = channelPagingItems.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }
                is LoadState.Error -> {
                    item {
                        Text(
                            text = "Error loading more: ${state.error.message}",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
                else -> {}
            }
        }

        if (channelPagingItems.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
