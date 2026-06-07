package com.mukeshteckwani.astro.astroapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel
import com.mukeshteckwani.astro.astroapp.ui.components.ChannelItem
import com.mukeshteckwani.astro.astroapp.ui.components.SectionHeader
import com.mukeshteckwani.astro.astroapp.viewmodel.ChannelsListViewModel

@Composable
fun ChannelsListScreen(
    viewModel: ChannelsListViewModel,
    onToggleFav: (ChannelsListModel.Channel) -> Unit
) {
    val channels by viewModel.channelList.observeAsState()
    val favChannels by viewModel.favouriteChannels.observeAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // Favourites section
            if (!favChannels.isNullOrEmpty()) {
                item {
                    SectionHeader(title = "Favourites")
                }
                items(favChannels!!, key = { it.channelId }) { channel ->
                    ChannelItem(
                        channel = channel,
                        onToggleFav = onToggleFav
                    )
                }
            }
            
            // All channels section
            item {
                SectionHeader(title = "All Channels")
            }
            
            if (channels != null) {
                items(channels!!.channels, key = { it.channelId }) { channel ->
                    ChannelItem(
                        channel = channel,
                        onToggleFav = onToggleFav
                    )
                }
            }
        }
    }
}
