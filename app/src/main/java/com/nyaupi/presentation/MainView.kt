package com.nyaupi.presentation

interface MainView {

    fun showLoader()
    fun hideLoader()
    fun showActiveMessage()
    fun showInactiveMessage()
    fun enableActivateButton(enabled: Boolean)
    fun enableDeactivateButton(enabled: Boolean)
}
