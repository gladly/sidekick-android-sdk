package com.gladly.samplechatapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.gladly.androidchatsdk.Gladly
import kotlinx.android.synthetic.main.activity_main.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openChat.setOnClickListener {
            Log.d(TAG, "Showing Chat...")

            GladlyChatUtils.loginAs(this,
                "john.connor@future.com",
                "John Connor")

            GladlyChatUtils.showChat(this)
        }

        openChatAsNewUser.setOnClickListener {
            Log.d(TAG, "Showing Chat as new user...")

            val layout = layoutInflater.inflate(R.layout.dialog_set_user, null)

            val b = AlertDialog.Builder(this)
                .setView(layout)
                .setPositiveButton("LOGIN") { _, _ ->
                    val email = layout.findViewById<EditText>(R.id.setUserEmail).text.toString()
                    val name = layout.findViewById<EditText>(R.id.setUserName).text.toString()

                    Toast.makeText(this, "Login As: $email $name", Toast.LENGTH_LONG).show()

                    GladlyChatUtils.loginAs(this, email, name)

                    GladlyChatUtils.showChat(this)
                }
                .create()

            b.show()
        }

        openNotificationSettings.setOnClickListener {
            Log.d(TAG, "Open notification settings...")

            GladlyChatUtils.openNotificationChannelSettings(this)
        }
    }

    override fun onResume() {
        super.onResume()

        shown = true

        Gladly.clearPushNotifications(this)

        LocalBroadcastManager.getInstance(this).registerReceiver(broadCastReceiver, IntentFilter(BROADCAST_UPDATE_UNREAD_COUNT))

        handleUpdateUnreadCount(Gladly.getUnreadCount(this))
    }

    override fun onPause() {
        super.onPause()

        shown = false

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadCastReceiver)
    }

    fun handleUpdateUnreadCount(unreadCount: Int) {
        runOnUiThread {
            unreadChatsTextView.text = "Unread Chats: $unreadCount"
        }
    }

    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            when (intent?.action) {
                BROADCAST_UPDATE_UNREAD_COUNT -> handleUpdateUnreadCount(intent.extras!!.getInt(BROADCAST_UPDATE_UNREAD_COUNT_EXTRA))
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"

        const val BROADCAST_UPDATE_UNREAD_COUNT = "com.gladly.samplechatapp.update.unread.count"
        const val BROADCAST_UPDATE_UNREAD_COUNT_EXTRA = "com.gladly.samplechatapp.update.unread.count.extra"

        var shown: Boolean = false
    }
}
