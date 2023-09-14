package com.example.shop.Views.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.shop.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class Authentication : AppCompatActivity() {
    private lateinit var gsc: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentification)





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
        val colorfb = ContextCompat.getColor(this, R.color.purple_500)



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


        // Bouton facebook
        val btnfacebook = findViewById<Button>(R.id.facebook)
        val radiusss = resources.getDimension(R.dimen.button_corner_radius_google)

        val backgroundColorFb = ContextCompat.getColor(this, R.color.purple_500)
        val backgroundDrawableee = GradientDrawable()
        backgroundDrawableee.setColor(backgroundColorFb) // définir la couleur de fond
        backgroundDrawableee.cornerRadius = radiusss // définir le rayon des coins
        btnfacebook.background = backgroundDrawableee
        btnfacebook.backgroundTintList = ColorStateList.valueOf(colorfb)


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

    private fun goToSignIn() {
        val signInIntent=gsc.signInIntent
        startActivityForResult(signInIntent,1000)
    }

    private fun goToHome() {
        val intent= Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }

}