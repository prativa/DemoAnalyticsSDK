package com.prativa.analyticssdk.eventmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.prativa.analyticssdk.database.DataBaseHelper
import com.prativa.analyticssdk.eventmanager.DemoEvent
import kotlinx.coroutines.launch


class AnalyticsViewModel() : ViewModel() {

    fun insertEvent(event: DemoEvent) = viewModelScope.launch{
        DataBaseHelper.insertEvent(event)
    }

    class ViewModeFactor() : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AnalyticsViewModel() as T
        }
    }
}