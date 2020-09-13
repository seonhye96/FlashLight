package com.naesseuapp.flashlight

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.graphics.BlendModeCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.azeesoft.lib.colorpicker.ColorPickerDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_screen_light.*
import kotlinx.android.synthetic.main.activity_timer.*

class MainActivity : BaseActivity() {

    var dialog = TimeFragment()

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
                    Log.d("ckeck", "ckeck")
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
            startActivity(intent)
        }// starBtn


    }// setupEvents

    override fun setupValues() {
        var data = dialog.arguments?.getString("TIMER")
        if (data != null) {
            MainTimeGoingTxt.text = data
        }
        Log.d("ARG", "ARG : ${dialog.arguments?.getString("TIMER")}")

    }


    /*
    fun timePicker() {
        val builder = AlertDialog.Builder(mContext)
        val dialogView = layoutInflater.inflate(R.layout.time_picker_layout, null)

        val dialogTime = dialogView.findViewById<TextView>(R.id.selectTimeTxt)



        builder.setView(dialogView)
            .setPositiveButton("실행") { dialogInterface, i ->

                MainTimeGoingTxt.text = dialogTime.text
                MainTimeGoingTxt.visibility = View.VISIBLE

                ScreenTimeGoingTxt.text = dialogTime.text
                ScreenTimeGoingTxt.visibility = View.VISIBLE
            }
            .setNegativeButton("취소") { dialogInterface, i ->
            }.show()
    }
     */
}
