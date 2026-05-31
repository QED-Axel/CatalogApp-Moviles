package com.example.catalogapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.catalogapp.ui.viewmodels.FavoritesViewModel

@Composable
fun FavoritesScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: FavoritesViewModel
) {
    val favorites by viewModel.favorites.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (favorites.isEmpty()) {
            Text("No tienes favoritos guardados aún.")
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(favorites) { mediaItem ->
                    MediaItemCard(mediaItem = mediaItem, onClick = { onNavigateToDetail(mediaItem.id) })
                }
            }
        }
    }
}
