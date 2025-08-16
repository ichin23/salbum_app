package com.ichin23.salbum.di

import com.ichin23.salbum.domain.repository.AlbumRepository
import com.ichin23.salbum.domain.repository.RatingsRepository
import com.ichin23.salbum.domain.repository.UserRepository
import com.ichin23.salbum.domain.repositoryImpl.AlbumRepositoryInMemory
import com.ichin23.salbum.domain.repositoryImpl.RatingsRepositoryInMemory
import com.ichin23.salbum.domain.repositoryImpl.UserRepostioryInMemory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideAlbumRepository(): AlbumRepository{
        return AlbumRepositoryInMemory()
    }

    @Provides
    @Singleton
    fun provideRatingsRepository(): RatingsRepository{
        return RatingsRepositoryInMemory();
    }

    @Provides
    @Singleton
    fun provideUsersRepository(): UserRepository{
        return UserRepostioryInMemory();
    }
}