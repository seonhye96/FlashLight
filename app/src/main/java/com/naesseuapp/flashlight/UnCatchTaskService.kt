package com.naesseuapp.flashlight

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class UnCatchTaskService : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent){
        Toast.makeText(MainActivity(), "종료되었습니다.", Toast.LENGTH_SHORT).show()
        stopSelf()

    }
}