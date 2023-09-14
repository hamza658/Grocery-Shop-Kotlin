package com.example.shopp.Utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopp.R

class RecommendedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val Type : TextView = itemView.findViewById<TextView>(R.id.PlaceName)
    val Image : ImageView = itemView.findViewById<ImageView>(R.id.PicRecomm)
    val Prix : TextView = itemView.findViewById<TextView>(R.id.LieuxPlace)
    val Quantite : TextView = itemView.findViewById<TextView>(R.id.quantite)


    val numbComment : TextView = itemView.findViewById<TextView>(R.id.numbComment)

}