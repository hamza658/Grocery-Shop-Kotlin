package com.example.shopp.ViewModel


import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopp.Data.Loginresponse
import com.example.shopp.Network.SessionManager
import com.example.shopp.Network.retrofit
import com.example.shopp.Utils.*
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import com.example.shopp.Network.UserApi
import com.example.shopp.Views.Activity.ForgetPassword2
import com.example.shopp.Views.Activity.ForgetPassword3
import com.example.shopp.Views.Activity.Login
import com.example.shopp.Utils.CustomToast


class UserViewModel : ViewModel() {
    val retrofi: Retrofit = retrofit.getInstance()
    val service: UserApi = retrofi.create(UserApi::class.java)
    //
    val ReadyFunction = ReadyFunction()
    //
    var errorMessage = MutableLiveData<String>()
    var UserLiveData: MutableLiveData<Loginresponse> = MutableLiveData()
    val _UserLiveData : LiveData<Loginresponse> = UserLiveData
    //
    private val statusMessage = MutableLiveData<String>()
    private lateinit var MySharedPref: SharedPreferences
    private lateinit var sessionManager: SessionManager
    //

    // Login Méthode 1
    fun login1(
        loginemail: TextInputEditText,
        loginpassw: TextInputEditText,
        context: Context,
        view: View,
        activity: Activity,
        cbRememberMe: CheckBox
    ) {
        ReadyFunction.showView(view)

        sessionManager = SessionManager(context)

        val paramObject1 = JSONObject()
        paramObject1.put("email", loginemail.text.toString().trim())
        paramObject1.put("password", loginpassw.text.toString().trim())

        val jsonParser = JsonParser()
        val gsonObject1 = jsonParser.parse(paramObject1.toString()) as JsonObject

        service.login1(gsonObject1).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.code() == 423) {
                    ReadyFunction().hideView(view) //Hide ProgressBar
                    val factory = LayoutInflater.from(context)
                    // val view: View = factory.inflate(R.layout.dialogverifyemail, null)
                    //  val msg = CustomDialog()
                    //  msg.ShowCustomDialog(context, view)
                }
                if (response.code() == 200) {
                    val dataBody = response.body()?.toString()
                    if (dataBody != null && dataBody.isNotEmpty()) {
                        val json = JsonParser()
                        val userData = json.parse(dataBody).asJsonObject

                        println("DataBody " + dataBody)
                        println("UserData " + userData)

                        val id = userData.getAsJsonPrimitive("id").asString
                        val firstName = userData.getAsJsonPrimitive("firstName").asString
                        val userEmail = userData.getAsJsonPrimitive("email").asString
                        val token = userData.getAsJsonPrimitive("token").asString

                        val userAvatar = if (userData.has("photo")) {
                            userData.getAsJsonPrimitive("photo").asString
                        } else {
                            "" // Empty string if photo doesn't exist
                        }

                        MySharedPref = context.getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
                        MySharedPref.edit().apply {
                            putString(IDUSER, id)
                            putString(NAMEUSER, firstName)
                            putString(EMAILUSER, userEmail)
                            putString(AVATARUSER, userAvatar)
                            putString(TOKENUSER, token)

                            if (cbRememberMe.isChecked) {
                                putString(RememberEmail, loginemail.text.toString().trim())
                                putString(RememberPassword, loginpassw.text.toString().trim())
                            }
                        }.apply()

                        CustomToast(context, "Login Successful!", "GREEN").show()
                        sessionManager.saveAuthToken(userData.getAsJsonPrimitive("token").asString)
                        ReadyFunction.GoToActivityNavigation(context, activity) //GoTo Page Navigation
                        statusMessage.value = "Sign in successful"
                    }
                }
                if (response.code() == 422) {
                    ReadyFunction().hideView(view) //Hide ProgressBar
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    println("Message :" + response.errorBody()?.string())
                    CustomToast(context, "Email or password is incorrect!", "RED").show()
                    statusMessage.value = "please enter all fields"
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                ReadyFunction().hideView(view) //Hide ProgressBar
                Log.e("Error", t.message.toString())
                CustomToast(context, "Sorry, Something Goes Wrong!", "RED").show()
            }
        })
    }


    fun Register(txtname: TextInputEditText,txtemail: TextInputEditText,txtpass:TextInputEditText,context:Context,Activity : Activity){
        val map: HashMap<String, String> = HashMap()
        map["firstName"] = txtname.text.toString()
        map["email"] = txtemail.text.toString()
        map["password"] = txtpass.text.toString()
        service.signup(map).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                println("okkkkkkkkkkkk");
                val Body = response.body().toString()
                var json = JsonParser()
                var Data = json.parse(Body).asJsonObject
                val message =Data!!.asJsonObject.get("message").asString
                //
                if (response.code()==200){
                    CustomToast(context, "Success! you've joined Discovery","GREEN").show()
                 //   SendConfirmEmail(txtemail.text.toString()) // Methode Post To Send Verification Email Of User Need to Confirm
                    //
                    val factory = LayoutInflater.from(context)
                  //  val view: View = factory.inflate(R.layout.dialogverifyemail, null)
                  //  val msg = CustomDialog()
                  //  msg.ShowCustomDialog(context, view)
                    //
                    Handler().postDelayed({
                        ReadyFunction.GoToActivityLogin(context,Activity) //GoTo Page Login
                    }, 4500)
                    //
                }else{
                    CustomToast(context, "This Email Already Exist!","RED").show()
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
            }
        })
    }


   /* fun SendConfirmEmail(EmailValue: String) {
        val User = Loginresponse()
        User.setEmail(EmailValue)
        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            try {
                val response = service.SendConfirmEmail(User)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        println("A verification email has been sent!")
                    } else {
                        Log.e("RETROFIT_ERROR", response.code().toString())
                        println("Message :" + response.errorBody()?.string())
                    }
                }
            } catch (e: Exception)
            {
                println(e.printStackTrace())
                println("Error")
            }
        }
    }*/


    // Login Méthode 1
    fun SendCodeForgot(EmailForget: String,context : Context, Activity : Activity) {
        val map: HashMap<String, String> = HashMap()
        map["email"] = EmailForget
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        service.SendCodeForgot(map).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.code() == 200) {
                        MySharedPref = context.getSharedPreferences(
                            PREF_NAME,
                            AppCompatActivity.MODE_PRIVATE
                        );
                        MySharedPref.edit().apply{
                            putString(EMAILFORGET, EmailForget)
                        }.apply()
                        //
                        CustomToast(context, "The Code has been Sent!","GREEN").show()
                        val intent = Intent(context, ForgetPassword2::class.java)
                        context.startActivity(intent)
                        Activity.finish()
                }
                else if (response.code() == 202) {
                    CustomToast(context, "Sorry, This Email Doesn't exist!","RED").show()
                }
                else
                {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    println("Message :" + response.errorBody()?.string())
                    CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Error", t.message.toString())
                CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
            }
        })
    }


    fun VerifCodeForgot(EmailValue: String,CodeValue: String,context : Context, Activity : Activity) {
        val map: HashMap<String, String> = HashMap()
        map["email"] = EmailValue
        map["code"] = CodeValue
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        service.VerifCodeForgot(map).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.code() == 200) {
                    MySharedPref = context.getSharedPreferences(
                        PREF_NAME,
                        AppCompatActivity.MODE_PRIVATE
                    );
                    MySharedPref.edit().apply{
                        putString(CODEFORGET, CodeValue)
                    }.apply()
                    //
                    CustomToast(context, "Reset Your Pasword Now!","GREEN").show()
                    val intent = Intent(context, ForgetPassword3::class.java)
                    context.startActivity(intent)
                    Activity.finish()
                }
                else if (response.code() == 402) {
                    println(CODEFORGET);

                    CustomToast(context, "Sorry, The code is incorrect!","RED").show()
                }
                else if (response.code() == 401) {
                    CustomToast(context, "Sorry, Click Resend!","RED").show()
                }
                else
                {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    println("Message :" + response.errorBody()?.string())
                    CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Error", t.message.toString())
                CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
            }
        })
    }

    fun ChangePasswordForgot(EmailValue:String,CodeValue:String,NewPassword:String,context:Context,Activity:Activity) {
        val map: HashMap<String, String> = HashMap()
        map["email"] = EmailValue
        map["code"] = CodeValue
        map["password"] = NewPassword
        /////////////////////////////////////////////////////////////////////////////////////////////////////
        service.ChangePasswordForgot(map).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.code() == 200) {
                    CustomToast(context, "Password has been changed!","GREEN").show()
                    val intent = Intent(context, Login::class.java)
                    context.startActivity(intent)
                    Activity.finish()
                }
                else if (response.code() == 402) {
                    CustomToast(context, "Sorry, The code is incorrect!","RED").show()
                }
                else
                {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                    println("Message :" + response.errorBody()?.string())
                    CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
                }
            }
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Error", t.message.toString())
                CustomToast(context, "Sorry, Something Goes Wrong!","RED").show()
            }
        })
    }





    fun UploadAvatar(email:RequestBody,photo: MultipartBody.Part,context: Context){
        val progressdialog = ProgressDialog(context)
        progressdialog.setMessage("Please Wait....")
        progressdialog.show();
        val addUser=service.postImage(email,photo)
        addUser.enqueue(object : Callback<Loginresponse> {
            override fun onResponse(call: Call<Loginresponse>, response: Response<Loginresponse>) {
                if (response.isSuccessful){
                    UserLiveData.postValue(response.body())
                    progressdialog.dismiss();

                    println("New Avatar "+response.body()?.getmsg())
                    ///
                    var DataBody = response.body()?.toString()
                    if (DataBody != null && !DataBody.isEmpty()) {
                        val avatarFromBody = response.body()?.getmsg()
                        println(avatarFromBody)
                        MySharedPref = context.getSharedPreferences(
                            PREF_NAME,
                            AppCompatActivity.MODE_PRIVATE
                        );
                        MySharedPref.edit().apply{putString(AVATARUSER, avatarFromBody)}.apply()
                    }
                    ///
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())
                    UserLiveData.postValue(response.body())
                    progressdialog.dismiss();
                }
            }
            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<Loginresponse>, t: Throwable) {
                UserLiveData.postValue(null)
                Log.i("failure",  t.message.toString())
                progressdialog.dismiss();
            }
        })
    }

    // Login Méthode 2 Ne9sa Go To Home
    /*
    @SuppressLint("RestrictedApi")
    fun login2(loginemail: TextView,loginpassw:TextView) {
        val User = Loginresponse()
        User.setEmail(loginemail.text.toString())
        User.setPassword(loginpassw.text.toString())
        //
        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            try {
                val response = service.login2(User)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        println("Token =============>>>>>>>>>  " + response.body()?.getToken())
                        println("ID =============>>>>>>>>>  " + response.body()?.getID())
                        println("Name =============>>>>>>>>>  " + response.body()?.getName())
                        statusMessage.value = "Sign in successful"
                    } else {
                       // hideProgressBar() //Hide ProgressBar
                        Log.e("RETROFIT_ERROR", response.code().toString())
                        println("Message :" + response.errorBody()?.string())
                       // CustomToast(this@Login, "Email or password is incorrect!","RED").show()
                        statusMessage.value = "please enter all fields"
                    }
                }
            } catch (e: Exception)
            {
                println(e.printStackTrace())
                println("Error")
            }
        }
    }
*/







}