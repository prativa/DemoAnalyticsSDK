package com.prativa.analyticssdk.database.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


// User 1-to-1 One Analytics User
// User 1-to-many Events
// Event 1-to-many Properties
class AnalyticsUser : RealmObject {
    var id : String = ""
    var eventName : String = ""
    var timeStamp : Long = 0
    var properties : String? = ""
}


