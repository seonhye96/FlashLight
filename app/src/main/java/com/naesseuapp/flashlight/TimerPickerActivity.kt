package com.naesseuapp.flashlight

import android.app.AlertDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_screen_light.*
import kotlinx.android.synthetic.main.activity_timer.*

class TimerPickerActivity : BaseActivity() {

    var timeSelected = ""

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.time_picker_layout)

        selectNumberPicker()

        makeToast(timeSelected)
        setupEvents()
        setupValues()
    }

    override fun setupEvents() {

    }

    override fun setupValues() {

    }

    private fun selectNumberPicker() {

        var hours = ""
        var min = ""
        var sec = ""

        hNumberPiker.value = 0
        mNumberPiker.value = 5
        sNumberPiker.value = 0

        // 시간
        hNumberPiker.minValue = 0
        hNumberPiker.maxValue = 6
        hNumberPiker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        // 분
        mNumberPiker.minValue = 0
        mNumberPiker.maxValue = 59
        // 초
        sNumberPiker.minValue = 0
        sNumberPiker.maxValue = 59



        if(hNumberPiker.value < 10) hours = "0${hNumberPiker.value}"
        else hours = "${hNumberPiker.value}"


        if(mNumberPiker.value < 10) min = "0${mNumberPiker.value}"
        else min = "${mNumberPiker.value}"

        if(sNumberPiker.value < 10) sec = "0${sNumberPiker.value}"
        else sec = "${sNumberPiker.value}"

        timeSelected = "${hours}${min}${sec}"
    }

    fun timePicker() {
        val builder = AlertDialog.Builder(mContext)
        val dialogView = layoutInflater.inflate(R.layout.time_picker_layout, null)

        val dialogTime = dialogView.findViewById<TextView>(R.id.selectTimeTxt)


        builder.setView(dialogView)
            .setPositiveButton("실행"){
                    dialogInterface, i ->

                MainTimeGoingTxt.text = dialogTime.text
                MainTimeGoingTxt.visibility = View.VISIBLE

                ScreenTimeGoingTxt.text = dialogTime.text
                ScreenTimeGoingTxt.visibility = View.VISIBLE
            }
            .setNegativeButton("취소"){
                    dialogInterface, i ->
            }.show()
    }
}