package com.example.shopp.Utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopp.R
import com.example.shopp.Data.Fournisseur
import java.io.ByteArrayOutputStream
import java.util.ArrayList

class FournisseurAdapter(var context: Context) : RecyclerView.Adapter<FournisseurAdapter.ViewHolder>(),
    Filterable {

    var searchText = ""

     var filteredFournisseurList = mutableListOf<Fournisseur>()
    var filteredNeedyList= ArrayList<Fournisseur>()
    val ReadyFunction = ReadyFunction()
     fun setDataList(newData: ArrayList<Fournisseur>) {
         this.filteredFournisseurList=newData
         this.filteredNeedyList = newData
         notifyDataSetChanged()
    }

    internal fun addDataList(data: Fournisseur) {
        this.filteredFournisseurList.add(data)
        notifyDataSetChanged()
    }


    // Provide a direct reference to each of the views with data items

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fullName : TextView
        val secteur : TextView
        val adresse: TextView
        val NumTel: TextView

        init {
            adresse = itemView.findViewById<TextView>(R.id.locationNeedy)
            NumTel = itemView.findViewById<TextView>(R.id.phoneNeedy)
            fullName= itemView.findViewById<TextView>(R.id.nomNeedy)
            secteur = itemView.findViewById<TextView>(R.id.SituationNeedy)


        }

    }
    //  total count of items in the list
    override fun getItemCount() = filteredFournisseurList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FournisseurAdapter.ViewHolder {


        // Inflate the custom layout
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_needly, parent, false)
        return ViewHolder(view)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: FournisseurAdapter.ViewHolder, position: Int) {




        // Get the data model based on position
        var data = filteredFournisseurList[position]
        holder.fullName.text = data.fullName
        holder.secteur.text = data.secteur
        holder.adresse.text = data.adresse
        holder.NumTel.text = data.numTel


        holder.itemView.setOnClickListener {

            println("Name Selected is " + data.fullName)

            val preferences: SharedPreferences =
                context.getSharedPreferences("azz", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("id", data.id)
            editor.putString("fullName", data.fullName)
            editor.putString("adresse", data.adresse)
            editor.putString("secteur", data.numTel)
            editor.putString("numTel", data.secteur)

            editor.apply()  //Save Data
            ///


            PopUpDetails(holder.itemView)
            //ScreenShot
            val fileOutputStream = ByteArrayOutputStream()
            //  ReadyFunction.ScreenShot(holder.itemView)?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            val compressImage: ByteArray = fileOutputStream.toByteArray()
            val sEncodedImage: String = Base64.encodeToString(compressImage, Base64.DEFAULT)

            println("sEncodedImage ===>>>>>> " + sEncodedImage)
            val preferencess = PreferenceManager.getDefaultSharedPreferences(context)
            val editorr = preferencess.edit()
            editorr.putString("NeedyScreenShot", sEncodedImage)
            editorr.apply()  //Save Data
        }


    }






    fun PopUpDetailsNeedy(context: Context) {
        //Show PopUpp
        val factory = LayoutInflater.from(context)
        val view: View = factory.inflate(R.layout.detailsfournisseur, null)
        val msg = CustomDialogs()
        msg.ShowDetailsNeedy(context, view)
    }

    fun PopUpDetails(view: View) {
        PopUpDetailsNeedy(view.context)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null && constraint.length >= 1) {
                    val searchString = constraint.toString().toLowerCase().trim()
                    val tempFilteredList: ArrayList<Fournisseur> = ArrayList(filteredFournisseurList)
                    val filteredList = tempFilteredList.filter { it.fullName.toLowerCase().startsWith(searchString) }
                    filterResults.count = filteredList.size
                    filterResults.values = filteredList
                } else {
                    filterResults.count = filteredFournisseurList.size
                    filterResults.values = ArrayList(filteredFournisseurList)
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredFournisseurList = results?.values as ArrayList<Fournisseur>
                notifyDataSetChanged()
            }
        }
    }



}



