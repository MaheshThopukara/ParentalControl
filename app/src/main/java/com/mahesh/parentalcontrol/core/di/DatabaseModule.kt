package com.mahesh.parentalcontrol.core.di

import android.content.Context
import androidx.room.Room
import com.mahesh.parentalcontrol.data.local.AppDatabase
import com.mahesh.parentalcontrol.data.local.dao.AppBlockListDao
import com.mahesh.parentalcontrol.data.local.dao.AppLimitDao
import com.mahesh.parentalcontrol.data.local.dao.DeviceTimeLimitDao
import com.mahesh.parentalcontrol.data.local.dao.SecurityDao
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
    @Singleton
    fun provideAppLimitDao(db: AppDatabase): AppLimitDao = db.appLimitDao()

    @Provides
    @Singleton
    fun provideAppBlockListDao(db: AppDatabase): AppBlockListDao = db.appBlockListDao()

    @Provides
    @Singleton
    fun provideDeviceTimeLimitDao(db: AppDatabase): DeviceTimeLimitDao = db.deviceTimeLimitDao()

    @Provides
    @Singleton
    fun provideSecurityDao(db: AppDatabase): SecurityDao = db.securityDao()
}