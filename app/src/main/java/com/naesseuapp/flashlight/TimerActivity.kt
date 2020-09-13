package com.naesseuapp.flashlight

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_screen_light.*
import kotlinx.android.synthetic.main.activity_timer.*
import java.util.*
import android.os.CountDownTimer as CountDownTimer

class TimerActivity : BaseActivity() {

    private var START_TIME_IN_MILLIS = 600000
    var mCountDownTimer: CountDownTimer? = null
    var mTimeLeftInMillis = START_TIME_IN_MILLIS.toLong()
    var mTimerRunning = false
    var timerGoing = 0

    var timeLeftFormatted = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        setupEvents()
        setupValues()
    }

    override fun setupEvents() {

        selectNumberPicker()

        startBtn.setOnClickListener{
            if (mTimerRunning){
                pauseTimer()
            }else{
                startTimer()
            }

        }

        resetBtn.setOnClickListener {
            resetTimer()
        }
        updateCountDownText()

    }

    override fun setupValues() {
    }

    private fun selectNumberPicker() {
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
    }


    private fun startTimer() {
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000){
            override fun onTick(millsUntilFinished : Long) {
                mTimeLeftInMillis = millsUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                mTimerRunning = false
                startBtn.text = "START"
                startBtn.visibility = View.INVISIBLE
                resetBtn.visibility = View.VISIBLE
            }

        }.start()

        mTimerRunning = true
        startBtn.text = "PAUSE"
        resetBtn.visibility = View.INVISIBLE


    }

    private fun pauseTimer() {
        mCountDownTimer?.cancel()
        mTimerRunning = false
        //메인화면
        MainTimeGoingTxt.visibility = View.VISIBLE
        //스크린화면
        ScreenTimeGoingTxt.visibility = View.VISIBLE
        resetBtn.visibility = View.VISIBLE
    }

    private fun resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS.toLong()
        updateCountDownText()
        startBtn.text = "START"
        resetBtn.visibility = View.INVISIBLE
        startBtn.visibility = View.VISIBLE
    }

    private fun updateCountDownText() {
        val minutes = (mTimeLeftInMillis.toInt() / 1000) / 60
        val seconds = (mTimeLeftInMillis.toInt() / 1000) % 60

        timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        TimeTxt.text = timeLeftFormatted

        //val intent = Intent(mContext, MainActivity::class.java)
        //intent.putExtra("time", timeLeftFormatted)
        //startActivity(intent)

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(timeLeftFormatted, timeLeftFormatted)
        makeToast(timeLeftFormatted)
    }
}