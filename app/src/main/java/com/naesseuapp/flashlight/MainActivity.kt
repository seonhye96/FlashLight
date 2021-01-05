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
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.naesseuapp.flashlight.shared.App
import com.naesseuapp.flashlight.shared.MySharedPreferences
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_screen_light.*

class MainActivity : BaseActivity() {

    lateinit var mAdView : AdView

    var dialog = TimeFragment()
    var isRunningMain = false
    var data = ""

    var thread = ThreadClass()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

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
                    args.putBoolean("isRunning", isRunningMain)
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
            var intentScrean = Intent(mContext, ScreenLightActivity::class.java)
            intentScrean.putExtra("colorPick", colorPick)

            intentScrean.putExtra("isRunningScrean", isRunningMain)
            if(isRunningMain == true) {
                intentScrean.putExtra("TIMEGOING", dialog.arguments?.getString("TIMER")!!)
            }

            startActivity(intentScrean)
        }// starBtn
        //애드몹
        mAdView.adListener = object : AdListener(){
            override fun onAdLoaded() {
                super.onAdLoaded()
                //광고 문제없이 로드시 출력
                Log.d("@@@", "onAdLoaded")
            }

            override fun onAdFailedToLoad(p0: Int) {
                super.onAdFailedToLoad(p0)
                //광고 로드에 문제가 있을시 출력
                Log.d("@@@", "onAdFailedToLoad : "+ p0)
            }
        }



    }// setupEvents

    override fun setupValues() {

    }

    fun isRunningTimer(){
        isRunningMain = true
    }
    fun isResetTimer(){
        isRunningMain = false
        data = ""
    }

    fun getTimer(){
        data = dialog.arguments?.getString("TIMER")!!
        if (data != null || data != "") {
            MainTimeGoingTxt.visibility = View.VISIBLE
            MainTimeGoingTxt.text = data
        }

        isRunningMain = true
        thread.start()
    }

    inner class ThreadClass : Thread(){
        override fun run() {
            //TODO
            while (isRunningMain){
                SystemClock.sleep(100)
                var time = System.currentTimeMillis()

                runOnUiThread {
                    var data = dialog.arguments?.getString("TIMER")!!
                    MainTimeGoingTxt.text = data
                    App.prefs.myEditText = data
                    if(MainTimeGoingTxt.text.equals("00:00:00")){
                        isRunningMain = false
                        System.exit(0)
                    }
                }
            }
        }
    }
}
