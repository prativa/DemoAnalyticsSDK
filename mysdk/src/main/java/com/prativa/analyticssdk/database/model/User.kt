package com.prativa.analyticssdk.database.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class User : RealmObject {
    var userId: String = ""
    @PrimaryKey
    var userApiKey: String = ""
    var sessionEnabled: Boolean = false
}