package com.nyaupi.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.nyaupi.R
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    private lateinit var progressBar: ProgressBar
    private lateinit var text: TextView
    private lateinit var activateButton: Button
    private lateinit var deactivateButton: Button

    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text = findViewById(R.id.text)
        activateButton = findViewById(R.id.activateButton)
        deactivateButton = findViewById(R.id.deactivateButton)
        progressBar = findViewById(R.id.progressBar)
        activateButton.setOnClickListener { presenter.onActivateButtonClick() }
        deactivateButton.setOnClickListener { presenter.onDeactivateButtonClick() }

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

    override fun showActiveMessage() {
        text.text = "Active"
    }

    override fun showInactiveMessage() {
        text.text = "Inactive"
    }

    override fun enableActivateButton(enabled: Boolean) {
        activateButton.isEnabled = enabled
    }

    override fun enableDeactivateButton(enabled: Boolean) {
        deactivateButton.isEnabled = enabled
    }
}