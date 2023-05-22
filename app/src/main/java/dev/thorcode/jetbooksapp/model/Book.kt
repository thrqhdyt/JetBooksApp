package dev.thorcode.jetbooksapp.model

data class Book(
    val id: Long,
    val image: Int,
    val title: String,
    val author: String,
    val description: String,
    val isFavorite: Boolean = false
)
