package com.gladly.samplechatapp

import android.content.Context
import android.graphics.Color
import android.util.Log

import com.gladly.androidchatsdk.Gladly
import com.gladly.androidchatsdk.models.*
import java.lang.Exception

object GladlyChatUtils {
    /**
     * Application ID given to identify your account in Gladly
     */
    private const val APP_ID = "some-app-id"

    /**
     * Specify what environment to connect to, either "STAGING" or "PROD"
     */
    private const val ENV = "STAGING"

    fun initialize(context: Context) {
        init(context)
    }

    fun loginAs(context: Context, email: String, name: String) {
        logoutIfNeeded(context, email, name)

        initUser(context, email, name)

        saveLogin(context, email, name)
    }

    fun showChat(context: Context) {
        Gladly.showChat(context)
    }

    fun openNotificationChannelSettings(context: Context) =
        Gladly.openNotificationChannelSettings(context)


    //////////////////////////////////////////////////////////////////////////////////////////////

    private fun init(context: Context) {
        val settings = Settings(
            appId = APP_ID,
            environment = ENV,
            uiConfiguration = UIConfiguration(
                timeFormat = "hh:mma",
                headerConfiguration = UIHeaderConfiguration(
                    titleText = "Sample Chat App",
                    backgroundColor = Color.argb(255, 0, 64, 128),
                    statusBarColor = Color.argb(255, 0, 64, 156)
                )
            )
        )

        val eventHandler = object : EventInterface {
            override fun onEvent(event: SidekickEvent) {
                Log.d(TAG, "EventInterface: onEvent: $event")
            }

            override fun onMessageReceived(text: String) {
                Log.d(TAG, "EventInterface: onMessageReceived: $text")

                val unreadCount = Gladly.getUnreadCount(context)
                Log.d(TAG, "Unread Count: $unreadCount")

                // Here's where you might broadcast a UI update to your activity to display an updated unread messages count.
                // Remember: this is not in the main UI thread.
            }

            override fun onError(error: SidekickError) {
                Log.e(TAG, "EventInterface: onError: $error")
            }
        }

        try {
            Gladly.initialize(context, settings, eventHandler)

            Log.d(TAG, "Gladly.initialize() Okay!")
        } catch (e: Exception) {
            Log.e(TAG, "Gladly.initialize() Failed! $e")
        }
    }

    private fun initUser(context: Context, email: String, name: String) {
        val user = User(
            email = email,
            name = name
        )

        try {
            Gladly.setUser(context, user)
            Log.d(TAG, "Gladly.setUser() Okay!")
        } catch (e: Exception) {
            Log.e(TAG, "Gladly.setUser() Failed!")
        }
    }

    private fun logoutIfNeeded(context: Context, email: String, name: String) {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).apply {
            val currEmail = getString(SHARED_PREF_NAME_CURRENT_EMAIL_LOGIN, "")
            val currName = getString(SHARED_PREF_NAME_CURRENT_NAME_LOGIN, "")

            if (currEmail != email || currName != name) {
                Log.d(TAG, "Logging in as a different user, logging old user out...")

                Gladly.logout(context)
            }
        }
    }

    private fun saveLogin(context: Context, email: String, name: String) {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit().apply {
            putString(SHARED_PREF_NAME_CURRENT_EMAIL_LOGIN, email)
            putString(SHARED_PREF_NAME_CURRENT_NAME_LOGIN, name)
            apply()
        }
    }

    fun isUserSet(context: Context): Boolean {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).apply {
            val currEmail = getString(SHARED_PREF_NAME_CURRENT_EMAIL_LOGIN, "")
            val currName = getString(SHARED_PREF_NAME_CURRENT_NAME_LOGIN, "")

            if (currEmail == "" || currName == "") {
                return false
            }
            return true
        }
    }

    private const val SHARED_PREF_NAME = "gladly.chat.utils.shared.prefs"
    private const val SHARED_PREF_NAME_CURRENT_EMAIL_LOGIN = "gladly.chat.utils.shared.prefs.email"
    private const val SHARED_PREF_NAME_CURRENT_NAME_LOGIN = "gladly.chat.utils.shared.prefs.name"

    private const val TAG = "GladlyChatUtils"
}