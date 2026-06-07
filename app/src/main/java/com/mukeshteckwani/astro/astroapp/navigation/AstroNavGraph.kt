package com.mukeshteckwani.astro.astroapp.navigation

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import com.mukeshteckwani.astro.astroapp.R
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel
import com.mukeshteckwani.astro.astroapp.ui.components.SortDropdownMenu
import com.mukeshteckwani.astro.astroapp.ui.screens.ChannelsListScreen
import com.mukeshteckwani.astro.astroapp.ui.screens.TvGuideScreen
import com.mukeshteckwani.astro.astroapp.viewmodel.ChannelsListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstroNavGraph(
    channelsViewModel: ChannelsListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val backStack = remember { mutableStateListOf<Any>(ChannelsKey) }
    var tvGuideSortOrder by remember { mutableIntStateOf(0) }
    var showSortMenu by remember { mutableStateOf(false) }
    var pendingChannel by remember { mutableStateOf<ChannelsListModel.Channel?>(null) }

    val channelList by channelsViewModel.channelList.collectAsStateWithLifecycle()
    val sortOrder by channelsViewModel.sortOrder.collectAsStateWithLifecycle()

    val currentKey = backStack.lastOrNull()
    val isTvGuide = currentKey is TvGuideKey

    val signInLauncher = rememberLauncherForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            pendingChannel?.let { channel ->
                channel.setChecked(true)
                channelsViewModel.writeOrRemoveChannelsData(channel)
                pendingChannel = null
            }
        }
    }

    fun launchSignIn() {
        val providers = listOf(AuthUI.IdpConfig.GoogleBuilder().build())
        signInLauncher.launch(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isTvGuide) stringResource(R.string.tv_guide)
                        else stringResource(R.string.app_name)
                    )
                },
                navigationIcon = {
                    if (isTvGuide) {
                        IconButton(onClick = { backStack.removeLastOrNull() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                actions = {
                    if (!isTvGuide) {
                        IconButton(
                            onClick = {
                                val channels = channelList?.channels.orEmpty()
                                if (channels.isNotEmpty()) {
                                    backStack.add(
                                        TvGuideKey(channels.mapNotNull { it.channelId })
                                    )
                                }
                            }
                        ) {
                            Icon(Icons.Default.List, contentDescription = "TV Guide")
                        }
                    }
                    IconButton(
                        onClick = {
                            if (FirebaseAuth.getInstance().currentUser == null) {
                                launchSignIn()
                            } else {
                                AuthUI.getInstance()
                                    .signOut(context)
                                    .addOnCompleteListener { }
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = if (FirebaseAuth.getInstance().currentUser != null) {
                                "Logout"
                            } else {
                                "Login"
                            }
                        )
                    }
                    IconButton(onClick = { showSortMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Sort")
                    }
                    SortDropdownMenu(
                        expanded = showSortMenu,
                        onDismiss = { showSortMenu = false },
                        onSortSelected = { order ->
                            if (isTvGuide) {
                                tvGuideSortOrder = order
                            } else {
                                channelsViewModel.setSortOrder(order)
                            }
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            modifier = Modifier.padding(paddingValues),
            entryProvider = entryProvider {
                entry<ChannelsKey> {
                    ChannelsListScreen(
                        sortOrder = sortOrder,
                        onSignInRequired = { channel ->
                            pendingChannel = channel
                            launchSignIn()
                        },
                        viewModel = channelsViewModel
                    )
                }
                entry<TvGuideKey> { key ->
                    TvGuideScreen(
                        channelIds = key.channelIds,
                        sortOrder = tvGuideSortOrder
                    )
                }
            }
        )
    }
}
