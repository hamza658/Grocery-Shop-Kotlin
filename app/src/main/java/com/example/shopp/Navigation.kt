package com.example.shopp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.shopp.Views.Fragement.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class Navigation : AppCompatActivity() {
    lateinit var BottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation)
        //

        BottomNavigationView = findViewById(R.id.bottomNavigationView)
        //
        val toolbar: Toolbar = findViewById(R.id.toolbar54)
        //toolbar.setNavigationIcon(R.drawable.yum)
        toolbar.setTitle("")
        setSupportActionBar(toolbar)
/*        if (supportActionBar != null) {
            val drawable = resources.getDrawable(R.drawable.yum)
            val bitmap = (drawable as BitmapDrawable).bitmap
            val newdrawable: Drawable =
                BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 50, 50, true))
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(newdrawable)
        }*/
        toolbar.visibility = View.GONE
        ///////////////////////////////////////////////////////////////////////////////////////////
        supportFragmentManager.beginTransaction().replace(R.id.container, FragmentHome()).commit();
        BottomNavigationView.setSelectedItemId(R.id.nav_home)
        BottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_home -> {
                    toolbar.visibility = View.GONE
                    selectedFragment = FragmentHome()
                }
                R.id.nav_profile -> {
                    toolbar.visibility = View.GONE
                    selectedFragment = ProfileFragment()
                }

                R.id.nav_favorite -> {
                    toolbar.visibility = View.GONE
                    selectedFragment = FournisseurFragment()
                    println("Forunisseur has selected !!")
                }

                R.id.nav_explore -> {
                    toolbar.visibility = View.GONE
                    selectedFragment = PromotionFragment()
                    println("Promotion has selected !!")
                }
                R.id.nav_add -> {
                    toolbar.visibility = View.GONE
                    selectedFragment = ProductFragment()
                    println("Promotion has selected !!")
                }
            }



            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                R.anim.slide_in,
                R.anim.slide_out
            )
            transaction.replace(R.id.container, selectedFragment!!)
            transaction.commit()
            true
        }
        ///////////////////////////////////////////////////////////////////////////////////////////
    }





}