package com.naesseuapp.flashlight

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.time_fragment.*
import kotlinx.android.synthetic.main.time_fragment.view.*
import java.util.*


class TimeFragment : DialogFragment(){

    var timeSelected = ""
    var timeLeftFormatted = ""
    var timer: CountDownTimer? = null
    var isRunning = true

    var fragment = this
    var bundle = Bundle()

    companion object {

        fun newInstance(): TimeFragment {
            return TimeFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.time_fragment, container, false)

        isRunning = (activity as MainActivity).isRunning    //arguments!!.getBoolean("timerRunning")

        if(isRunning == true){
            view.startAABtn.isEnabled = false
            view.resetAABtn.isEnabled = true
        }

        view.startAABtn.setOnClickListener {
            checkNumber()
            countDown(timeSelected)

            view.startAABtn.isEnabled = false
            view.resetAABtn.isEnabled = true

            dismiss()
        }

        view.resetAABtn.setOnClickListener {

            startAABtn.isEnabled = true
            resetAABtn.isEnabled = false

            (activity as MainActivity)?.isResetTimer()

            timer?.cancel()
        }

        view.cancelAABtn.setOnClickListener {
            dismiss()
        }
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        selectNumberPicker()

    }

    private fun selectNumberPicker() {

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



    }

    private fun checkNumber() : String {

        var hours = ""
        var min = ""
        var sec = ""

        if(hNumberPiker.value < 10) hours = "0${hNumberPiker.value}"
        else hours = "${hNumberPiker.value}"


        if(mNumberPiker.value < 10) min = "0${mNumberPiker.value}"
        else min = "${mNumberPiker.value}"

        if(sNumberPiker.value < 10) sec = "0${sNumberPiker.value}"
        else sec = "${sNumberPiker.value}"

        timeSelected = "${hours}${min}${sec}"


        return timeSelected
    }

    private fun countDown(time : String){
        if (timeSelected == "000000"){
            Toast.makeText(context, "시간을 입력해주세요.", Toast.LENGTH_SHORT).show()
        }else{
            var conversionTime = 0
            var getHour = time.substring(0, 2)
            var getMin = time.substring(2, 4)
            var getSecond = time.substring(4, 6)

            if (getHour.substring(0, 1) == "0") {
                getHour = getHour.substring(1, 2);
            }

            if (getMin.substring(0, 1) == "0") {
                getMin = getMin.substring(1, 2);
            }

            if (getSecond.substring(0, 1) == "0") {
                getSecond = getSecond.substring(1, 2);
            }

            conversionTime =
                (getHour.toLong() * 1000 * 3600 + getMin.toLong() * 60 * 1000 + getSecond.toLong() * 1000).toInt();

            timer = object : CountDownTimer(conversionTime.toLong(), 1000){

                override fun onTick(millisUntilFinished: Long) {
                    var hour = (millisUntilFinished / (60*60*1000))
                    val getMin = millisUntilFinished - (millisUntilFinished/(60*60*1000))
                    var min = (getMin / (60*1000))
                    var second = ((getMin % (60*1000)) / 1000)

                    timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, min, second)

                    bundle.putString("TIMER", timeLeftFormatted)
                    fragment.arguments = bundle

                    isRunning = true

                    Log.d("########## TIMEGOING : ", timeLeftFormatted)
                    try {
                        (activity as MainActivity)?.getTimer()
                    } catch (e: Exception) {
                    }
                }
                override fun onFinish() {
                    isRunning = false

                    (activity as BaseActivity)!!.stopApp()

                }
            }.start()

            (activity as MainActivity).isRunningTimer()
        }
    }

}