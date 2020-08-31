package com.naesseuapp.flashlight

import android.content.Context
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class FlashActivity (context: Context) {
    private var cameraId: String? = null

    private var cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    init {
        cameraId = getCameraId()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun flashOn() {
        cameraId?.let{
            cameraManager.setTorchMode(cameraId.toString(), true)
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun flashOff(){
        cameraId?.let {
            cameraManager.setTorchMode(cameraId.toString(), false)
        }
    }

    private fun getCameraId() : String? {
        val cameraIds = cameraManager.cameraIdList
        for(id in cameraIds){
            val info = cameraManager.getCameraCharacteristics(id)
            val flashAvailable = info.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
            val lensFacing = info.get(CameraCharacteristics.LENS_FACING)

            if(flashAvailable != null
                && flashAvailable && lensFacing != null
                && lensFacing == CameraCharacteristics.LENS_FACING_BACK)
                return id
        }
        return null
    }
}