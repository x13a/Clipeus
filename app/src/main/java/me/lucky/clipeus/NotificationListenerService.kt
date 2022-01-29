package me.lucky.clipeus

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.service.notification.NotificationListenerService
import androidx.annotation.RequiresApi

class NotificationListenerService : NotificationListenerService() {
    private val screenReceiver = ScreenReceiver()

    override fun onCreate() {
        super.onCreate()
        registerReceiver(screenReceiver, IntentFilter(Intent.ACTION_SCREEN_OFF))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(screenReceiver)
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return
        migrate()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun migrate() {
        val packages = packageManager
            .getInstalledPackages(0)
            .map { it.packageName }
            .toMutableSet()
        packages.addAll(packageManager
            .queryIntentActivities(
                Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER),
                0,
            )
            .map{ it.activityInfo.packageName })
        migrateNotificationFilter(0, packages.toMutableList())
    }

    private class ScreenReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (context == null || intent?.action != Intent.ACTION_SCREEN_OFF) return
            Utils(context).clean()
        }
    }
}
