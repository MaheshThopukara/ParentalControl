package com.mahesh.parentalcontrol.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mahesh.parentalcontrol.data.local.dao.AppBlockListDao
import com.mahesh.parentalcontrol.data.local.dao.AppLimitDao
import com.mahesh.parentalcontrol.data.local.dao.DeviceTimeLimitDao
import com.mahesh.parentalcontrol.data.local.dao.SecurityDao
import com.mahesh.parentalcontrol.data.local.entity.AppBlockSettingEntity
import com.mahesh.parentalcontrol.data.local.entity.AppLimitEntity
import com.mahesh.parentalcontrol.data.local.entity.BlockedAppEntity
import com.mahesh.parentalcontrol.data.local.entity.DeviceTimeLimitEntity
import com.mahesh.parentalcontrol.data.local.entity.SecurityAnswerEntity

@Database(
    entities = [
        AppLimitEntity::class,
        BlockedAppEntity::class,
        AppBlockSettingEntity::class,
        DeviceTimeLimitEntity::class,
        SecurityAnswerEntity::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appLimitDao(): AppLimitDao

    abstract fun appBlockListDao(): AppBlockListDao

    abstract fun deviceTimeLimitDao(): DeviceTimeLimitDao

    abstract fun securityDao(): SecurityDao
}