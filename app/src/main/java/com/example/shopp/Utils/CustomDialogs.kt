package com.example.shopp.Utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.example.shopp.R
import com.example.shopp.Data.Fournisseur
import com.example.shopp.Data.Produit
import com.example.shopp.Data.Promotion
import com.example.shopp.Network.UserApi
import com.example.shopp.Network.retrofit
import com.example.shopp.Views.Fragement.PromotionFragment
import com.google.android.material.internal.ContextUtils
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import java.util.*

class CustomDialogs(val talk: talk? = null ) {
    private lateinit var DialogNameNeedy: TextInputLayout
    private lateinit var DialogLocationNeedy: TextInputEditText
    private lateinit var DialogSecteurNeedy: TextInputLayout
    private lateinit var DialogPhoneNeedy: TextInputLayout




    private lateinit var DialogPrix: TextInputLayout
    private lateinit var Dialogduaration: TextInputLayout
    private lateinit var Dialogproduct: TextInputLayout


    private lateinit var DialogPrice: TextInputEditText
    private lateinit var Dialogquantite: TextInputEditText





    var mMediaPlayer: MediaPlayer? = null
    fun SoundNotification(context: Context?) {
        mMediaPlayer = MediaPlayer.create(context, R.raw.soundialog)
        mMediaPlayer!!.start()
    }



    fun ShowDetailsNeedy(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;
        dialog.show()
       // val sound = PlayMusic()
        //sound.SoundNotification(context!!)

        /////
        val sharedPreference : SharedPreferences = context.getSharedPreferences("azz", Context.MODE_PRIVATE)

        val id = sharedPreference.getString("id", null)
        val fullName = sharedPreference.getString("fullName", null)
        val adresse = sharedPreference.getString("adresse", null)
        val secteur = sharedPreference.getString("secteur", null)
        val numTel = sharedPreference.getString("numTel", null)




        /////
        val DetailsBlood = view.findViewById<TextView>(R.id.DetailsBlood) as? TextView
        val DetailsFullName = view.findViewById<TextView>(R.id.DetailsFullName) as? TextView
        val DetailsHospitalLocation = view.findViewById<TextView>(R.id.DetailsHospitalLocation) as? TextView
        val DetailsPhoneNeedy = view.findViewById<TextView>(R.id.DetailsPhoneNeedy) as? TextView
        /////
        DetailsBlood?.text=numTel
        DetailsFullName?.text=fullName
        DetailsHospitalLocation?.text=adresse
        DetailsPhoneNeedy?.text=secteur
        /////
        val DetailsCallNeedy = view.findViewById<TextView>(R.id.DetailsCallNeedy) as? TextView
        val DetailsDeleteNeedy = view.findViewById<TextView>(R.id.DetailsDeleteNeedy) as? TextView
        val CloseDetailsNeed = view.findViewById<ImageView>(R.id.CloseDetailsNeed) as? ImageView
        /////


        /////
        CloseDetailsNeed?.setOnClickListener{
            dialog.dismiss()
        }
        DetailsCallNeedy?.setOnClickListener{
            try {
                val intent = Intent(Intent.ACTION_DIAL)
               intent.data = Uri.parse("tel:$numTel")
                context.startActivity(intent)
            }catch (e: Exception) {
                println(e.printStackTrace())
            }
        }
        DetailsDeleteNeedy?.setOnClickListener{
            dialog.cancel()
            dialog.dismiss()
            val factory = LayoutInflater.from(context)
           // val view: View = factory.inflate(R.layout.optionsharepopup, null)
            val msg = CustomDialogs()
           // msg.ShareToHelp(context, view)

        }

    }






