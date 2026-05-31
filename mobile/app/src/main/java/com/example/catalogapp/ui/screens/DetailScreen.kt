package com.example.catalogapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.catalogapp.domain.model.Resource
import com.example.catalogapp.ui.viewmodels.CatalogViewModel
import com.example.catalogapp.ui.viewmodels.FavoritesViewModel
import android.app.Application
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.catalogapp.R

@Composable
fun DetailScreen(
    id: Int,
    viewModel: CatalogViewModel = viewModel(factory = CatalogViewModel.Factory),
    favoritesViewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.provideFactory(LocalContext.current.applicationContext as Application))
) {
    val state by viewModel.catalogState.collectAsState()
    val mediaItem = state.data?.find { it.id == id }

    val isFavorite by favoritesViewModel.isFavorite(id).collectAsState()

    if (mediaItem == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            Text(stringResource(id = R.string.error_item_not_found))
        }
        return
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { favoritesViewModel.toggleFavorite(mediaItem, isFavorite) }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(id = R.string.content_desc_favorite),
                    tint = if (isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = mediaItem.imageUrl ?: "https://via.placeholder.com/400x300",
                contentDescription = mediaItem.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = mediaItem.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Badge(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                        Text(text = mediaItem.year.toString(), modifier = Modifier.padding(4.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Badge(containerColor = MaterialTheme.colorScheme.secondaryContainer) {
                        Text(text = mediaItem.genre, modifier = Modifier.padding(4.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Badge(containerColor = MaterialTheme.colorScheme.tertiaryContainer) {
                        Text(text = mediaItem.type.uppercase(), modifier = Modifier.padding(4.dp))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.label_synopsis),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = mediaItem.synopsis,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
