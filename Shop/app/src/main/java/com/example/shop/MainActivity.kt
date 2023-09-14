package com.example.shop

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.shop.R
import com.example.shop.Views.Activity.Authentication

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnstarted = findViewById<Button>(R.id.started)
        val radius = resources.getDimension(R.dimen.button_corner_radius) // obtenir le rayon du coin depuis les ressources
        val backgroundColor = ContextCompat.getColor(this, R.color.white) // obtenir la couleur de fond depuis les ressources

        val backgroundDrawable = GradientDrawable() // créer un objet GradientDrawable
        backgroundDrawable.setColor(backgroundColor) // définir la couleur de fond
        backgroundDrawable.cornerRadius = radius // définir le rayon des coins

        btnstarted.background = backgroundDrawable // affecter


        val myButton = findViewById<Button>(R.id.started)
        myButton.setOnClickListener {
            val intent = Intent(this, Authentication::class.java)
            startActivity(intent)
        }



    }
}