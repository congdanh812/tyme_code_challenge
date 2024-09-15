package dc.danh.tyme.code.challenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dc.danh.tyme.code.challenge.data.api.GitHubApiService
import dc.danh.tyme.code.challenge.data.local.UserDao
import dc.danh.tyme.code.challenge.data.repository.UserRepository
import javax.inject.Singleton

/**
 * Dagger module for providing repository dependencies.
 *
 * This module provides the [UserRepository] instance, injecting the required
 * [GitHubApiService] and [UserDao] dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(apiService: GitHubApiService, userDao: UserDao): UserRepository {
        return UserRepository(apiService, userDao)
    }
}
