package com.prativa.analyticssdk.database

import android.util.Log
import com.prativa.analyticssdk.database.model.Event
import com.prativa.analyticssdk.database.model.User
import com.prativa.analyticssdk.eventmanager.DemoEvent
import com.prativa.analyticssdk.eventmanager.DemoUser
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.migration.AutomaticSchemaMigration
import io.realm.kotlin.query.RealmQuery
import io.realm.kotlin.query.RealmSingleQuery

object DataBaseHelper {
    val realm: Realm by lazy {
        val configuration = RealmConfiguration.Builder(
            schema = setOf(
                Event::class,
                User::class
            )
        ).schemaVersion(101)
            .migration(AutomaticSchemaMigration {})
            .build()
        Realm.open(configuration)
    }

    suspend fun insertEvent(event: DemoEvent) {
        realm.write {
            val eventTrack = Event().apply {
                id = event.userId
                timeStamp = event.timeStamp
                eventName = event.eventName
                properties = event.getPropertiesInJson()


            }
            copyToRealm(eventTrack, UpdatePolicy.ALL)

        }
    }

    suspend fun insertUser(user: DemoUser) {
        realm.write {
            val analyticsUser = User().apply {
                userId = user.userId
                userApiKey = user.userApiKey
                trackingEnabled = user.isSessionEnabled
            }
            copyToRealm(analyticsUser, UpdatePolicy.ALL)

        }
    }

    suspend fun hasEventData() : Boolean?{
        return try {
            !realm.query(Event::class).find().isNullOrEmpty()

        }catch (ex : NoSuchElementException){
            false
        }

    }

    suspend fun getAllEvents(): List<Event> {
        return if (hasEventData() == true){
            realm.query(Event::class).find()
        }else
            listOf()
    }

    suspend fun hasUserData(): Boolean? {
        return try {
            !realm.query(User::class).find().isNullOrEmpty()

        } catch (ex: NoSuchElementException) {
            false
        }

    }

    fun updateSession(isSessionEnabled: Boolean) {
        realm.query(User::class).first().find().also { it ->
            val localUser: User? = it
            if (localUser != null) {
                realm.writeBlocking {
                    findLatest(localUser)?.apply {
                        trackingEnabled = isSessionEnabled

                    }
                }
            }
        }
    }

    suspend fun deleteAllEvents() {
        realm.write {
            val queryEvent: RealmQuery<Event> = this.query(Event::class);
            delete(queryEvent)

        }
    }
    suspend fun deleteUser() {
        realm.write {
            val queryUser: RealmQuery<User> = this.query(User::class);
            delete(queryUser)
        }
    }

    fun isSessionEnabled(): RealmSingleQuery<User> {
        return realm.query(User::class).first()
    }

    fun hasSession(): Boolean {
        return realm.query(User::class).first().find()!!.trackingEnabled
    }

}