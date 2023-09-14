package com.example.shopp.Data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Promotion(

    @SerializedName("_id")  var id: String,
    @SerializedName("prix_promo") var prix_promo: String,
    @SerializedName("produit") var produit: String,
    @SerializedName("duree") val duree: String,

    ) : Serializable
