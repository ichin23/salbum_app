package com.ichin23.salbum.data.api

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MusicBrainzApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImagesBrainzAPI

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SalbumAPI