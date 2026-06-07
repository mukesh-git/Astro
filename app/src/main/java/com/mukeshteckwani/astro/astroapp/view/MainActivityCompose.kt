package com.mukeshteckwani.astro.astroapp.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.mukeshteckwani.astro.astroapp.R
import com.mukeshteckwani.astro.astroapp.ui.theme.AstroAppTheme
import com.mukeshteckwani.astro.astroapp.ui.screens.ChannelsListScreen
import com.mukeshteckwani.astro.astroapp.utils.BundleKeys
import com.mukeshteckwani.astro.astroapp.viewmodel.ChannelsListViewModel

class MainActivityCompose : ComponentActivity() {
    
    private lateinit var viewModel: ChannelsListViewModel
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        viewModel = androidx.lifecycle.ViewModelProvider(this).get(ChannelsListViewModel::class.java)
        
        setContent {
            AstroAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainAppContent(viewModel = viewModel)
                }
            }
        }
    }
    
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainAppContent(viewModel: ChannelsListViewModel) {
        var showMenu by remember { mutableStateOf(false) }
        
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Astro App") },
                    actions = {
                        IconButton(onClick = { /* Handle TV Guide */ }) {
                            Icon(
                                imageVector = Icons.Filled.List,
                                contentDescription = "TV Guide"
                            )
                        }
                        IconButton(onClick = { /* Handle Login/Logout */ }) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Login/Logout"
                            )
                        }
                        IconButton(onClick = { showMenu = true }) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                contentDescription = "More"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                ChannelsListScreen(
                    viewModel = viewModel,
                    onToggleFav = { channel ->
                        channel.setChecked(!channel.isChecked())
                        viewModel.writeOrRemoveChannelsData(channel)
                    }
                )
            }
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.tv_guide -> {
                val intent = Intent(this, TvGuideActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
