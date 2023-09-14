package com.example.shopp.Utils

import android.content.Context
import android.preference.PreferenceManager
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopp.R
import com.example.shopp.Data.Promotion
import java.io.ByteArrayOutputStream
import java.util.ArrayList

class PromotionAdapter (var context: Context) : RecyclerView.Adapter<PromotionAdapter.ViewHolder>() {

    var filteredPromotionList = mutableListOf<Promotion>()
    val ReadyFunction = ReadyFunction()

    fun setDataList(newData: ArrayList<Promotion>) {
        this.filteredPromotionList=newData
        notifyDataSetChanged()
    }

    internal fun addDataList(data: Promotion) {
        this.filteredPromotionList.add(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val prix : TextView
        val produit: TextView
        val duree : TextView


        init {
            prix = itemView.findViewById<TextView>(R.id.prix)
            produit= itemView.findViewById<TextView>(R.id.produit)
            duree = itemView.findViewById<TextView>(R.id.duree)

        }

    }

    override fun getItemCount() = filteredPromotionList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromotionAdapter.ViewHolder {


        // Inflate the custom layout
        var view = LayoutInflater.from(parent.context).inflate(R.layout.promotion, parent, false)
        return PromotionAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PromotionAdapter.ViewHolder, position: Int) {

        // Get the data model based on position
        var data = filteredPromotionList[position]
        holder.prix.text = data.prix_promo
        holder.produit.text = data.produit
        holder.duree.text = data.duree

        holder.itemView.setOnClickListener {

            println("Name Selected is "+data.prix_promo)




            //ScreenShot
            val fileOutputStream = ByteArrayOutputStream()
            //  ReadyFunction.ScreenShot(holder.itemView)?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            val compressImage: ByteArray = fileOutputStream.toByteArray()
            val sEncodedImage: String = Base64.encodeToString(compressImage, Base64.DEFAULT)

            println("sEncodedImage ===>>>>>> "+sEncodedImage)
            val preferencess = PreferenceManager.getDefaultSharedPreferences(context)
            val editorr = preferencess.edit()
            editorr.putString("NeedyScreenShot", sEncodedImage)
            editorr.apply()  //Save Data
        }

    }


}