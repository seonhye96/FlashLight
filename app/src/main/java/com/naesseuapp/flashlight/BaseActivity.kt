package com.naesseuapp.flashlight

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    val mContext = this

    abstract fun setupEvents()
    abstract fun setupValues()

    fun makeToast(msg : String){
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun stopApp() {
        startService(Intent(mContext, UnCatchTaskService::class.java))
        startService(Intent(mContext, UnCatchTaskService::class.java))
    }

}