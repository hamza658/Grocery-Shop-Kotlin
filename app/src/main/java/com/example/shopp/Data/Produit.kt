package com.example.shopp.Data

class Produit {
    internal var id: String? = null
    internal var type: String? = null
    internal var quantite: String? = null
    internal var prix: String? = null
    internal var image: String? = null



    // Getter ID
    fun getID(): String? {
        return id
    }
    // Setter ID
    fun setID(newID: String?) {
        id = newID
    }


    // Getter type
    fun getType(): String? {
        return type
    }
    // Setter type
    fun setType(newType: String?) {
        type = newType
    }

    // Getter quantite
    fun getQuantite(): String? {
        return quantite
    }
    // Setter quantite
    fun setQuantite(newQuantite: String?) {
        quantite = newQuantite
    }

    // Getter prix
    fun getPrix(): String? {
        return prix
    }
    // Setter Password
    fun setPrix(newPrix: String?) {
        prix = newPrix
    }
    // Getter avatar
    fun getPhoto(): String? {
        return image
    }
    // Setter avatar
    fun setPhoto(newPhoto: String?) {
        image = newPhoto
    }




}