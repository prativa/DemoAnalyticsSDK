package com.prativa.analyticssdk

import android.content.Context
import android.util.Log
import com.prativa.analyticssdk.database.DataBaseHelper
import com.prativa.analyticssdk.eventmanager.DemoEvent
import com.prativa.analyticssdk.eventmanager.DemoUser
import com.prativa.analyticssdk.session.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


public class AnalyticsDemoSDK(
    val context: Context,
    val userId: String,
    trackAutomaticEvents: Boolean,
    val apiToken: String
) {
    private val eventTimings = mutableMapOf<String, Long>()
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private val sessionManager = SessionManager.getInstance(context)

    init {
        scope.launch {
            if (DataBaseHelper.hasUserData() == false) {
                val demoUser = DemoUser(userId, apiToken, trackAutomaticEvents)
                DataBaseHelper.insertUser(demoUser)
            }
        }
    }

    companion object {
        private var INSTANCE: AnalyticsDemoSDK? = null
        fun getInstance(
            context: Context,
            userId: String,
            trackAutomaticEvents: Boolean,
            apiToken: String
        ): AnalyticsDemoSDK {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let { return it }
                val demoSDK = AnalyticsDemoSDK(context, userId, trackAutomaticEvents, apiToken)
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
            Log.i("Demo SDK Property Check", "key : $key value : $value")
            demoEvent.trackProperty(key, value)
        }

        scope.launch {
            val sessionEnabled = DataBaseHelper.hasSession()

            Log.i("Demo SDK", "Session Enabled : $sessionEnabled .eventName $eventName} ")
            if (sessionEnabled) {
                DataBaseHelper.insertEvent(demoEvent)
            }
        }



    }

    fun trackEvent(eventName: String) {

        val demoEvent = DemoEvent(
            userId = userId,
            eventName = eventName,
            timeStamp = System.currentTimeMillis()
        )


        scope.launch {

            val sessionEnabled = DataBaseHelper.hasSession()

            Log.i("Demo SDK", "Session Enabled : $sessionEnabled .eventName $eventName} ")
            if (sessionEnabled) {
                DataBaseHelper.insertEvent(demoEvent)
            }
        }




    }

    fun clearEventData(){
        scope.launch {
            DataBaseHelper.deleteAllEvents()
        }
    }

    fun clearUserData(){
        scope.launch {
            DataBaseHelper.deleteUser()
        }
    }

    fun listEvent(){
        scope.launch {

            if (DataBaseHelper.hasEventData() == true) {
                DataBaseHelper.getAllEvents().forEach {
                    Log.i("Demo SDK","Event Name : ${it.eventName} Property ${it.properties}")

                }

            }

        }
    }

    fun startSession() {
            DataBaseHelper.updateSession(true)

    }

    fun stopSession() {
            DataBaseHelper.updateSession(false)

    }




}