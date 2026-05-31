package com.example.catalogapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.catalogapp.data.local.DatabaseProvider
import com.example.catalogapp.data.repository.FavoritesRepositoryImpl
import com.example.catalogapp.domain.model.MediaItem
import com.example.catalogapp.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: FavoritesRepository
) : ViewModel() {

    val favorites: StateFlow<List<MediaItem>> = repository.getFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun isFavorite(id: Int): StateFlow<Boolean> {
        return repository.isFavorite(id)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )
    }

    fun toggleFavorite(item: MediaItem, isCurrentlyFavorite: Boolean) {
        viewModelScope.launch {
            if (isCurrentlyFavorite) {
                repository.removeFavorite(item.id)
            } else {
                repository.addFavorite(item)
            }
        }
    }

    companion object {
        fun provideFactory(application: Application): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = DatabaseProvider.getDatabase(application)
                val repository = FavoritesRepositoryImpl(database.favoriteDao())
                return FavoritesViewModel(repository) as T
            }
        }
    }
}
