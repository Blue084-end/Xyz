package com.example.networkguardian

import androidx.room.*

@Dao
interface NetworkEventDao {
    @Insert
    fun insert(event: NetworkEvent)

    @Query("SELECT * FROM network_events ORDER BY timestamp DESC")
    fun getAll(): List<NetworkEvent>
}
