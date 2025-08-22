package com.ichin23.salbum.data.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModules {

    @Provides
    fun provideApiMusicBrainzService(@MusicBrainzApi retrofit: Retrofit): APIMusicBrainzService =
        retrofit.create(APIMusicBrainzService::class.java)

    @Provides
    fun provideApiImagesService(@ImagesBrainzAPI retrofit: Retrofit): APIReleasesImagesService =
        retrofit.create(APIReleasesImagesService::class.java)
}