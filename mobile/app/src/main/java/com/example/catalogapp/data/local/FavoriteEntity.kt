package com.example.catalogapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.catalogapp.domain.model.MediaItem

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val type: String,
    val synopsis: String,
    val imageUrl: String?,
    val year: Int,
    val genre: String
)

fun FavoriteEntity.toDomainModel(): MediaItem {
    return MediaItem(
        id = id,
        title = title,
        type = type,
        synopsis = synopsis,
        imageUrl = imageUrl,
        year = year,
        genre = genre
    )
}

fun MediaItem.toEntity(): FavoriteEntity {
    return FavoriteEntity(
        id = id,
        title = title,
        type = type,
        synopsis = synopsis,
        imageUrl = imageUrl,
        year = year,
        genre = genre
    )
}
