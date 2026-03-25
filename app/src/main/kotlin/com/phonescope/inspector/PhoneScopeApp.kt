package com.phonescope.inspector

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PhoneScopeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Native engine is loaded lazily via NativeEngine singleton
    }
}
