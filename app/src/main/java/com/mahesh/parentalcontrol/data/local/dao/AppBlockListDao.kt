package com.mahesh.parentalcontrol.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahesh.parentalcontrol.data.local.entity.AppBlockSettingEntity
import com.mahesh.parentalcontrol.data.local.entity.AppLimitEntity
import com.mahesh.parentalcontrol.data.local.entity.BlockedAppEntity

@Dao
interface AppBlockListDao {
    @Query("SELECT enabled FROM app_blocklist_setting WHERE id = 0")
    fun isBlockListEnabled(): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertSettings(settings: AppBlockSettingEntity)

    @Query("SELECT * FROM blocked_apps")
    suspend fun getAllAppBlockList(): List<BlockedAppEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlocked(apps: BlockedAppEntity)

    @Delete
    suspend fun deleteBlocked(apps: BlockedAppEntity)

    @Query("DELETE FROM blocked_apps")
    suspend fun clearAllAppBlockList()
}