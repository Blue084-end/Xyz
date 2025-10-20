package com.example.networkguardian

import android.content.*
import android.widget.Toast

class ScreenReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_SCREEN_OFF -> {
                FirewallController.toggleBlockAll(true)
                Toast.makeText(context, "⛔ Mạng đã bị khóa khi tắt màn hình", Toast.LENGTH_SHORT).show()
            }
            Intent.ACTION_SCREEN_ON -> {
                FirewallController.toggleBlockAll(false)
                Toast.makeText(context, "✅ Mạng đã mở lại", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
