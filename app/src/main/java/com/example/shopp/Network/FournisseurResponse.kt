package com.example.shopp.Network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FournisseurResponse {


    @SerializedName("_id")
    @Expose
    internal var id: String? = null

    @SerializedName("fullName")
    @Expose
    internal var fullName: String? = null

    @SerializedName("adresse")
    @Expose
    internal var adresse: String? = null

    @SerializedName("secteur")
    @Expose
    internal var secteur: String? = null

    @SerializedName("numTel")
    @Expose
    internal var numTel: Int? = null


    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getFullName(): String? {
        return fullName
    }
    fun setFullName(fullName: String?) {
        this.fullName = fullName
    }

    fun getAdresse(): String? {
        return adresse
    }
    fun setAdresse(adresse: String?) {
        this.adresse = adresse
    }


    fun getSecteur(): String? {
        return secteur
    }
    fun setSecteur(secteur: String?) {
       this.secteur = secteur
    }

    fun getNumTel(): Int? {
        return numTel
    }
    fun setNumTel(numTel: Int?) {
        this.numTel = numTel
    }


}