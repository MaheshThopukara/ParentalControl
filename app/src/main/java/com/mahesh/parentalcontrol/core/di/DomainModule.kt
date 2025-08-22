package com.mahesh.parentalcontrol.core.di

import com.mahesh.parentalcontrol.data.local.dao.AppBlockListDao
import com.mahesh.parentalcontrol.data.local.dao.AppLimitDao
import com.mahesh.parentalcontrol.data.local.dao.DeviceTimeLimitDao
import com.mahesh.parentalcontrol.domain.repository.AppUsageRepository
import com.mahesh.parentalcontrol.domain.repository.SuspendedAppRepository
import com.mahesh.parentalcontrol.domain.usecase.AddAppTimeLimitUseCase
import com.mahesh.parentalcontrol.domain.usecase.DeleteAppBlockListUseCase
import com.mahesh.parentalcontrol.domain.usecase.DeleteAppTimeLimitUseCase
import com.mahesh.parentalcontrol.domain.usecase.GetAppBlockListUseCase
import com.mahesh.parentalcontrol.domain.usecase.GetAppBlockSettingUseCase
import com.mahesh.parentalcontrol.domain.usecase.GetAppUsageUseCase
import com.mahesh.parentalcontrol.domain.usecase.GetDeviceTimeLimitUseCase
import com.mahesh.parentalcontrol.domain.usecase.SetAppBlockListUseCase
import com.mahesh.parentalcontrol.domain.usecase.SetAppBlockSettingUseCase
import com.mahesh.parentalcontrol.domain.usecase.SuspendAppUseCase
import com.mahesh.parentalcontrol.domain.usecase.UpdateAppTimeLimitUseCase
import com.mahesh.parentalcontrol.domain.usecase.UpdateDeviceTimeLimitUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetAppUsageUseCase(
        repository: AppUsageRepository,
        limitDao: AppLimitDao
    ) = GetAppUsageUseCase(repository, limitDao)

    @Provides
    fun provideAddAppTimeLimitUseCase(
        repository: AppUsageRepository,
        limitDao: AppLimitDao
    ) = AddAppTimeLimitUseCase(repository, limitDao)

    @Provides
    fun provideUpdateAppTimeLimitUseCase(
        repository: AppUsageRepository,
        limitDao: AppLimitDao
    ) = UpdateAppTimeLimitUseCase(repository, limitDao)

    @Provides
    fun provideDeleteAppTimeLimitUseCase(
        appUsageRepository: AppUsageRepository,
        suspendedAppRepository: SuspendedAppRepository,
        limitDao: AppLimitDao
    ) = DeleteAppTimeLimitUseCase(appUsageRepository, suspendedAppRepository, limitDao)

    @Provides
    fun provideGetAppBlockListUseCase(
        repository: AppUsageRepository,
        appBlockListDao: AppBlockListDao
    ) = GetAppBlockListUseCase(repository, appBlockListDao)

    @Provides
    fun provideSetAppBlockSettingUseCase(
        suspendedAppRepository: SuspendedAppRepository,
        appBlockListDao: AppBlockListDao
    ) = SetAppBlockSettingUseCase(suspendedAppRepository, appBlockListDao)

    @Provides
    fun provideGetAppBlockSettingUseCase(
        appBlockListDao: AppBlockListDao
    ) = GetAppBlockSettingUseCase(appBlockListDao)

    @Provides
    fun provideSetAppBlockListUseCase(
        suspendedAppRepository: SuspendedAppRepository,
        appBlockListDao: AppBlockListDao
    ) = SetAppBlockListUseCase(suspendedAppRepository, appBlockListDao)

    @Provides
    fun provideDeleteAppBlockListUseCase(
        suspendedAppRepository: SuspendedAppRepository,
        appBlockListDao: AppBlockListDao
    ) = DeleteAppBlockListUseCase(suspendedAppRepository, appBlockListDao)

    @Provides
    fun provideSuspendAppUseCase(
        suspendedAppRepository: SuspendedAppRepository
    ) = SuspendAppUseCase(suspendedAppRepository)

    @Provides
    fun provideGetDeviceTimeLimitUseCase(
        deviceTimeLimitDao: DeviceTimeLimitDao
    ) = GetDeviceTimeLimitUseCase(deviceTimeLimitDao)

    @Provides
    fun provideUpdateDeviceTimeLimitUseCase(
        deviceTimeLimitDao: DeviceTimeLimitDao
    ) = UpdateDeviceTimeLimitUseCase(deviceTimeLimitDao)
}