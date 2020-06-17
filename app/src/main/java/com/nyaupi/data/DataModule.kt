package com.nyaupi.data

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.nyaupi.BuildConfig
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideAuthHttpInterceptor() = AuthHttpInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(authHttpInterceptor: AuthHttpInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(authHttpInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideGsonConverter(): Converter.Factory = GsonConverterFactory.create(
        GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create())

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gsonConverterFactory: Converter.Factory) =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideAlarmApiRepository(retrofit: Retrofit, @Named("io") scheduler: Scheduler) =
        AlarmApiRepository(retrofit, scheduler)
}