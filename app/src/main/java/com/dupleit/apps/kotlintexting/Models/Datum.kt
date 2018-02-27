package com.dupleit.apps.kotlintexting.Models

import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Datum : RealmObject() {

    @PrimaryKey
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

   // var editedText:Spanned? = null

}
