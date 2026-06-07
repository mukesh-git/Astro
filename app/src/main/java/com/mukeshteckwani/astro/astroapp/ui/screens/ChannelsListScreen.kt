package com.mukeshteckwani.astro.astroapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.firebase.auth.FirebaseAuth
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel
import com.mukeshteckwani.astro.astroapp.ui.components.ChannelItem
import com.mukeshteckwani.astro.astroapp.ui.components.SectionHeader
import com.mukeshteckwani.astro.astroapp.utils.sortChannels
import com.mukeshteckwani.astro.astroapp.viewmodel.ChannelsListViewModel

@Composable
fun ChannelsListScreen(
    sortOrder: Int,
    onSignInRequired: (ChannelsListModel.Channel) -> Unit,
    viewModel: ChannelsListViewModel
) {
    val channelList by viewModel.channelList.collectAsStateWithLifecycle()
    val favouriteChannels by viewModel.favouriteChannels.collectAsStateWithLifecycle()

    val isLoading = channelList == null
    val allChannels = remember(channelList, sortOrder) {
        sortChannels(channelList?.channels.orEmpty(), sortOrder)
    }
    val favourites = remember(favouriteChannels, sortOrder) {
        sortChannels(favouriteChannels, sortOrder)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            if (favourites.isNotEmpty()) {
                item { SectionHeader(title = "Favourites") }
                items(favourites, key = { it.channelId ?: 0 }) { channel ->
                    ChannelItem(
                        channel = channel,
                        onToggleFav = { toggled ->
                            if (FirebaseAuth.getInstance().currentUser != null) {
                                toggled.setChecked(!toggled.isChecked)
                                viewModel.writeOrRemoveChannelsData(toggled)
                            }
                        }
                    )
                }
            }

            item { SectionHeader(title = "All Channels") }
            items(allChannels, key = { it.channelId ?: 0 }) { channel ->
                ChannelItem(
                    channel = channel,
                    onToggleFav = { toggled ->
                        if (FirebaseAuth.getInstance().currentUser != null) {
                            toggled.setChecked(!toggled.isChecked)
                            viewModel.writeOrRemoveChannelsData(toggled)
                        } else {
                            onSignInRequired(toggled)
                        }
                    }
                )
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
