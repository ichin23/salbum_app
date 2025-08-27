package com.ichin23.salbum.core.datastore


import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.ichin23.salbum.core.datastore.UserStateOuterClass.UserState
import java.io.InputStream
import java.io.OutputStream

object UserStateSerializer : Serializer<UserState> {
    override val defaultValue: UserState = UserState.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserState {
        try{
            return UserState.parseFrom(input)
        }catch (exception: InvalidProtocolBufferException){
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: UserState, output: OutputStream) = t.writeTo(output)

}

val Context.userStateDataStore: DataStore<UserState> by dataStore(
    fileName = "userState.pb",
    serializer = UserStateSerializer
)