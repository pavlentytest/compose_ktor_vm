package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.ApiResult
import com.example.myapplication.model.Photo
import com.example.myapplication.model.Todo
import com.example.myapplication.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppViewModel @Inject constructor(private val apiService: ApiService,
                                       private val defaultDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _todos= MutableStateFlow<ApiResult<List<Todo>>>(ApiResult.Loading())
    val todos = _todos.asStateFlow()

    private val _photos= MutableStateFlow<ApiResult<List<Photo>>>(ApiResult.Loading())
    val photos = _photos.asStateFlow()

    init {
        fetchPhotos()
    }

    private fun fetchQuotes() {
        viewModelScope.launch {
            apiService.getTodos()
                .flowOn(defaultDispatcher)
                //.catch {
                //  _todos.value=ApiResult.Error(it.message ?: "Something went wrong")
                //}
                .collect{
                    _todos.emit(it)
                }
        }
    }
    private fun fetchPhotos() {
        viewModelScope.launch {
            apiService.getPhotos()
                .flowOn(defaultDispatcher)
                //.catch {
                //  _todos.value=ApiResult.Error(it.message ?: "Something went wrong")
                //}
                .collect{
                    _photos.emit(it)
                }
        }
    }
}