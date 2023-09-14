package com.example.shopp

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.shopp.Views.Activity.Authentication

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnstarted = findViewById<Button>(R.id.started)
        val radius = resources.getDimension(R.dimen.button_corner_radius) // obtenir le rayon du coin depuis les ressources
        val backgroundColor = ContextCompat.getColor(this, R.color.black) // obtenir la couleur de fond depuis les ressources

        val backgroundDrawable = GradientDrawable() // créer un objet GradientDrawable
        backgroundDrawable.setColor(backgroundColor) // définir la couleur de fond
        backgroundDrawable.cornerRadius = radius // définir le rayon des coins
        val color = ContextCompat.getColor(this, R.color.white)

        btnstarted.background = backgroundDrawable // affecter
        btnstarted.backgroundTintList = ColorStateList.valueOf(color)



        val myButton = findViewById<Button>(R.id.started)
        myButton.setOnClickListener {
            val intent = Intent(this, Authentication::class.java)
            startActivity(intent)
        }



    }
}