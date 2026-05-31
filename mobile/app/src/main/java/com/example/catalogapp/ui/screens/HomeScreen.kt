package com.example.catalogapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.catalogapp.R
import com.example.catalogapp.domain.model.MediaItem
import com.example.catalogapp.domain.model.Resource
import com.example.catalogapp.ui.viewmodels.CatalogViewModel

@Composable
fun HomeScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: CatalogViewModel = viewModel(factory = CatalogViewModel.Factory)
) {
    val state by viewModel.catalogState.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (state) {
            is Resource.Loading -> {
                CircularProgressIndicator()
            }
            is Resource.Error -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = state.message ?: "Unknown Error", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.fetchCatalog() }) {
                        Text(stringResource(id = R.string.btn_retry))
                    }
                }
            }
            is Resource.Success -> {
                val items = state.data ?: emptyList()
                if (items.isEmpty()) {
                    Text(stringResource(id = R.string.error_no_catalog_items))
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(items) { mediaItem ->
                            MediaItemCard(mediaItem = mediaItem, onClick = { onNavigateToDetail(mediaItem.id) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MediaItemCard(mediaItem: MediaItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column {
            AsyncImage(
                model = mediaItem.imageUrl ?: "https://via.placeholder.com/150",
                contentDescription = mediaItem.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = mediaItem.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${mediaItem.year} • ${mediaItem.genre}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
