package com.example.shopp.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("id")  var id: String,
    @SerializedName("firstName") var firstName: String,
    @SerializedName("email") var userEmail: String,
    @SerializedName("token") val token:String,
) :  Serializable


