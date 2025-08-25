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
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    @ViewModelScoped
    fun provideGetAppUsageUseCase(
        repository: AppUsageRepository,
        limitDao: AppLimitDao
    ) = GetAppUsageUseCase(repository, limitDao)

    @Provides
    @ViewModelScoped
    fun provideAddAppTimeLimitUseCase(
        repository: AppUsageRepository,
        limitDao: AppLimitDao
    ) = AddAppTimeLimitUseCase(repository, limitDao)

    @Provides
    @ViewModelScoped
    fun provideUpdateAppTimeLimitUseCase(
        repository: AppUsageRepository,
        limitDao: AppLimitDao
    ) = UpdateAppTimeLimitUseCase(repository, limitDao)

    @Provides
    @ViewModelScoped
    fun provideDeleteAppTimeLimitUseCase(
        appUsageRepository: AppUsageRepository,
        suspendedAppRepository: SuspendedAppRepository,
        limitDao: AppLimitDao
    ) = DeleteAppTimeLimitUseCase(appUsageRepository, suspendedAppRepository, limitDao)

    @Provides
    @ViewModelScoped
    fun provideGetAppBlockListUseCase(
        repository: AppUsageRepository,
        appBlockListDao: AppBlockListDao
    ) = GetAppBlockListUseCase(repository, appBlockListDao)

    @Provides
    @ViewModelScoped
    fun provideSetAppBlockSettingUseCase(
        suspendedAppRepository: SuspendedAppRepository,
        appBlockListDao: AppBlockListDao
    ) = SetAppBlockSettingUseCase(suspendedAppRepository, appBlockListDao)

    @Provides
    @ViewModelScoped
    fun provideGetAppBlockSettingUseCase(
        appBlockListDao: AppBlockListDao
    ) = GetAppBlockSettingUseCase(appBlockListDao)

    @Provides
    @ViewModelScoped
    fun provideSetAppBlockListUseCase(
        suspendedAppRepository: SuspendedAppRepository,
        appBlockListDao: AppBlockListDao
    ) = SetAppBlockListUseCase(suspendedAppRepository, appBlockListDao)

    @Provides
    @ViewModelScoped
    fun provideDeleteAppBlockListUseCase(
        suspendedAppRepository: SuspendedAppRepository,
        appBlockListDao: AppBlockListDao
    ) = DeleteAppBlockListUseCase(suspendedAppRepository, appBlockListDao)

    @Provides
    @ViewModelScoped
    fun provideSuspendAppUseCase(
        suspendedAppRepository: SuspendedAppRepository
    ) = SuspendAppUseCase(suspendedAppRepository)

    @Provides
    @ViewModelScoped
    fun provideGetDeviceTimeLimitUseCase(
        deviceTimeLimitDao: DeviceTimeLimitDao
    ) = GetDeviceTimeLimitUseCase(deviceTimeLimitDao)

    @Provides
    @ViewModelScoped
    fun provideUpdateDeviceTimeLimitUseCase(
        deviceTimeLimitDao: DeviceTimeLimitDao
    ) = UpdateDeviceTimeLimitUseCase(deviceTimeLimitDao)

    @Provides
    @ViewModelScoped
    fun provideSavePinUseCase(
        pinRepository: PinRepository
    ) = SavePinUseCase(pinRepository)

    @Provides
    @ViewModelScoped
    fun provideValidatePinStrengthUseCase() = ValidatePinStrengthUseCase()

    @Provides
    @ViewModelScoped
    fun provideValidatePinUseCase(pinRepository: PinRepository) = ValidatePinUseCase(pinRepository)

    @Provides
    @ViewModelScoped
    fun provideGetSecurityQuestionsUseCase(
        securityQuestionsRepository: SecurityQuestionsRepository
    ) = GetSecurityQuestionsUseCase(securityQuestionsRepository)

    @Provides
    @ViewModelScoped
    fun provideSaveSecurityAnswersUseCase(
        securityQuestionsRepository: SecurityQuestionsRepository
    ) = SaveSecurityAnswersUseCase(securityQuestionsRepository)

    @Provides
    @ViewModelScoped
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
    @ViewModelScoped
    fun provideGenerateAndStoreRecoveryCodeUseCase(
        recoveryCodeRepository: RecoveryCodeRepository
    ) = GenerateAndStoreRecoveryCodeUseCase(recoveryCodeRepository)

    @Provides
    @ViewModelScoped
    fun provideVerifyRecoveryCodeUseCase(
        recoveryCodeRepository: RecoveryCodeRepository,
        pinRepository: PinRepository,
        recoveryCodeRepository1: RecoveryCodeRepository
    ) = VerifyRecoveryCodeUseCase(recoveryCodeRepository, pinRepository, recoveryCodeRepository1)

    @Provides
    @ViewModelScoped
    fun provideMarkRecoveryCodeShownUseCase(
        recoveryCodeRepository: RecoveryCodeRepository
    ) = MarkRecoveryCodeShownUseCase(recoveryCodeRepository)

    @Provides
    @ViewModelScoped
    fun provideIsRecoveryCodeShownUseCase(
        recoveryCodeRepository: RecoveryCodeRepository
    ) = IsRecoveryCodeShownUseCase(recoveryCodeRepository)


}