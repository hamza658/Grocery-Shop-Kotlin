package com.example.shopp.Views.Activity
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.shopp.Navigation
import com.example.shopp.R
import com.example.shopp.Utils.IS_FRENSH
import com.example.shopp.Utils.PREF_NAME
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import java.util.*

class Authentication : AppCompatActivity() {
    private lateinit var gsc: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions

    private lateinit var MySharedPref: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)

        MySharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE)



        initView()








    }


    @SuppressLint("WrongViewCast", "UseSwitchCompatOrMaterialCode")
    fun initView() {
        val switchLanguage = findViewById<Switch>(R.id.switch_language)



        val isFrench = MySharedPref.getBoolean(IS_FRENSH, false)
        switchLanguage.isChecked = isFrench






        switchLanguage.setOnCheckedChangeListener { _, isChecked ->
            // Check if the switch is checked
            if (isChecked) {
                // Change the language to Arabic
                MySharedPref.edit().putBoolean(IS_FRENSH, true).apply()

                setLocale("fr")

            } else {
                // Use the default language

                setLocale("")
                MySharedPref.edit().putBoolean(IS_FRENSH, false).apply()

            }

            // Recreate the activity to apply the change of language
            updateUIWithNewLanguage()
            recreate()
        }





        // changement couleur bouton et corner radius

        val btnlogin = findViewById<Button>(R.id.btnlogin)
        val radius = resources.getDimension(R.dimen.button_corner_radius) // obtenir le rayon du coin depuis les ressources
        val backgroundColor = ContextCompat.getColor(this, R.color.green1) // obtenir la couleur de fond depuis les ressources

        val backgroundDrawable = GradientDrawable() // créer un objet GradientDrawable
        backgroundDrawable.setColor(backgroundColor) // définir la couleur de fond
        backgroundDrawable.cornerRadius = radius // définir le rayon des coins
        btnlogin.background = backgroundDrawable // affecter


        val btnregister = findViewById<Button>(R.id.btnregister)
        btnregister.background = backgroundDrawable // affecter

        val color = ContextCompat.getColor(this, R.color.green1)
        val colorgoogle = ContextCompat.getColor(this, R.color.white)



        btnlogin.backgroundTintList = ColorStateList.valueOf(color)
        btnregister.backgroundTintList = ColorStateList.valueOf(color)

//Action Bouton google
        val btngoogle = findViewById<Button>(R.id.google)
        //corner radius et couler
        val radiuss = resources.getDimension(R.dimen.button_corner_radius_google)
        val backgroundColorGoogle = ContextCompat.getColor(this, R.color.white)
        val backgroundDrawablee = GradientDrawable()
        backgroundDrawablee.setColor(backgroundColorGoogle) // définir la couleur de fond
        backgroundDrawablee.cornerRadius = radiuss // définir le rayon des coins
        btngoogle.background = backgroundDrawablee
        btngoogle.backgroundTintList = ColorStateList.valueOf(colorgoogle)

        //SIGN IN GOOGLE

        gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        gsc=GoogleSignIn.getClient(this,gso)

        val account:GoogleSignInAccount?= GoogleSignIn
            .getLastSignedInAccount(this)

        if(account!=null){
            goToHome()
            print("kkkk");
        }

        btngoogle.setOnClickListener {
            goToSignIn()
        }





        val ButtonLogin = findViewById<Button>(R.id.btnlogin)
        ButtonLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val ButtonRegister = findViewById<Button>(R.id.btnregister)
        ButtonRegister.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }



    }

    private fun updateUIWithNewLanguage() {
        // Mettez à jour tous les éléments de l'interface utilisateur nécessitant une traduction ou un rafraîchissement

        // Par exemple, vous pouvez mettre à jour le texte des boutons :
        val buttonLogin = findViewById<Button>(R.id.btnlogin)
        buttonLogin.text = getString(R.string.Login)

        val buttonRegister = findViewById<Button>(R.id.btnregister)
        buttonRegister.text = getString(R.string.Register)

        // Mettez à jour d'autres éléments de l'interface utilisateur selon vos besoins
    }


    override fun  onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode,resultCode,data)

        if(requestCode==1000){
            val task:Task<GoogleSignInAccount> = GoogleSignIn
                .getSignedInAccountFromIntent(data)


            try {

                task.getResult(ApiException::class.java)

                goToHome()


            }
            catch (e:java.lang.Exception){

                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }



    private fun setLocale(language: String) {
        val config = Configuration(resources.configuration)
        val locale = if (language.isNotEmpty()) Locale(language) else Locale.getDefault()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        val editor = MySharedPref.edit()
        editor.putBoolean(IS_FRENSH, language == "fr")
        editor.apply()
    }





    private fun goToSignIn() {
        val signInIntent=gsc.signInIntent
        startActivityForResult(signInIntent,1000)
    }

    private fun goToHome() {
        val intent= Intent(this, Navigation::class.java)
        startActivity(intent)
        finish()
    }



















}