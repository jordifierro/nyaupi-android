package com.nyaupi.data

import io.reactivex.Flowable
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.POST


interface AlarmApi {

    @GET("/status")
    fun alarmStatus(): Flowable<Result<AlarmMapper>>

    @POST("/on")
    fun activateAlarm(): Flowable<Result<AlarmMapper>>

    @POST("/off")
    fun deactivateAlarm(): Flowable<Result<AlarmMapper>>
}