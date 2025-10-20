package com.example.networkguardian

object IPAnalyzer {
    private val blacklistedIPs = listOf("185.234.219.", "91.212.89.", "45.143.200.")
    private val safeIPs = listOf("8.8.8.8", "1.1.1.1", "104.16.")

    fun analyze(ip: String): String = when {
        blacklistedIPs.any { ip.startsWith(it) } -> "Nguy hiểm"
        safeIPs.any { ip.startsWith(it) } -> "An toàn"
        else -> "Không rõ"
    }
}
