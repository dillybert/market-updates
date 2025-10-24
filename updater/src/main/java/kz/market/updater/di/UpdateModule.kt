package kz.market.updater.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.market.updater.domain.repository.UpdateRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UpdateModule {

    @Binds
    @Singleton
    fun provideUpdateRepository(
        updateRepository: UpdateRepositoryImpl
    ): UpdateRepository = updateRepository

}