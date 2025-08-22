package com.mahesh.parentalcontrol.core.di

import android.content.Context
import androidx.room.Room
import com.mahesh.parentalcontrol.data.local.AppDatabase
import com.mahesh.parentalcontrol.data.local.dao.AppBlockListDao
import com.mahesh.parentalcontrol.data.local.dao.AppLimitDao
import com.mahesh.parentalcontrol.data.local.dao.DeviceTimeLimitDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_limits_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideAppLimitDao(db: AppDatabase): AppLimitDao = db.appLimitDao()

    @Provides
    fun provideAppBlockListDao(db: AppDatabase): AppBlockListDao = db.appBlockListDao()

    @Provides
    fun provideDeviceTimeLimitDao(db: AppDatabase): DeviceTimeLimitDao = db.deviceTimeLimitDao()
}