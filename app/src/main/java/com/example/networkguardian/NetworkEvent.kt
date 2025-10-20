package com.example.networkguardian

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "network_events")
data class NetworkEvent(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val transportType: String,
    val networkInfo: String,
    val riskLevel: String // "An toàn", "Không rõ", "Nguy hiểm"
)
