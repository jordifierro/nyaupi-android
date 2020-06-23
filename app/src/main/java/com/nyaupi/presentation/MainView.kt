package com.nyaupi.presentation

interface MainView {

    fun showLoader()
    fun hideLoader()
    fun showActive(active: Boolean)
    fun hideActive()
    fun enableSwitch(enabled: Boolean)
    fun checkSwitch(checked: Boolean)
    fun showRetry()
    fun hideRetry()
}
