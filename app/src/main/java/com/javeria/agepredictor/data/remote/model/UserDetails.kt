package com.javeria.agepredictor.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserDetails {

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("age")
    @Expose
    var age = 0

    @SerializedName("count")
    @Expose
    var count = 0
}