package com.example.serviceexample

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    val TAG:String="Yogesh"
   private lateinit var myIntent :Intent
   private lateinit var myBoundIntent :Intent
   private lateinit var myIntentServiceIntent1 :Intent
   private lateinit var myIntentServiceIntent2 :Intent
   private lateinit var myIntentServiceIntent3 :Intent
   private lateinit var myService: Class<MyService>
    private lateinit var myBoundService: Class<MyBoundService>
    private lateinit var myIntentServive: Class<MyIntentService>
    private lateinit var myForegroundServive: Class<MyForegroundService>
    private lateinit var myForegroundServiveIntent: Intent

    private var isBound:Boolean=false
    private lateinit var serviceConnection:ServiceConnection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        setContentView(R.layout.boundservice)
//        setContentView(R.layout.intentservice)
        setContentView(R.layout.foregroundresource)
        myService = MyService::class.java
        myBoundService = MyBoundService::class.java
        myIntentServive = MyIntentService::class.java
        myForegroundServive = MyForegroundService::class.java


        myIntent= Intent(this,myService)

        myBoundIntent= Intent(this,myBoundService)

        myIntentServiceIntent1= Intent(this,myIntentServive)
        myIntentServiceIntent1.putExtra("item_no",5)
        myIntentServiceIntent2= Intent(this,myIntentServive)
        myIntentServiceIntent2.putExtra("item_no",10)
        myIntentServiceIntent3= Intent(this,myIntentServive)
        myIntentServiceIntent3.putExtra("item_no",15)

        myForegroundServiveIntent= Intent(this,myForegroundServive)
        myForegroundServiveIntent.putExtra("notfication_name","YoYoYo")

        serviceConnection = object:ServiceConnection{
            override fun onServiceConnected(p0: ComponentName?, binder: IBinder?) {
                var myBinder = binder as MyBoundService.LocalBinder
                var service:MyBoundService = myBinder.getService()
                isBound=true
            }

            override fun onServiceDisconnected(p0: ComponentName?) {
                isBound=false
                Log.d(TAG, "onBoundServiceDisconnected: ")
            }
        }
    }

    fun startMyService(view: View) {
        if(isServiceRunning(myService)){
            Toast.makeText(this@MainActivity,"Service Already Running",Toast.LENGTH_SHORT).show()
        }
        else{
            startService(myIntent)
            Toast.makeText(this@MainActivity,"Service Started",Toast.LENGTH_SHORT).show()
        }

    }
    fun showStats(view: View) {
        if (isServiceRunning(myService)) {
            Toast.makeText(this@MainActivity,"Service is Running",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity,"Service is stopped.",Toast.LENGTH_SHORT).show()
        }
        if (isServiceRunning(myBoundService)) {
            Toast.makeText(this@MainActivity,"Bound Service is Running",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity,"Bound Service is stopped.",Toast.LENGTH_SHORT).show()
        }
        if (isServiceRunning(myIntentServive)) {
            Toast.makeText(this@MainActivity,"Intent Service is Running",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity,"Intent Service is stopped.",Toast.LENGTH_SHORT).show()
        }
        if (isServiceRunning(myForegroundServive)) {
            Toast.makeText(this@MainActivity,"ForeGround Service is Running",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity,"ForeGround Service is stopped.",Toast.LENGTH_SHORT).show()
        }
    }

    fun startIntentService(view:View){
        if(isServiceRunning(myIntentServive)){
            Toast.makeText(this@MainActivity,"Service Already Running",Toast.LENGTH_SHORT).show()
        }
        else{
            MyIntentService.enqueueWork(this,  myIntentServiceIntent1)
            SystemClock.sleep(2000)
            MyIntentService.enqueueWork(this,  myIntentServiceIntent2)
            SystemClock.sleep(2000)
            MyIntentService.enqueueWork(this,  myIntentServiceIntent3)
            Toast.makeText(this@MainActivity,"Intent Service Started",Toast.LENGTH_SHORT).show()
        }


    }

    fun stopMyService(view: View) {
        if (isServiceRunning(myService)) {
            stopService(myIntent)
        } else {
            Toast.makeText(this@MainActivity,"Service Already Stopped",Toast.LENGTH_SHORT).show()
        }
    }


    fun isServiceRunning(myService: Class<*>):Boolean{
        val activityManager=getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        // Loop through the running services

        for(service in activityManager.getRunningServices(Int.MAX_VALUE)){
        if(myService.name == service.service.className )
            return true
        }
        return false;
    }

    fun startBoundService(view: View){
        Log.d(TAG, "startBoundService:Clicked")
        if (isServiceRunning(myBoundService)) {

            Toast.makeText(this@MainActivity,"BoundService Already Started",Toast.LENGTH_SHORT).show()

        } else {
            bindService(myBoundIntent,serviceConnection,Context.BIND_AUTO_CREATE)
            Toast.makeText(this@MainActivity,"Bound Service Started",Toast.LENGTH_SHORT).show()
        }

    }
    fun stopBoundService(view:View){
        if (isServiceRunning(myBoundService)) {
            unbindService(serviceConnection)
        } else {
            Toast.makeText(this@MainActivity,"BoundService Already Stopped",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        val view=View(this)
        stopMyService(view)
       stopBoundService(view)
        super.onDestroy()
    }

    fun StopForeGroundService(view: View) {
        if (isServiceRunning(myForegroundServive)) {

            val stopIntent = Intent(this@MainActivity, MyForegroundService::class.java)
            stopService(stopIntent)
        } else {
            Toast.makeText(this@MainActivity,"ForeGroundService Already Stopped",Toast.LENGTH_SHORT).show()
        }
    }
    fun StartForeGroundService(view: View) {
        Log.d(TAG, "StartForeGroundService:Clicked")
        if (isServiceRunning(myForegroundServive)) {
            Toast.makeText(this@MainActivity,"ForeGroundService Already Started",Toast.LENGTH_SHORT).show()

        } else {
            ContextCompat.startForegroundService(this@MainActivity,myForegroundServiveIntent)
            Toast.makeText(this@MainActivity,"ForeGround Service Started",Toast.LENGTH_SHORT).show()
        }

    }

}