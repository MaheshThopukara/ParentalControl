package com.mahesh.parentalcontrol.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahesh.parentalcontrol.data.local.entity.AppBlockSettingEntity
import com.mahesh.parentalcontrol.data.local.entity.AppLimitEntity
import com.mahesh.parentalcontrol.data.local.entity.BlockedAppEntity
import com.mahesh.parentalcontrol.data.local.entity.DeviceTimeLimitEntity

@Dao
interface DeviceTimeLimitDao {
    @Query("SELECT * FROM device_time_limit WHERE id = 0")
    fun getDeviceTimeLimit(): DeviceTimeLimitEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDeviceTimeLimit(settings: DeviceTimeLimitEntity)

}