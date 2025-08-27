package com.ichin23.salbum.core.datastore


import com.ichin23.salbum.core.datastore.UserStateOuterClass.UserState
import android.content.Context
import androidx.datastore.core.DataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(dagger.hilt.components.SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideUserStateDataStore(
        @ApplicationContext context: Context
    ): DataStore<UserState>{
        return context.userStateDataStore
    }
}