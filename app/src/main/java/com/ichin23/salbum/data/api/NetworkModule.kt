package com.ichin23.salbum.data.api

import androidx.datastore.core.DataStore
import com.google.gson.GsonBuilder
import com.ichin23.salbum.core.datastore.UserStateOuterClass.UserState
import com.ichin23.salbum.core.utils.LocalDateTimeDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val MUSICBRAINZ_API_URL = "https://musicbrainz.org/ws/2/";
    private const val IMAGES_API_URL = "https://coverartarchive.org/";
    private const val SALBUM_API_URL = "http://192.168.1.2:8080/";
    //private const val SALBUM_API_URL = "http://200.128.157.75:8080/";

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        userStateDataStore: DataStore<UserState>
    ): ApiSalbumInterceptor = ApiSalbumInterceptor(userStateDataStore)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder().addInterceptor{chain ->
            val request = chain.request().newBuilder()
                .header("User-Agent", "Salbum/1.0")
                .build()
            chain.proceed(request)
        }.build()
    }

    @SalbumAPI
    @Provides
    @Singleton
    fun provideOkHttpClientSalbum(authInterceptor: ApiSalbumInterceptor): OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @MusicBrainzApi
    @Provides
    @Singleton
    fun provideMusicBrainzRetrofit(okHttpClient: OkHttpClient): Retrofit{
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())

        val customGson = gsonBuilder.create()

        return Retrofit.Builder()
            .baseUrl(MUSICBRAINZ_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(customGson))
            .build()
    }

    @ImagesBrainzAPI
    @Provides
    @Singleton
    fun provideImagesRetrofit(okHttpClient: OkHttpClient): Retrofit{
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())

        val customGson = gsonBuilder.create()

        return Retrofit.Builder()
            .baseUrl(IMAGES_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(customGson))
            .build()
    }

    @SalbumAPI
    @Provides
    @Singleton
    fun provideSalbumRetrofit(@SalbumAPI okHttpClient: OkHttpClient): Retrofit{
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())

        val customGson = gsonBuilder.create()

        return Retrofit.Builder()
            .baseUrl(SALBUM_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(customGson))
            .build()
    }
}