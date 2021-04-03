package com.example.serviceexample

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.util.Log
import android.widget.Toast


class MyBoundService:Service() {
    private val TAG:String="Yogesh"+MyBoundService::class.java.simpleName
    private val binder= LocalBinder()
    override fun onBind(p0: Intent?): Binder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this@MyBoundService,"BoundService doing work",Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onStartCommand:Bound ")
        return START_NOT_STICKY
    }
 inner class LocalBinder:Binder(){
         fun getService(): MyBoundService {
            return this@MyBoundService
        }
    }
}