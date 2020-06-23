package com.nyaupi.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import com.nyaupi.R
import com.suke.widget.SwitchButton
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var progressBar: ProgressBar
    private lateinit var lockImage: ImageView
    private lateinit var unlockImage: ImageView
    private lateinit var switchButton: SwitchButton
    private lateinit var retry: RelativeLayout

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main)

        lockImage = findViewById(R.id.lockImage)
        unlockImage = findViewById(R.id.unlockImage)
        progressBar = findViewById(R.id.progressBar)
        switchButton = findViewById(R.id.switchButton)
        switchButton.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) presenter.onActivateButtonClick()
            else presenter.onDeactivateButtonClick()
        }
        retry = findViewById(R.id.retry)
        retry.setOnClickListener {
            presenter.onRetryClick()
        }

        NyaupiApplication.injector.inject(this)
        presenter.view = this
        lifecycle.addObserver(presenter)
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun showActive(active: Boolean) {
        if (active) {
            unlockImage.visibility = View.INVISIBLE
            lockImage.visibility = View.VISIBLE
        }
        else {
            unlockImage.visibility = View.VISIBLE
            lockImage.visibility = View.INVISIBLE
        }
    }

    override fun hideActive() {
        unlockImage.visibility = View.INVISIBLE
        lockImage.visibility = View.INVISIBLE
    }

    override fun enableSwitch(enabled: Boolean) {
        switchButton.isEnabled = enabled
        if (enabled) switchButton.visibility = View.VISIBLE
        else switchButton.visibility = View.INVISIBLE
    }

    override fun checkSwitch(checked: Boolean) {
        switchButton.isChecked = checked
    }

    override fun showRetry() {
        retry.visibility = View.VISIBLE
    }

    override fun hideRetry() {
        retry.visibility = View.INVISIBLE
    }
}