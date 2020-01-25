package com.gladly.samplechatapp

import android.content.Intent
import android.content.Intent.*
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val thread = object : Thread() {
            override fun run() {
                try {
                    super.run()

                    // This is a placeholder of loading stuff...
                    sleep(3000)

                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                } finally {
                    Intent(applicationContext, MainActivity::class.java).let { intent ->
                        intent.flags = FLAG_ACTIVITY_NEW_TASK and FLAG_ACTIVITY_CLEAR_TASK

                        startActivity(intent)
                    }
                }
            }
        }

        thread.start()
    }

    companion object {
        const val TAG = "SplashActivity"
    }
}