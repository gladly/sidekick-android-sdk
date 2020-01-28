package com.gladly.samplechatapp

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.gladly.androidchatsdk.Gladly
import com.gladly.samplechatapp.MainActivity.Companion.BROADCAST_UPDATE_UNREAD_COUNT
import com.gladly.samplechatapp.MainActivity.Companion.BROADCAST_UPDATE_UNREAD_COUNT_EXTRA
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Gladly.registerPushToken(this, token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (Gladly.handleMessageReceived(this, remoteMessage, MainActivity.shown)) {
            Log.i(TAG, "Gladly SDK handled this message...")

            // TODO: This is where you would update your store of unread counts or update your UI. This is just an example
            // of broadcasting this to the specified Activity to update it's view.
            Intent().let { intent ->
                intent.putExtra(BROADCAST_UPDATE_UNREAD_COUNT_EXTRA, Gladly.getUnreadCount(this))
                intent.action = BROADCAST_UPDATE_UNREAD_COUNT
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            }

            return
        }

        // TODO: This is where you would handle your other push notifications.
        Log.i(TAG, "Got a non-gladly SDK message. ")
    }

    companion object {
        const val TAG = "FcmMessagingService"
    }
}