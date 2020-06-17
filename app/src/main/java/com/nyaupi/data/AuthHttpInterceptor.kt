package com.nyaupi.data

import com.nyaupi.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class AuthHttpInterceptor() : Interceptor {

    fun key() = "Authorization"
    fun value() = "Token " + BuildConfig.SECRET_KEY

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader(key(), value())
            .build()
        return chain.proceed(request)
    }
}