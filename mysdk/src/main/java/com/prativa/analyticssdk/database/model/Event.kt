package com.prativa.analyticssdk.database.model

import io.realm.kotlin.types.RealmObject


// User 1-to-1 One Analytics User
// User 1-to-many Events
// Event 1-to-many Properties
class Event : RealmObject {
    var id : String = ""
    var eventName : String = ""
    var timeStamp : Long = 0
    var properties : String? = ""
}


