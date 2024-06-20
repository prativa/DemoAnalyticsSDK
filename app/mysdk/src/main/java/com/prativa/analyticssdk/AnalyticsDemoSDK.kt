package com.prativa.analyticssdk

import android.content.Context
import com.prativa.analyticssdk.eventmanager.DemoEvent
import com.prativa.analyticssdk.eventmanager.viewmodel.AnalyticsViewModel
import kotlinx.coroutines.coroutineScope

public class AnalyticsDemoSDK(
    val context: Context,
    val userId: String,
    trackAutomaticEvents: Boolean
) {
    private val eventTimings = mutableMapOf<String, Long>()

    val analyticsViewModel = AnalyticsViewModel()
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


    }

    fun trackEvent(eventName: String) {
        val demoEvent = DemoEvent(
            userId = userId,
            eventName = eventName,
            timeStamp = System.currentTimeMillis()
        )
        analyticsViewModel.insertEvent(demoEvent)
    }

    fun listEvent(){
    }


}