package com.example.shopp.Data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Fournisseur(


    @SerializedName("_id")  var id: String,
    @SerializedName("fullName") var fullName: String,
    @SerializedName("adresse") val adresse: String,
    @SerializedName("secteur") var secteur: String,
    @SerializedName("numTel") val numTel: String,

) : Serializable
