package me.lucky.clipeus

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.widget.Toast

class Utils(private val ctx: Context) {
    fun clean(showToast: Boolean) {
        val manager = ctx.getSystemService(ClipboardManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            manager?.clearPrimaryClip()
        } else {
            manager?.setPrimaryClip(ClipData.newPlainText("", ""))
        }
        if (manager != null && showToast)
            Toast.makeText(ctx, R.string.clipboard_cleaned, Toast.LENGTH_SHORT).show()
    }
}
