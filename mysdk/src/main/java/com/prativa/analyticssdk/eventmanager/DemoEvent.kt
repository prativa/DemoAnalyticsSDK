package com.prativa.analyticssdk.eventmanager

import android.util.Log
import org.json.JSONArray
import org.json.JSONObject

//userId can be a device id, if user has not set any id, if user has set id, then user id will be the userid
data class DemoEvent(
    val userId: String,
    val eventName: String,
    val timeStamp: Long,
    val properties: MutableMap<String, Any>? = mutableMapOf()
) {
    fun trackProperty(key: String, value: Any) {
        properties?.set(key, value)
        Log.i("Demo SDK Property Check", "Demo Event key : $key value : $value")

    }

    fun getPropertiesInJson(): String? {
        val json = JSONObject()
        if (properties != null) {
            for ((key, value) in properties) {
                if (value is List<*>) {
                    val jsonArray = JSONArray()
                    for (item in value) {
                        if (item is Map<*, *>) {
                            jsonArray.put(JSONObject(item))
                        } else {
                            jsonArray.put(item)
                        }
                    }
                    json.put(key, jsonArray)
                } else {
                    json.put(key, value)
                }

            }

            return json.toString()
        } else
            return null
    }

}