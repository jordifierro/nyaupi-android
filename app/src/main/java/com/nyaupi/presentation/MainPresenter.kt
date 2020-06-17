package com.nyaupi.presentation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.nyaupi.data.AlarmApiRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Named

class MainPresenter @Inject constructor(
    private val alarmApiRepository: AlarmApiRepository,
    @Named("main") private val mainScheduler: Scheduler
) : LifecycleObserver {

    lateinit var view: MainView

    var updateDisposable: Disposable? = null
    var getDisposable: Disposable? = null


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        getDisposable = alarmApiRepository.alarmStatus()
            .observeOn(mainScheduler)
            .subscribe {
                if (it.isSuccess()) {
                    view.hideLoader()
                    if (it.data!!.active) {
                        view.showActiveMessage()
                        view.enableActivateButton(false)
                        view.enableDeactivateButton(true)
                    }
                    else {
                        view.showInactiveMessage()
                        view.enableActivateButton(true)
                        view.enableDeactivateButton(false)
                    }
                } else if (it.isError()) {
                    view.hideLoader()
                    view.enableActivateButton(false)
                    view.enableDeactivateButton(false)
                } else if (it.isInProgress()) {
                    view.showLoader()
                    view.enableActivateButton(false)
                    view.enableDeactivateButton(false)
                }
            }
    }


    fun onActivateButtonClick() {
        updateDisposable = alarmApiRepository.activateAlarm()
            .observeOn(mainScheduler)
            .subscribe {
                if (it.isSuccess()) {
                    view.hideLoader()
                    view.showActiveMessage()
                    view.enableActivateButton(false)
                    view.enableDeactivateButton(true)
                } else if (it.isError()) {
                    view.hideLoader()
                    view.showInactiveMessage()
                    view.enableActivateButton(true)
                    view.enableDeactivateButton(false)
                } else if (it.isInProgress()) {
                    view.enableActivateButton(false)
                    view.enableDeactivateButton(false)
                    view.showLoader()
                }
            }
    }

    fun onDeactivateButtonClick() {
        updateDisposable = alarmApiRepository.deactivateAlarm()
            .observeOn(mainScheduler)
            .subscribe {
                if (it.isSuccess()) {
                    view.enableActivateButton(true)
                    view.enableDeactivateButton(false)
                    view.hideLoader()
                    view.showInactiveMessage()
                } else if (it.isError()) {
                    view.enableActivateButton(false)
                    view.enableDeactivateButton(true)
                    view.hideLoader()
                    view.showActiveMessage()
                } else if (it.isInProgress()) {
                    view.enableActivateButton(false)
                    view.enableDeactivateButton(false)
                    view.showLoader()
                }
            }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        getDisposable?.dispose()
        updateDisposable?.dispose()
    }
}
