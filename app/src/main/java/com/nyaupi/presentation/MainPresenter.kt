package com.nyaupi.presentation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.nyaupi.data.Alarm
import com.nyaupi.data.AlarmApiRepository
import com.nyaupi.data.Result
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import javax.inject.Inject
import javax.inject.Named

class MainPresenter @Inject constructor(
    private val alarmApiRepository: AlarmApiRepository,
    @Named("main") private val mainScheduler: Scheduler
) : LifecycleObserver {

    lateinit var view: MainView

    var disposable: Disposable? = null


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        disposable = alarmApiRepository.alarmStatus()
            .observeOn(mainScheduler)
            .subscribe(resultConsumer)
    }

    fun onActivateButtonClick() {
        disposable = alarmApiRepository.activateAlarm()
            .observeOn(mainScheduler)
            .subscribe(resultConsumer)
    }

    fun onDeactivateButtonClick() {
        disposable = alarmApiRepository.deactivateAlarm()
            .observeOn(mainScheduler)
            .subscribe(resultConsumer)
    }

    private val resultConsumer = Consumer<Result<Alarm>> {
        if (it.isSuccess()) {
            view.hideLoader()
            if (it.data!!.active) {
                view.showActive(true)
                view.enableSwitch(true)
                view.checkSwitch(true)
            }
            else {
                view.showActive(false)
                view.enableSwitch(true)
                view.checkSwitch(false)
            }
        } else if (it.isError()) {
            view.hideLoader()
            view.hideActive()
            view.enableSwitch(false)
        } else if (it.isInProgress()) {
            view.showLoader()
            view.hideActive()
            view.enableSwitch(false)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        disposable?.dispose()
    }
}
