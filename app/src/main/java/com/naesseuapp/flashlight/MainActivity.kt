package com.naesseuapp.flashlight

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.graphics.toColor
import androidx.core.view.isInvisible
import com.azeesoft.lib.colorpicker.ColorPickerDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.file.StandardWatchEventKinds

class MainActivity : BaseActivity() {

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
        val colorPickerDialog = ColorPickerDialog.createColorPickerDialog(mContext, R.style.CustomColorPicker)

        btnNav.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.colorMenu ->{
                    colorPickerDialog.show()
                    true
                }R.id.flashMenu ->{
                    if (Check_Num == 0){
                        Check_Num = 1
                        starBtn.visibility = View.GONE
                        starBright.visibility = View.VISIBLE
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            flash.flashOn()
                        }
                    }else if (Check_Num == 1){
                        Check_Num = 0
                        starBright.visibility = View.GONE
                        starBtn.visibility = View.VISIBLE
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            flash.flashOff()
                        }
                    }
                    true
                }R.id.timeMenu ->{
                    true
                }
                else -> false
            }

        }

        // Flash
        starBtn.setOnClickListener {
            val intent = Intent(mContext, ScreenLightActivity::class.java)
        }// starBtn

        /*
        // Timer
        timerBtn.setOnClickListener {
            Handler().postDelayed({

                startActivity(intent)
            }, 2000)
         }*/
    }// setupEvents

    override fun setupValues() {
    }
}
