package com.prativa.analyticssdk

import org.json.JSONObject

public class AnalyticsDemoSDK(token: String, trackAutomaticEvents: Boolean) {
    private val eventTimings = mutableMapOf<String, Long>()

    companion object {
        private var INSTANCE: AnalyticsDemoSDK? = null
        fun getInstance(token: String, trackAutomaticEvents: Boolean): AnalyticsDemoSDK {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let { return it }
                val demoSDK = AnalyticsDemoSDK(token, trackAutomaticEvents)
                INSTANCE = demoSDK
                demoSDK
            }
        }
    }

    fun track(eventName: String, properties: JSONObject?, isAutomaticEvent: Boolean) {

    }


}