package com.nyaupi.data

import io.reactivex.Flowable
import io.reactivex.Scheduler
import retrofit2.Retrofit
import javax.inject.Named

class AlarmApiRepository (retrofit: Retrofit, @Named("io") private val ioScheduler: Scheduler) {

    private val authApi: AlarmApi = retrofit.create(AlarmApi::class.java)

    fun alarmStatus(): Flowable<Result<Alarm>> =
        authApi.alarmStatus()
            .subscribeOn(ioScheduler)
            .map {
                if (it.isError) throw it.error()!!
                else if (it.response()!!.isSuccessful.not())
                    throw Exception(it.response()!!.errorBody()!!.string())
                else Result(Status.SUCCESS, it.response()!!.body()!!.toDomain())
            }
            .startWith(Result(Status.IN_PROGRESS))

    fun activateAlarm(): Flowable<Result<Alarm>> =
        authApi.activateAlarm()
            .subscribeOn(ioScheduler)
            .map {
                if (it.isError) throw it.error()!!
                else if (it.response()!!.isSuccessful.not())
                    throw Exception(it.response()!!.errorBody()!!.string())
                else Result(Status.SUCCESS, it.response()!!.body()!!.toDomain())
            }
            .startWith(Result(Status.IN_PROGRESS))

    fun deactivateAlarm(): Flowable<Result<Alarm>> =
        authApi.deactivateAlarm()
            .subscribeOn(ioScheduler)
            .map {
                if (it.isError) throw it.error()!!
                else if (it.response()!!.isSuccessful.not())
                    throw Exception(it.response()!!.errorBody()!!.string())
                else Result(Status.SUCCESS, it.response()!!.body()!!.toDomain())
            }
            .startWith(Result(Status.IN_PROGRESS))
}