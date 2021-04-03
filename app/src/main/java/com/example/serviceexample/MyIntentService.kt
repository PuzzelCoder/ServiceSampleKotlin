package com.example.serviceexample

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.core.app.JobIntentService


class MyIntentService:JobIntentService() {

    private val TAG:String="Yogesh"+MyIntentService::class.java.simpleName

    /**
     * Convenience method for enqueuing work in to this service.
     */
    companion object{
        fun enqueueWork(context: Context, work: Intent) {
            val JOB_ID:Int= 1000
            enqueueWork(context, MyIntentService::class.java, JOB_ID, work)
        }

    }
    override fun onHandleWork(intent: Intent) {
      val num:Int= intent.getIntExtra("item_no",10)
        Log.d(TAG, "onHandleWork: ")
        for(i in 0 until num){
            Log.d(TAG, "onHandleWork: $i")
            SystemClock.sleep(500)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        showToast("All Work Completed")
        Log.d(TAG, "onDestroy: ")
    }

    val handler =Handler(Looper.getMainLooper())

    fun showToast(msg:String){
        val runnable= object :Runnable{
            override fun run() {
                Toast.makeText(this@MyIntentService, msg,Toast.LENGTH_SHORT).show()
            }
        }
        handler.post(runnable)

    }
}