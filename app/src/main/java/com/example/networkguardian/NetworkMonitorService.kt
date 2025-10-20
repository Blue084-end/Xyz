package com.example.networkguardian

import android.app.*
import android.content.Context
import android.net.*
import android.os.IBinder
import androidx.core.app.NotificationCompat

class NetworkMonitorService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotification())
        startMonitoring()
        return START_STICKY
    }

    private fun createNotification(): Notification {
        val channelId = "network_monitor_channel"
        val channel = NotificationChannel(channelId, "Giám sát mạng", NotificationManager.IMPORTANCE_LOW)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Đang giám sát mạng")
            .setSmallIcon(R.drawable.ic_network)
            .build()
    }

    private fun startMonitoring() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val ip = extractIpFrom(network.toString())
                val domain = resolveDomain(ip)
                val port = extractPort(network)
                val protocol = extractProtocol(network)

                if (!ConnectionRuleManager.isAllowed(ip, domain, port, protocol)) {
                    Toast.makeText(applicationContext, "⛔ Bị chặn: $ip:$port ($protocol)", Toast.LENGTH_LONG).show()
                    return
                }

                val risk = IPAnalyzer.analyze(ip)
                val event = NetworkEvent(
                    timestamp = System.currentTimeMillis(),
                    transportType = protocol ?: "Không rõ",
                    networkInfo = "$ip:$port",
                    riskLevel = risk
                )

                Thread {
                    AppDatabase.getInstance(applicationContext).networkEventDao().insert(event)
                }.start()
            }

            override fun onLost(network: Network) {
                Toast.makeText(applicationContext, "🔌 Mạng bị mất", Toast.LENGTH_SHORT).show()
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
