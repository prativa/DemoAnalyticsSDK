package com.prativa.analyticssdk.session

import android.content.Context
import kotlinx.coroutines.flow.Flow

class SessionManager(val context: Context) {

    suspend fun saveAPIToke(apiToken: String) {
        context.writeString(API_TOKEN, apiToken)
    }

    suspend fun getAPIToken(): Flow<String>? {
        return context.readString(API_TOKEN)
    }

    suspend fun enableSession() {
        context.writeBoolean(SESSION_ENABLED, true)
        isSessionEnabled()
    }

    suspend fun disableSession() {
        context.writeBoolean(SESSION_ENABLED, false)
        isSessionEnabled()
    }

    suspend fun isSessionEnabled(): Flow<Boolean>? {
        return context.readBoolean(SESSION_ENABLED)
    }

    companion object {
        const val API_TOKEN = "token"
        const val SESSION_ENABLED = "session_enabled"
        const val USER_ID = "user_id"
        const val TRACING_ENABLED = "tracing_enabled"
        private var INSTANCE: SessionManager? = null
        fun getInstance(context: Context): SessionManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let { return it }
                val sessionManager = SessionManager(context)
                INSTANCE = sessionManager
                sessionManager
            }
        }
    }
}