package com.dupleit.apps.kotlintexting.Models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Datum {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("class")
    @Expose
    var class_: String? = null
    @SerializedName("text")
    @Expose
    var text: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param id
     * @param text
     * @param _class
     * @param name
     */
    constructor(id: String, name: String, _class: String, text: String) : super() {
        this.id = id
        this.name = name
        this.class_ = _class
        this.text = text
    }

}
