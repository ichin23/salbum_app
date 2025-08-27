package com.ichin23.salbum.core.utils

import com.google.protobuf.Timestamp
import java.time.LocalDateTime
import java.time.ZoneOffset

fun LocalDateTime.toProtoTimestamp(): Timestamp {
    val epochSecond = this.toEpochSecond(ZoneOffset.UTC)
    val nano = this.nano
    return Timestamp.newBuilder()
        .setSeconds(epochSecond)
        .setNanos(nano)
        .build()
}