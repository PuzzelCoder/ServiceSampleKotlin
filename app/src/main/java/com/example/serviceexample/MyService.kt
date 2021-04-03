package com.example.serviceexample


/**
 * By default started services are background, meaning that their process won't be given foreground CPU scheduling
 * (unless something else in that process is foreground) and, if the system needs to kill them to reclaim more memory
 * (such as to display a large page in a web browser), they can be killed without too much harm
 */

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast

import java.lang.UnsupportedOperationException
import java.util.*

class MyService: Service() {
    private val TAG:String="Yogesh"+MyService::class.java.simpleName
    private lateinit var handler :Handler
    private lateinit var runnable: Runnable

    override fun onBind(p0: Intent?): IBinder? {
       throw UnsupportedOperationException("Not yet Implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
         Log.d(TAG, "onStartCommand: ")

         runnable = object : Runnable {
            override fun run() {
            Toast.makeText(this@MyService,"Inside Runnable", Toast.LENGTH_SHORT).show()
                showRandomNumber()
            }
        }
        handler= Handler(Looper.getMainLooper())
        handler.postDelayed(runnable, 1000)
        return START_NOT_STICKY
    }

    private fun showRandomNumber(){
        
        val rand = Random()
        val num = rand.nextInt(100)
        Log.d(TAG, "ShowRandomNumber: $num")
       
    }

    override fun stopService(name: Intent?): Boolean {
        Log.d(TAG, "stopService: ")
        return super.stopService(name)
        
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
        handler.removeCallbacks(runnable)

    }
}