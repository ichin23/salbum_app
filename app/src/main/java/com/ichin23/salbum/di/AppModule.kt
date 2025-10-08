package com.ichin23.salbum.di

import com.ichin23.salbum.data.api.ApiSalbumService
import com.ichin23.salbum.domain.repository.AlbumRepository
import com.ichin23.salbum.domain.repository.RatingsRepository
import com.ichin23.salbum.domain.repository.UserRepository
import com.ichin23.salbum.domain.repositoryImpl.AlbumRepositoryImpl
import com.ichin23.salbum.domain.repositoryImpl.RatingsRepositoryImpl
import com.ichin23.salbum.domain.repositoryImpl.UserRepositoryImpl
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
    fun provideAlbumRepository(apiSalbumService: ApiSalbumService): AlbumRepository{
        return AlbumRepositoryImpl(apiSalbumService)
    }

    @Provides
    @Singleton
    fun provideRatingsRepository(apiSalbumService: ApiSalbumService): RatingsRepository{
        return RatingsRepositoryImpl(apiSalbumService);
    }

    @Provides
    @Singleton
    fun provideUsersRepository(userStateDataStore: androidx.datastore.core.DataStore<com.ichin23.salbum.core.datastore.UserStateOuterClass.UserState>): UserRepository{
        return UserRepositoryImpl(userStateDataStore);
    }
}