package com.mahesh.parentalcontrol.core.di

import android.content.Context
import com.mahesh.parentalcontrol.data.repository.AppUsageRepositoryImpl
import com.mahesh.parentalcontrol.data.repository.SuspendedAppRepositoryImpl
import com.mahesh.parentalcontrol.domain.repository.AppUsageRepository
import com.mahesh.parentalcontrol.domain.repository.SuspendedAppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideAppUsageRepository(
        @ApplicationContext context: Context
    ): AppUsageRepository = AppUsageRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideSuspendedAppRepository(
        @ApplicationContext context: Context
    ): SuspendedAppRepository = SuspendedAppRepositoryImpl(context)
}