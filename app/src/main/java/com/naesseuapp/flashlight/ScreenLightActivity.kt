package com.naesseuapp.flashlight

import android.app.ActionBar
import android.os.Bundle
import android.view.Window
import kotlinx.android.synthetic.main.activity_screen_light.*

class ScreenLightActivity : BaseActivity() {
    override fun supportRequestWindowFeature(featureId: Int): Boolean {
        return super.supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_light)

        setupEvents()
        setupValues()

    }

    override fun setupEvents() {
    }

    override fun setupValues() {
        var colorPick = intent.getIntExtra("colorPick", 0)
        screenAct.setBackgroundColor(colorPick)
    }

}