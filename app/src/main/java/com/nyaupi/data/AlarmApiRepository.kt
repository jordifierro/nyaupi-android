package com.nyaupi.data

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Scheduler
import retrofit2.Retrofit
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Named

class AlarmApiRepository (retrofit: Retrofit, @Named("io") private val ioScheduler: Scheduler) {

    private val authApi: AlarmApi = retrofit.create(AlarmApi::class.java)

    fun alarmStatus(): Flowable<Result<Alarm>> =
        authApi.alarmStatus()
            .subscribeOn(ioScheduler)
            .compose(AlarmResultTransformer())
            .startWith(Result(Status.IN_PROGRESS))

    fun activateAlarm(): Flowable<Result<Alarm>> =
        authApi.activateAlarm()
            .subscribeOn(ioScheduler)
            .compose(AlarmResultTransformer())
            .startWith(Result(Status.IN_PROGRESS))

    fun deactivateAlarm(): Flowable<Result<Alarm>> =
        authApi.deactivateAlarm()
            .subscribeOn(ioScheduler)
            .compose(AlarmResultTransformer())
            .startWith(Result(Status.IN_PROGRESS))

    class AlarmResultTransformer() : FlowableTransformer<retrofit2.adapter.rxjava2.Result<AlarmMapper>, Result<Alarm>> {

        override fun apply(
            upstream: Flowable<retrofit2.adapter.rxjava2.Result<AlarmMapper>>): Flowable<Result<Alarm>> =
            upstream.map {
                    if (it.isError) throw it.error()!!
                    else if (it.response()!!.isSuccessful.not())
                        throw Exception(it.response()!!.errorBody()!!.string())
                    else Result(Status.SUCCESS, it.response()!!.body()!!.toDomain())
                }
                .onErrorResumeNext { error: Throwable ->
                    if (error is UnknownHostException || error is SocketTimeoutException || error is SocketException)
                        Flowable.just(Result<Alarm>(Status.ERROR, null, error))
                    else Flowable.error(error)
                }
    }
}