package com.naesseuapp.flashlight

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.azeesoft.lib.colorpicker.ColorPickerDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    var dialog = TimeFragment()
    var isRunning = false
    var data = ""

    var thread = ThreadClass()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEvents()
        setupValues()

    }



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun setupEvents() {

        var Check_Num = 0
        val flash = FlashActivity(mContext)
        val colorPickerDialog =
            ColorPickerDialog.createColorPickerDialog(mContext, ColorPickerDialog.DARK_THEME)
        var colorPick = 0



        btnNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.colorMenu -> {
                    colorPickerDialog.show()
                    true
                }
                R.id.flashMenu -> {
                    if (Check_Num == 0) {
                        Check_Num = 1
                        starBtn.visibility = View.GONE
                        starBright.visibility = View.VISIBLE
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            flash.flashOn()
                        }
                    } else if (Check_Num == 1) {
                        Check_Num = 0
                        starBright.visibility = View.GONE
                        starBtn.visibility = View.VISIBLE
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            flash.flashOff()
                        }
                    }
                    true
                }
                R.id.timeMenu -> {

                    var args = Bundle()
                    args.putBoolean("isRunning", isRunning)
                    dialog.arguments = args

                    dialog.show(supportFragmentManager, "tag")

                    true
                }
                else -> false
            }

        }

        colorPickerDialog.setOnColorPickedListener { color, hexVal ->
            colorPick = Color.parseColor(hexVal)
            starBtn.setColorFilter(colorPick)
            //starBtn.background.setColorFilter(colorPick)
        }
        // Flash
        starBtn.setOnClickListener {
            val intent = Intent(mContext, ScreenLightActivity::class.java)
            intent.putExtra("colorPick", colorPick)
            if(dialog.arguments?.getString("TIMER") != null) {
                intent.putExtra("timer", dialog.arguments?.getString("TIMER"))
            }
            startActivity(intent)
        }// starBtn


    }// setupEvents

    override fun setupValues() {

    }

    fun isRunningTimer(){
        isRunning = true
    }
    fun isResetTimer(){
        isRunning = false
        data = ""
    }

    fun getTimer(){
        data = dialog.arguments?.getString("TIMER")!!
        if (data != null || data != "") {
            MainTimeGoingTxt.visibility = View.VISIBLE
            MainTimeGoingTxt.text = data
        }

        isRunning = true
        thread.start()
    }

    inner class ThreadClass : Thread(){
        override fun run() {
            while (isRunning){
                SystemClock.sleep(100)
                var time = System.currentTimeMillis()

                runOnUiThread {
                   MainTimeGoingTxt.text = data
                }
            }
        }
    }

}
