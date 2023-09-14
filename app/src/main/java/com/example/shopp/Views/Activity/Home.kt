package com.example.shopp.Views.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shopp.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class Home : AppCompatActivity() {

    private lateinit var gsc: GoogleSignInClient
    private lateinit var gso: GoogleSignInOptions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


     /*   val name = findViewById<TextView>(R.id.name)
        val email = findViewById<TextView>(R.id.email)
        val userId = findViewById<TextView>(R.id.userid)
        val btnSignOut = findViewById<Button>(R.id.btnsignout)

        gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        gsc= GoogleSignIn.getClient(this,gso)

        val account: GoogleSignInAccount?= GoogleSignIn
            .getLastSignedInAccount(this)

        if(account!=null){
            name.text=account.displayName
            email.text=account.email

            userId.text=account.id


        }
        else{
            goSignOut()

        }

        btnSignOut.setOnClickListener{
            goSignOut()
        }
    }

    private fun goSignOut() {
        gsc.signOut().addOnSuccessListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }*/
    }
}