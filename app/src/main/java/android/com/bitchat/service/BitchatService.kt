package android.com.bitchat.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class BitchatService : Service() {

    override fun onBind(intent: Intent): IBinder? {
      return null
    }

    override fun onCreate() {

    }


}
