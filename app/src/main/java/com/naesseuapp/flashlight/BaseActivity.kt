package com.naesseuapp.flashlight

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun setupEvents()
    abstract fun setupValues()

}