    private fun gettextwathcerAddPromo() {
        DialogPrix?.editText?.addTextChangedListener(PrixTextWatcher)
        Dialogproduct?.editText?.addTextChangedListener(ProductTextWatcher)
        Dialogduaration?.editText?.addTextChangedListener(DuarationTextWatcher)

    }
    private val PrixTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validatePrix() }
    }
    private val ProductTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateProduct() }
    }
    private val DuarationTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateDuration() }
    }



    private fun validatePrix(): Boolean {
        if (DialogPrix?.editText?.text!!.isEmpty()) {
            DialogPrix.setError("Must Not be Empty !")
            return false
        } else {
            DialogPrix.setError(null)
            return true
        }
        return true
    }
    private fun validateProduct(): Boolean {
        if (Dialogproduct?.editText?.text!!.isEmpty()) {
            Dialogproduct.setError("Must Not be Empty !")
            return false
        } else {
            Dialogproduct.setError(null)
            return true
        }
        return true
    }
    private fun validateDuration(): Boolean {
        if (Dialogduaration?.editText?.text!!.isEmpty()) {
            Dialogduaration.setError("Must Not be Empty !")
            return false
        } else {
            Dialogduaration.setError(null)
            return true
        }
        return true
    }








    private fun gettextwathcerAddNeedy() {
        DialogNameNeedy?.editText?.addTextChangedListener(NameTextWatcher)
        DialogLocationNeedy.addTextChangedListener(LocationTextWatcher)
        DialogPhoneNeedy?.editText?.addTextChangedListener(PhoneTextWatcher)
        DialogSecteurNeedy?.editText?.addTextChangedListener(SecteurTextWatcher)

    }
    private val SecteurTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateNeedyName() }
    }
    private val NameTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateNeedyName() }
    }
    private val LocationTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateNeedyLocation() }
    }
    private val PhoneTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) { validateNeedyPhone() }
    }
    private fun validateNeedyLocation(): Boolean {
        if (DialogLocationNeedy.text!!.isEmpty()) {
            DialogLocationNeedy.setError("Must Not be Empty !")
            return false
        } else {
            DialogLocationNeedy.setError(null)
            return true
        }
        return true
    }
    private fun validateNeedyName(): Boolean {
        if (DialogNameNeedy?.editText?.text!!.isEmpty()) {
            DialogNameNeedy.setError("Must Not be Empty !")
            return false
        } else {
            DialogNameNeedy.setError(null)
            return true
        }
        return true
    }
    private fun validateNeedySecteur(): Boolean {
        if (DialogSecteurNeedy?.editText?.text!!.isEmpty()) {
            DialogSecteurNeedy.setError("Must Not be Empty !")
            return false
        } else {
            DialogSecteurNeedy.setError(null)
            return true
        }
        return true
    }
    private fun validateNeedyPhone(): Boolean {
        if (DialogPhoneNeedy.editText?.text!!.isEmpty()) {
            DialogPhoneNeedy.setError("Must Not be Empty!")
            return false
        }  else if (DialogPhoneNeedy.editText?.text!!.length < 8) {
            DialogPhoneNeedy.setError("Missing "+(8-DialogPhoneNeedy.editText?.text!!.length)+" numbers")
            return false
        }
        else if (!(DialogPhoneNeedy.editText?.text!!.toString().isPhoneValid())) {
            DialogPhoneNeedy.setError("Malformed Phone Number!")
            return false
        }
        else {
            DialogPhoneNeedy.setError(null)
            return true
        }
        return true
    }

    private fun String.isPhoneValid(): Boolean {
        return !TextUtils.isEmpty(this) && Patterns.PHONE.matcher(this).matches()
    }



    fun ShowDialogAddNeedy(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()



        val BtnSaveAddNeedy = view.findViewById<Button>(R.id.BtnSaveAddNeedy) as? Button
        DialogNameNeedy = view.findViewById(R.id.DialogNameNeedy)
        DialogLocationNeedy = view.findViewById(R.id.DialogLocationNeedy)
        DialogSecteurNeedy = view.findViewById(R.id.DialogSecteurNeedy)
        DialogPhoneNeedy = view.findViewById(R.id.DialogPhoneNeedy)




        /////
        val preferences : SharedPreferences = context.getSharedPreferences("azz", Context.MODE_PRIVATE)
        val MyID = preferences.getString("id", null)
        //
        ////



        ///////
        gettextwathcerAddNeedy()
        BtnSaveAddNeedy?.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("RestrictedApi")
            override fun onClick(view: View?) {
                if (!validateNeedySecteur()
                    or  !validateNeedyName() or !validateNeedyLocation() or !validateNeedyPhone()
                ) {
                    CustomToast(context, "Something is empty!", "RED").show()
                    return
                }
                if(
                    validateNeedyName()&&validateNeedyLocation()&&validateNeedyPhone()
                )
                {

                    val retrofi: Retrofit = retrofit.getInstance()
                    val service: UserApi = retrofi.create(UserApi::class.java)
                    val Needy = Fournisseur(
                        "",
                        DialogNameNeedy?.editText?.text.toString(),
                        DialogLocationNeedy?.text.toString(),
                        DialogSecteurNeedy?.editText?.text.toString(),
                        DialogPhoneNeedy?.editText?.text.toString()
                    )



                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            // Do the POST request and get response
                            val response = service.AddFournisseur(Needy)
                            withContext(Dispatchers.Main) {
                                if (response!!.isSuccessful) {
                                    CustomToast(context, "Added Successfully!","GREEN").show()
                                    talk!!.senddata(response.body()!!)
                                    dialog.dismiss()
                                    println("success")


                                } else {
                                    Log.e("RETROFIT_ERROR", response.code().toString())
                                    println("Message :" + response.errorBody()?.string())
                                    CustomToast(context, "Something Went Wrong!", "RED").show()
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                                dialog.dismiss()
                                CustomToast(context, "Sorry, Our Server Is Down!", "RED").show()
                            })
                        }

                    }
                }

            }
        })

    }






    fun ShowDialogAddPromotion(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()

        val BtnSaveAddPromo = view.findViewById<Button>(R.id.BtnSaveAddPromotion) as? Button
        DialogPrix = view.findViewById(R.id.DialogPrix)
        Dialogduaration = view.findViewById(R.id.Dialogduaration)
        Dialogproduct = view.findViewById(R.id.Dialogproduct)

        /////
        val preferences : SharedPreferences = context.getSharedPreferences("promo", Context.MODE_PRIVATE)
        val MyID = preferences.getString("id", null)
        //
        /////

        ///////
        gettextwathcerAddPromo()
        BtnSaveAddPromo?.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("RestrictedApi")
            override fun onClick(view: View?) {
                if (!validatePrix() or  !validateProduct() or !validateDuration() ) {
                    CustomToast(context, "Something is empty!", "RED").show()
                    return
                }
                if (validatePrix()&&validateProduct()&&validateDuration())  {

                    val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val channelId = "MyApp"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(channelId, "MyApp", NotificationManager.IMPORTANCE_DEFAULT)
                        notificationManager.createNotificationChannel(channel)
                    }




                    val intent = Intent(context, PromotionFragment::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)



                    val notificationBuilder = NotificationCompat.Builder(context, channelId)
                        .setStyle(NotificationCompat.BigTextStyle()
                            .setBigContentTitle(Html.fromHtml("<b>Nouvelle promotion!!</b>")))
                        .setContentText("Vérifiez les promotions du jour pour mettre les étiquetes sur les produits")
                        .setSmallIcon(R.drawable.notification)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                    notificationManager.notify(1, notificationBuilder.build())

                    val retrofi: Retrofit = retrofit.getInstance()
                    val service: UserApi = retrofi.create(UserApi::class.java)
                    val Promo = Promotion(
                        "",
                        DialogPrix?.editText?.text.toString(),
                        Dialogproduct?.editText?.text.toString(),
                        Dialogduaration?.editText?.text.toString()
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            // Do the POST request and get response
                            val response = service.AddPromotion(Promo)
                            withContext(Dispatchers.Main) {
                                if (response!!.isSuccessful) {
                                    CustomToast(context, "Added Successfully!","GREEN").show()
                                    talk!!.senddata(response.body()!!)
                                    dialog.dismiss()
                                    println("success")


                                } else {
                                    Log.e("RETROFIT_ERROR", response.code().toString())
                                    println("Message :" + response.errorBody()?.string())
                                    CustomToast(context, "Something Went Wrong!", "RED").show()
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                                dialog.dismiss()
                                CustomToast(context, "Sorry, Our Server Is Down!", "RED").show()
                            })
                        }
                    }
                }
            }
        })
    }





    fun ShowDialogUpdateProduct(context: Context?, view: View) {
        val dialog = Dialog(context!!)
        dialog.setContentView(view)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //Make it TRANSPARENT
        dialog.window!!.getAttributes().windowAnimations = R.style.DialogAnimation; //Set Animation
        dialog.show()

        val BtnSaveUpdateProduct = view.findViewById<Button>(R.id.BtnSaveAddPromotion) as? Button
        DialogPrice = view.findViewById(R.id.edt_Prix)
        Dialogquantite = view.findViewById(R.id.edt_quantite)
        val txtPrice = view.findViewById<TextView>(R.id.edt_Prix) as? TextView
        val txtquantite = view.findViewById<TextView>(R.id.edt_quantite) as? TextView




        /////
        val preferences : SharedPreferences = context.getSharedPreferences("product", Context.MODE_PRIVATE)
        val MyID = preferences.getString("_id", null)
        val prix = preferences.getString("prix", null)
        val quantite = preferences.getString("quantite", null)
        println(MyID);

        txtPrice!!.text = prix
        txtquantite!!.text = quantite


        //
        /////

        ///////
       // gettextwathcerUpdateProduct()
        BtnSaveUpdateProduct?.setOnClickListener(object : View.OnClickListener {
            @SuppressLint("RestrictedApi")
            override fun onClick(view: View?) {



                EditProduct(MyID.toString(),DialogPrice.text.toString(),Dialogquantite.text.toString(),context,dialog)



            }
        })

    }




    @SuppressLint("RestrictedApi")
    fun EditProduct(Productid:String, prix:String, quantite: String, context: Context?, dialog: Dialog)
    {
        val retrofi: Retrofit = retrofit.getInstance()
        val service: UserApi = retrofi.create(UserApi::class.java)
        val Product = Produit()
        Product.setID(Productid)
        Product.setPrix(prix)
        Product.setQuantite(quantite)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.UpdateProduit(Product)
                withContext(Dispatchers.Main) {
                    if (response!!.isSuccessful) {
                        if (context != null) {
                            CustomToast(context, "Updated Successfully!","GREEN").show()
                        }
                        dialog.dismiss()

                    } else {
                        Log.e("RETROFIT_ERROR", response.code().toString())
                        println("Message :" + response.errorBody()?.string())
                        if (context != null) {
                            CustomToast(context, "Something Went Wrong!", "RED").show()
                        }
                    }
                }
            } catch (e: Exception) {
                println("Error")
                println(e.printStackTrace())
                ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                    dialog.dismiss()
                    // CustomToast(context, "Sorry, Our Server Is Down!", "RED").show()
                    //ReadyFunction.changeFragmentnull(FragmentExplore(), context)
                })
            }
        }
    }


}
interface talk {
    fun senddata(n: Fournisseur)
    fun senddata(n: Promotion)
    fun senddata(n: Produit)

}




