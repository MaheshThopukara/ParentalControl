package com.mahesh.parentalcontrol.core.di

import com.mahesh.parentalcontrol.data.local.dao.AppBlockListDao
import com.mahesh.parentalcontrol.data.local.dao.AppLimitDao
import com.mahesh.parentalcontrol.data.local.dao.DeviceTimeLimitDao
import com.mahesh.parentalcontrol.domain.repository.AppUsageRepository
import com.mahesh.parentalcontrol.domain.repository.PinRepository
import com.mahesh.parentalcontrol.domain.repository.RecoveryCodeRepository
import com.mahesh.parentalcontrol.domain.repository.SecurityQuestionsRepository
import com.mahesh.parentalcontrol.domain.repository.SuspendedAppRepository
import com.mahesh.parentalcontrol.domain.usecase.AddAppTimeLimitUseCase
import com.mahesh.parentalcontrol.domain.usecase.DeleteAppBlockListUseCase
import com.mahesh.parentalcontrol.domain.usecase.DeleteAppTimeLimitUseCase
import com.mahesh.parentalcontrol.domain.usecase.GetAppBlockListUseCase
import com.mahesh.parentalcontrol.domain.usecase.GetAppBlockSettingUseCase
import com.mahesh.parentalcontrol.domain.usecase.GetAppUsageUseCase
import com.mahesh.parentalcontrol.domain.usecase.GetDeviceTimeLimitUseCase
import com.mahesh.parentalcontrol.domain.usecase.MidnightRolloverUseCase
import com.mahesh.parentalcontrol.domain.usecase.SetAppBlockListUseCase
import com.mahesh.parentalcontrol.domain.usecase.SetAppBlockSettingUseCase
import com.mahesh.parentalcontrol.domain.usecase.SuspendAppUseCase
import com.mahesh.parentalcontrol.domain.usecase.UpdateAppTimeLimitUseCase
import com.mahesh.parentalcontrol.domain.usecase.UpdateDeviceTimeLimitUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.GenerateAndStoreRecoveryCodeUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.GetSecurityQuestionsUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.IsRecoveryCodeShownUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.MarkRecoveryCodeShownUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.SavePinUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.SaveSecurityAnswersUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.ValidatePinStrengthUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.ValidatePinUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.VerifyRecoveryCodeUseCase
import com.mahesh.parentalcontrol.domain.usecase.pin.VerifySecurityAnswersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetAppUsageUseCase(
        repository: AppUsageRepository,
        limitDao: AppLimitDao
    ) = GetAppUsageUseCase(repository, limitDao)

    @Provides
    @Singleton
    fun provideAddAppTimeLimitUseCase(
        repository: AppUsageRepository,
        limitDao: AppLimitDao
    ) = AddAppTimeLimitUseCase(repository, limitDao)

    @Provides
    @Singleton
    fun provideUpdateAppTimeLimitUseCase(
        repository: AppUsageRepository,
        limitDao: AppLimitDao
    ) = UpdateAppTimeLimitUseCase(repository, limitDao)

    @Provides
    @Singleton
    fun provideDeleteAppTimeLimitUseCase(
        appUsageRepository: AppUsageRepository,
        suspendedAppRepository: SuspendedAppRepository,
        limitDao: AppLimitDao
    ) = DeleteAppTimeLimitUseCase(appUsageRepository, suspendedAppRepository, limitDao)

    @Provides
    @Singleton
    fun provideGetAppBlockListUseCase(
        repository: AppUsageRepository,
        appBlockListDao: AppBlockListDao
    ) = GetAppBlockListUseCase(repository, appBlockListDao)

    @Provides
    @Singleton
    fun provideSetAppBlockSettingUseCase(
        suspendedAppRepository: SuspendedAppRepository,
        appBlockListDao: AppBlockListDao
    ) = SetAppBlockSettingUseCase(suspendedAppRepository, appBlockListDao)

    @Provides
    @Singleton
    fun provideGetAppBlockSettingUseCase(
        appBlockListDao: AppBlockListDao
    ) = GetAppBlockSettingUseCase(appBlockListDao)

    @Provides
    @Singleton
    fun provideSetAppBlockListUseCase(
        suspendedAppRepository: SuspendedAppRepository,
        appBlockListDao: AppBlockListDao
    ) = SetAppBlockListUseCase(suspendedAppRepository, appBlockListDao)

    @Provides
    @Singleton
    fun provideDeleteAppBlockListUseCase(
        suspendedAppRepository: SuspendedAppRepository,
        appBlockListDao: AppBlockListDao
    ) = DeleteAppBlockListUseCase(suspendedAppRepository, appBlockListDao)

    @Provides
    @Singleton
    fun provideSuspendAppUseCase(
        suspendedAppRepository: SuspendedAppRepository
    ) = SuspendAppUseCase(suspendedAppRepository)

    @Provides
    @Singleton
    fun provideGetDeviceTimeLimitUseCase(
        deviceTimeLimitDao: DeviceTimeLimitDao
    ) = GetDeviceTimeLimitUseCase(deviceTimeLimitDao)

    @Provides
    @Singleton
    fun provideUpdateDeviceTimeLimitUseCase(
        deviceTimeLimitDao: DeviceTimeLimitDao
    ) = UpdateDeviceTimeLimitUseCase(deviceTimeLimitDao)

    @Provides
    @Singleton
    fun provideSavePinUseCase(
        pinRepository: PinRepository
    ) = SavePinUseCase(pinRepository)

    @Provides
    @Singleton
    fun provideValidatePinStrengthUseCase() = ValidatePinStrengthUseCase()

    @Provides
    @Singleton
    fun provideValidatePinUseCase(pinRepository: PinRepository) = ValidatePinUseCase(pinRepository)

    @Provides
    @Singleton
    fun provideGetSecurityQuestionsUseCase(
        securityQuestionsRepository: SecurityQuestionsRepository
    ) = GetSecurityQuestionsUseCase(securityQuestionsRepository)

    @Provides
    @Singleton
    fun provideSaveSecurityAnswersUseCase(
        securityQuestionsRepository: SecurityQuestionsRepository
    ) = SaveSecurityAnswersUseCase(securityQuestionsRepository)

    @Provides
    @Singleton
    fun provideVerifySecurityAnswersUseCase(
        securityQuestionsRepository: SecurityQuestionsRepository,
        pinRepository: PinRepository,
        recoveryCodeRepository: RecoveryCodeRepository
    ) = VerifySecurityAnswersUseCase(
        securityQuestionsRepository,
        pinRepository,
        recoveryCodeRepository
    )

    @Provides
    @Singleton
    fun provideGenerateAndStoreRecoveryCodeUseCase(
        recoveryCodeRepository: RecoveryCodeRepository
    ) = GenerateAndStoreRecoveryCodeUseCase(recoveryCodeRepository)

    @Provides
    @Singleton
    fun provideVerifyRecoveryCodeUseCase(
        recoveryCodeRepository: RecoveryCodeRepository,
        pinRepository: PinRepository,
        recoveryCodeRepository1: RecoveryCodeRepository
    ) = VerifyRecoveryCodeUseCase(recoveryCodeRepository, pinRepository, recoveryCodeRepository1)

    @Provides
    @Singleton
    fun provideMarkRecoveryCodeShownUseCase(
        recoveryCodeRepository: RecoveryCodeRepository
    ) = MarkRecoveryCodeShownUseCase(recoveryCodeRepository)

    @Provides
    @Singleton
    fun provideIsRecoveryCodeShownUseCase(
        recoveryCodeRepository: RecoveryCodeRepository
    ) = IsRecoveryCodeShownUseCase(recoveryCodeRepository)

    @Provides
    @Singleton
    fun provideMidnightRolloverUseCase(
        appUsageRepository: AppUsageRepository,
        suspendedAppRepository: SuspendedAppRepository,
        appLimitDao: AppLimitDao,
        appBlockListDao: AppBlockListDao
    ) = MidnightRolloverUseCase(
        appUsageRepository,
        suspendedAppRepository,
        appLimitDao,
        appBlockListDao
    )

}