package com.prativa.analyticssdk

import android.content.Context
import android.util.Log
import com.prativa.analyticssdk.database.DataBaseHelper
import com.prativa.analyticssdk.eventmanager.DemoEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


public class AnalyticsDemoSDK(
    val context: Context,
    val userId: String,
    trackAutomaticEvents: Boolean
) {
    private val eventTimings = mutableMapOf<String, Long>()
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    companion object {
        private var INSTANCE: AnalyticsDemoSDK? = null
        fun getInstance(
            context: Context,
            userId: String,
            trackAutomaticEvents: Boolean
        ): AnalyticsDemoSDK {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let { return it }
                val demoSDK = AnalyticsDemoSDK(context, userId, trackAutomaticEvents)
                INSTANCE = demoSDK
                demoSDK
            }
        }
    }

    fun trackEventWithProperties(eventName: String, properties: Map<String, Any>) {
        val demoEvent = DemoEvent(
            userId = userId,
            eventName = eventName,
            timeStamp = System.currentTimeMillis()
        )
        properties.forEach { (key, value) ->
            demoEvent.trackProperty(key, value)
        }

        scope.launch {
            DataBaseHelper.insertEvent(demoEvent)
        }



    }

    fun trackEvent(eventName: String) {
        val demoEvent = DemoEvent(
            userId = userId,
            eventName = eventName,
            timeStamp = System.currentTimeMillis()
        )
        scope.launch {
            DataBaseHelper.insertEvent(demoEvent)
        }
    }

    fun listEvent(){
        scope.launch {
            if(DataBaseHelper.hasEventData() == true){
                DataBaseHelper.getAllEvents().forEach {
                    Log.i("Demo SDK","Event Name : ${it.eventName} ")
                }

            }

        }
    }


}