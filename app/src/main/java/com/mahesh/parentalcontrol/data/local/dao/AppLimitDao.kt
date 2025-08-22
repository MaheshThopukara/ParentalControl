package com.mahesh.parentalcontrol.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mahesh.parentalcontrol.data.local.entity.AppLimitEntity

@Dao
interface AppLimitDao {
    @Query("SELECT * FROM app_limits")
    suspend fun getAllLimits(): List<AppLimitEntity>

    @Query("SELECT * FROM app_limits WHERE packageName = :packageName LIMIT 1")
    suspend fun getLimit(packageName: String): AppLimitEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLimit(limit: AppLimitEntity)

    @Delete
    suspend fun deleteLimit(limit: AppLimitEntity)
}