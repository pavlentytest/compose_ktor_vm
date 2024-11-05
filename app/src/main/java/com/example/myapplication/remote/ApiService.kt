package com.example.myapplication.remote

import com.example.myapplication.model.ApiResult
import com.example.myapplication.model.Photo
import com.example.myapplication.model.Todo
import kotlinx.coroutines.flow.Flow

interface ApiService {
    fun getTodos(): Flow<ApiResult<List<Todo>>>
    fun getPhotos(): Flow<ApiResult<List<Photo>>>
}
