package com.naesseuapp.flashlight

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Window
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import kotlinx.android.synthetic.main.activity_screen_light.*

class ScreenLightActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_light)

        val brightness = brightness
        seekBarScr.progress = brightness

        setupEvents()
        setupValues()

    }

    override fun setupEvents() {
        //val seekBar :SeekBar = findViewById(R.id.seekBarScr)

        if (!canWite){
            seekBarScr.isEnabled = false
            allowWritePermission()
        }

        seekBarScr.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, i : Int, b: Boolean) {
                // seekbar 를 조작하고 있는 중 작동
                if (canWite){
                    setBrightness(i)
                }

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                // seekBar 를 조작하기 시작했을 때 작동

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                // seekBar 조작을 마무리했을 때 작동

            }

        })
    }

    override fun setupValues() {

        var colorPick = intent.getIntExtra("colorPick", 0)
        screenAct.setBackgroundColor(colorPick)
    }


    // Extension property to get write system settings permission status
    val Context.canWite:Boolean
    get() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return Settings.System.canWrite(mContext)
        }else{
            return true
        }
    }

    fun Context.allowWritePermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(
                Settings.ACTION_MANAGE_WRITE_SETTINGS,
                Uri.parse("package:$packageName")
            )
            startActivity(intent)
        }
    }

    // 화면 밝기를 얻기위한 속성
    val Context.brightness:Int
    get() {
        return Settings.System.getInt(
            mContext.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            0
        )
    }

    // 화면 밝기 설정
    fun Context.setBrightness(value: Int):Unit{
        Settings.System.putInt(
            mContext.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            value
        )
    }



    /*
    private fun setBrightness(value : Int) {
        var num = 0
        if (value < 10) {
            num = 10
        }else if (value > 100) {
            num = 100
        }
        params.screenBrightness = (num / 100).toFloat()
        window.attributes = params
    }
    */

}