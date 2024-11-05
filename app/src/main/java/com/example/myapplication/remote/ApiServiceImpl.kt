package com.example.myapplication.remote

import android.util.Log
import com.example.myapplication.model.ApiResult
import com.example.myapplication.model.Photo
import com.example.myapplication.model.Todo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val httpClient: HttpClient) : ApiService {
    override fun getTodos() : Flow<ApiResult<List<Todo>>> = flow{
        emit(ApiResult.Loading())
        try {
            emit(ApiResult.Success(httpClient.get("/todos").body()))
        }catch (e:Exception){
            e.printStackTrace()
            emit(ApiResult.Error(e.message ?: "Something went wrong"))
        }
    }
    override fun getPhotos() : Flow<ApiResult<List<Photo>>> = flow{
        emit(ApiResult.Loading())
        try {
            Log.d("RRR","Resul!")
            val result : ApiResult<List<Photo>> = ApiResult.Success(httpClient.get("/photos").body())
            Log.d("RRR",result.data.toString())
            emit(result)
        }catch (e:Exception){
            e.printStackTrace()
            emit(ApiResult.Error(e.message ?: "Something went wrong"))
        }
    }
}
