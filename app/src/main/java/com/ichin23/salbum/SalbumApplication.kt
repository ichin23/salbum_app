package com.ichin23.salbum

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SalbumApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}