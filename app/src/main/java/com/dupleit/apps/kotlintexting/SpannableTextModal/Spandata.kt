package com.dupleit.apps.kotlintexting.SpannableTextModal

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Spandata: RealmObject() {

    @PrimaryKey
    var id: Int? = null
    var startIndex: Int? = null
    var endIndex: Int? = null
    var dataID: String? = null

    /**
     * No args constructor for use in serialization
     *
     *//*
    constructor() {}

    *//**
     *
     * @param id
     * @param startIndex
     * @param dataID
     * @param endIndex
     *//*
    constructor(id: Int?, startIndex: Int?, endIndex: Int?, dataID: String) : super() {
        this.id = id
        this.startIndex = startIndex
        this.endIndex = endIndex
        this.dataID = dataID
    }*/

}