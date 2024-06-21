package com.prativa.analyticssdk.database

import com.prativa.analyticssdk.database.model.AnalyticsUser
import com.prativa.analyticssdk.eventmanager.DemoEvent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.migration.AutomaticSchemaMigration

object DataBaseHelper {
    val realm: Realm by lazy {
        val configuration = RealmConfiguration.Builder(
            schema = setOf(
                AnalyticsUser::class
            )
        ).schemaVersion(34)
            .migration(AutomaticSchemaMigration {})
            .build()
        Realm.open(configuration)
    }

    suspend fun insertEvent(event: DemoEvent) {
        realm.write {
            val analyticsUser = AnalyticsUser().apply {
                id = event.userId
                timeStamp = event.timeStamp
                eventName = event.eventName
                properties = event.getPropertiesInJson()

            }
            copyToRealm(analyticsUser, UpdatePolicy.ALL)

        }
    }

    suspend fun hasEventData() : Boolean?{
        return !realm.query(AnalyticsUser::class).find().isNullOrEmpty()

    }

    suspend fun getAllEvents() : List<AnalyticsUser>{
        return if (hasEventData() == true){
            realm.query(AnalyticsUser::class).find()
        }else
            listOf()
    }


}