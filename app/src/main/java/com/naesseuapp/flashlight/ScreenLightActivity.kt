package com.naesseuapp.flashlight

import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_screen_light.*
import java.util.*

class ScreenLightActivity : BaseActivity() {

    lateinit var params : WindowManager.LayoutParams
    var bright : Float = 0.0F
    var seek = 0

    var dialog = TimeFragment()
    var isRunning = false

    var timer = ""
    var time: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_light)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        params = window.attributes

        val brightness = brightness
        seekBarScr.progress = brightness

        setupEvents()
        setupValues()
    }

    override fun onResume() {
        super.onResume()

        //기존밝기 저장
        bright = params.screenBrightness
        //최대밝기로 설정
        params.screenBrightness = 1f
        //밝기 설정 적용
        window.attributes = params
    }

    override fun onPause() {
        super.onPause()

        //기존 밝기로 변경
        params.screenBrightness = bright
        window.attributes = params
    }

    override fun setupEvents() {

        if (!canWite){
            seekBarScr.isEnabled = false
            allowWritePermission()
        }

        seekBarScr.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, i : Int, b: Boolean) {
                // seekbar 를 조작하고 있는 중 작동
                if (canWite){
                    setBrightness(i)
                    params.screenBrightness = i/100.0f
                    window.attributes = params
                }

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                // seekBar 를 조작하기 시작했을 때 작동
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                // seekBar 조작을 마무리했을 때 작동
            }

        })

        seekBarImg.setOnClickListener {
            if (seek.equals(0)){
                seekBarScr.visibility = View.VISIBLE
                seek = 1
            }else{
                seekBarScr.visibility = View.GONE
                seek = 0
            }

        }

    }

    override fun setupValues() {

        var colorPick = intent.getIntExtra("colorPick", 0)

        screenAct.setBackgroundColor(colorPick)
    }

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

}