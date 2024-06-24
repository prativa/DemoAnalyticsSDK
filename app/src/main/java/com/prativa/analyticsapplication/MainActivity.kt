package com.prativa.analyticsapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.prativa.analyticssdk.AnalyticsDemoSDK

class MainActivity : AppCompatActivity() {
    val dummyApiToken = "123"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val analytics = AnalyticsDemoSDK.getInstance(this, "1", true, dummyApiToken)

        analytics.startSession()
        analytics.trackEvent("Login");
        analytics.trackEvent("SignUp")
        val properties = mutableMapOf<String, Any>()
        properties["device"] = "Android"
        properties["version"] = "1.0.0"
        properties["name"] = "Demo User"
        analytics.trackEventWithProperties("SignUp", properties)
        analytics.listEvent()
        analytics.stopSession()
        analytics.trackEvent("Logout")
        analytics.clearEventData()





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}