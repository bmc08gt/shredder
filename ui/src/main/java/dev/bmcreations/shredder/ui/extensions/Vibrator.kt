package dev.bmcreations.shredder.ui.extensions

import android.annotation.SuppressLint
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

@SuppressLint("MissingPermission")
fun Vibrator.vibrateCompat() {
// Vibrate for 500 milliseconds
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        //deprecated in API 26
        vibrate(100)
    }
}