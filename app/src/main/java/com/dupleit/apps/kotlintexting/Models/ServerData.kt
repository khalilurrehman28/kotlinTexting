package com.dupleit.apps.kotlintexting.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject

open class ServerData :RealmObject{

    @SerializedName("status")
    @Expose
    var status: Boolean? = null
    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("data")
    @Expose
    var data: RealmList<Datum>? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param status
     * @param data
     * @param code
     */
    constructor(status: Boolean?, code: String, data: RealmList<Datum>) : super() {
        this.status = status
        this.code = code
        this.data = data
    }

}
