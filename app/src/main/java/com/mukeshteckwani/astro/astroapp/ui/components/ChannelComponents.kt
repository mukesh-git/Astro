package com.mukeshteckwani.astro.astroapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mukeshteckwani.astro.astroapp.R
import com.mukeshteckwani.astro.astroapp.model.ChannelsListModel
import com.mukeshteckwani.astro.astroapp.ui.theme.Black
import com.mukeshteckwani.astro.astroapp.ui.theme.HeadingBackground

@Composable
fun ChannelItem(
    channel: ChannelsListModel.Channel,
    onToggleFav: (ChannelsListModel.Channel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${channel.channelId} ",
                    fontSize = 18.sp,
                    color = Black,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = channel.channelTitle ?: "",
                    fontSize = 14.sp,
                    color = Black.copy(alpha = 0.87f)
                )
            }
            
            Icon(
                painter = painterResource(
                    id = if (channel.isChecked()) R.drawable.ic_favorite_black_24dp else R.drawable.ic_favorite_border_black_24dp
                ),
                contentDescription = if (channel.isChecked()) "Remove from favorites" else "Add to favorites",
                tint = if (channel.isChecked()) Color(0xFFFFD700) else Color.Gray,
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onToggleFav(channel) }
            )
        }
    }
}

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(HeadingBackground)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Black,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
        )
    }
}
