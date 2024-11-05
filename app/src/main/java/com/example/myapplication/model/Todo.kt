package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Todo(
    val userId: Int?,
    val id: Int?,
    val title: String?,
    val completed: Boolean?
)
@Serializable
data class Photo(
    val albumId: Int?,
    val id: Int?,
    val title: String?,
    val url: String?,
    val thumbnailUrl: String?
)
