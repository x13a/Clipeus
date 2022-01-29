package me.lucky.clipeus

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build

class Utils(private val ctx: Context) {
    fun clean() {
        val manager = ctx.getSystemService(ClipboardManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            manager?.clearPrimaryClip()
        } else {
            manager?.setPrimaryClip(ClipData.newPlainText("", ""))
        }
    }
}
