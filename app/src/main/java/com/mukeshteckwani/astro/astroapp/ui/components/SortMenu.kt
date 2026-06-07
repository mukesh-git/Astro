package com.mukeshteckwani.astro.astroapp.ui.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.mukeshteckwani.astro.astroapp.utils.Constants

@Composable
fun SortDropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onSortSelected: (Int) -> Unit
) {
    DropdownMenu(expanded = expanded, onDismissRequest = onDismiss) {
        DropdownMenuItem(
            text = { Text("Name Ascending") },
            onClick = {
                onSortSelected(Constants.SORT_NAME_ASC)
                onDismiss()
            }
        )
        DropdownMenuItem(
            text = { Text("Name Descending") },
            onClick = {
                onSortSelected(Constants.SORT_NAME_DESC)
                onDismiss()
            }
        )
        DropdownMenuItem(
            text = { Text("Channel No. Ascending") },
            onClick = {
                onSortSelected(Constants.SORT_ID_ASC)
                onDismiss()
            }
        )
        DropdownMenuItem(
            text = { Text("Channel No. Descending") },
            onClick = {
                onSortSelected(Constants.SORT_ID_DESC)
                onDismiss()
            }
        )
    }
}
