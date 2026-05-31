package com.example.catalogapp.ui.screens

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.catalogapp.R
import com.example.catalogapp.domain.model.MediaItem
import com.example.catalogapp.domain.model.Resource
import com.example.catalogapp.ui.viewmodels.CatalogViewModel

@Composable
fun HomeScreen(
    onNavigateToDetail: (Int) -> Unit
) {
    val viewModel: CatalogViewModel = viewModel(factory = CatalogViewModel.Factory)

    val catalogState by viewModel.catalogState.collectAsState()
    val trendingState by viewModel.trendingState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Barra de búsqueda
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.searchMovies(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Buscar películas...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
            singleLine = true,
            shape = MaterialTheme.shapes.medium
        )

        // Contenido Principal
        Box(modifier = Modifier.fillMaxSize()) {
            if (catalogState is Resource.Loading || trendingState is Resource.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (catalogState is Resource.Error && trendingState is Resource.Error) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(text = catalogState.message ?: "Unknown Error", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { 
                        viewModel.fetchCatalog()
                        viewModel.fetchTrending() 
                    }) {
                        Text(stringResource(id = R.string.btn_retry))
                    }
                }
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    // Mostrar Tendencias solo si no hay búsqueda activa
                    if (searchQuery.isBlank() && trendingState is Resource.Success) {
                        val trendingItems = trendingState.data ?: emptyList()
                        if (trendingItems.isNotEmpty()) {
                            Text(
                                text = "Tendencias de Hoy",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                            )
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(trendingItems) { item ->
                                    TrendingItemCard(mediaItem = item, onClick = { onNavigateToDetail(item.id) })
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    // Catálogo General o Resultados de Búsqueda
                    val titleText = if (searchQuery.isNotBlank()) "Resultados de Búsqueda" else "Catálogo Completo"
                    Text(
                        text = titleText,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    val catalogItems = catalogState.data ?: emptyList()
                    if (catalogItems.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(stringResource(id = R.string.error_no_catalog_items))
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(8.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(catalogItems) { item ->
                                MediaItemCard(mediaItem = item, onClick = { onNavigateToDetail(item.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrendingItemCard(mediaItem: MediaItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(210.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = mediaItem.imageUrl,
                contentDescription = mediaItem.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Overlay gradient for text readability
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.3f)
            ) {}
            Text(
                text = mediaItem.title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.surface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            )
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
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = mediaItem.imageUrl,
                contentDescription = mediaItem.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = mediaItem.title,
                    style = MaterialTheme.typography.titleMedium,
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
