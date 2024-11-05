package com.example.myapplication.model

sealed class ApiResult<T>(val data:T?=null, val error:String?=null){
    class Success<T>(todos: T):ApiResult<T>(data = todos)
    class Error<T>(error: String):ApiResult<T>(error = error)
    class Loading<T>:ApiResult<T>()
}
