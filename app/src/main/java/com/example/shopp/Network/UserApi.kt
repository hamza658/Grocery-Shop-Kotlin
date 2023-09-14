package com.example.shopp.Network

import com.example.shopp.Data.Fournisseur
import com.example.shopp.Data.Loginresponse
import com.example.shopp.Data.Produit
import com.example.shopp.Data.Promotion
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.util.*


interface UserApi {
    //Login Méthode 1
    @POST("users/signIn")
    fun login1(
        @Body body: JsonObject
    ): Call<JsonObject>
    // A suspending function is simply a function that can be paused and resumed at a later time. They can execute a long running operation and wait for it to complete without blocking.
    //Login Méthode 2
    @POST("users/signIn")
    suspend fun login2(@Body User: Loginresponse): Response<Loginresponse>
    //
    @POST("users/signup")
    fun signup(@Body map : HashMap<String, String> ): Call<JsonObject>


    @POST("users/forgetPassword")
    fun SendCodeForgot(@Body map : HashMap<String, String> ): Call<JsonObject>

    @POST("users/VerifCode")
    fun VerifCodeForgot(@Body map : HashMap<String, String> ): Call<JsonObject>

    @POST("users/changePassword")
    fun ChangePasswordForgot(@Body map : HashMap<String, String> ): Call<JsonObject>

    @POST("users/UpdateUser")
    fun EditProfil(@Body map : HashMap<String, String> ): Call<JsonObject>

    @POST("users/UpdatePassword")
    fun UpdatePassword(@Body map : HashMap<String, String> ): Call<JsonObject>

    @POST("fournisseurs/fournisseur")
    fun GetAllfournisseur(): Call<List<Fournisseur>>

    @POST("fournisseurs/addfournisseur")
    suspend fun AddFournisseur(@Body User: Fournisseur): Response<Fournisseur>

    @POST("promotions/promotion")
    fun GetAllPromotion(): Call<List<Promotion>>

    @POST("promotions/addpromotion")
    suspend fun AddPromotion(@Body User: Promotion): Response<Promotion>

    @POST("stocks/stock")
    fun GetAllProduit(): Call<List<Produit>>

    @POST("stocks/UpdateStock")
    suspend fun UpdateProduit(@Body Post: Produit): Response<Produit>


    @Multipart
    @POST("stocks/addStock")
    suspend fun addProduit(
        @Part("type") type: RequestBody,
        @Part("quantite") quantite: RequestBody,
        @Part("prix") prix: RequestBody,
        @Part image: MultipartBody.Part,
    ): Response<Produit>



    @Multipart
    @POST("users/updateavatar")
    fun postImage(
        @Part("email") email:RequestBody,
        @Part photo: MultipartBody.Part,
    ):Call<Loginresponse>

}