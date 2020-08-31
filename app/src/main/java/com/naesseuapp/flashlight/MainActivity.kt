package com.naesseuapp.flashlight

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

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

        starBtn.setOnClickListener {

            if (Check_Num == 0){
                Toast.makeText(mContext, "반짝!", Toast.LENGTH_SHORT).show()
                Check_Num = 1
                starBtn.setBackgroundResource(R.drawable.star2)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    flash.flashOn()
                }
            }else if (Check_Num == 1){
                Toast.makeText(mContext, "흐유", Toast.LENGTH_SHORT).show()
                Check_Num = 0
                starBtn.setBackgroundResource(R.drawable.star1)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    flash.flashOff()
                }
            }
        }
    }

    override fun setupValues() {

    }


}
