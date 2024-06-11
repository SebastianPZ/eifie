package com.mentalhealth.eifie.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class EMessagingService: FirebaseMessagingService() {
    private val TAG = EMessagingService::class.java.simpleName

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }

}