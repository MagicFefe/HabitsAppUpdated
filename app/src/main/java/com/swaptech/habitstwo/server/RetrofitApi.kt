package com.swaptech.habitstwo.server

object RetrofitApi {
    val api by lazy {
        RetrofitClient.retrofitClient.create(Api::class.java)
    }
}