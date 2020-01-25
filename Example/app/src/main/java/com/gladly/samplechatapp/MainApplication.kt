package com.gladly.samplechatapp

import android.app.Application
import android.util.Log
import com.gladly.androidchatsdk.Gladly

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate")

        Log.d(TAG, "Initializing Chat...")

        GladlyChatUtils.initialize(applicationContext)

        Log.d(TAG, "Done initializing chat...")
    }

    override fun onTrimMemory(level: Int) {
        // Invoke Gladly.destroy to clean up memory depending on if/when the OS is requesting you to do so.
        when (level) {
            TRIM_MEMORY_RUNNING_LOW -> {
                Gladly.destroy(applicationContext)
            }

            else -> {
                Log.d(TAG, "Initializing Chat...")

            }
        }

        super.onTrimMemory(level)
    }

    companion object {
        const val TAG = "MainApplication"
    }
}