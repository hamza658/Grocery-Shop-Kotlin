import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.preference.PreferenceManager
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopp.Data.Produit
import com.example.shopp.R
import com.example.shopp.Utils.CustomDialogs
import com.example.shopp.Utils.ReadyFunction
import com.example.shopp.Utils.RecommendedViewHolder
import java.io.ByteArrayOutputStream

class RecommendedAdapter(private val context: Context, private val limit: Int) :
    RecyclerView.Adapter<RecommendedViewHolder>(), Filterable {

    private var dataList = mutableListOf<Produit>()
    private var filteredPostsList = ArrayList<Produit>()
    private val ReadyFunction = ReadyFunction()

    fun setDataList(postsArrayList: ArrayList<Produit>) {
        this.dataList = postsArrayList
        this.filteredPostsList = postsArrayList
        notifyDataSetChanged()
    }

    fun addDataList(data: Produit) {
        this.dataList.add(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recommended, parent, false)
        return RecommendedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (filteredPostsList.size > limit) {
            limit
        } else {
            filteredPostsList.size
        }
    }


    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        val data = filteredPostsList[position]

        val imagePlace = filteredPostsList[position].image
        val type = filteredPostsList[position].type
        val quantite = filteredPostsList[position].quantite
        val prix = filteredPostsList[position].prix

        Glide.with(context)
            .load(imagePlace)
            .fitCenter()
            .into(holder.Image)

        holder.Type.text = type
        holder.Quantite.text = quantite
        holder.Prix.text = prix
        holder.itemView.setBackgroundColor(Color.parseColor("#FAFAFA"))

        val animation = AnimationUtils.loadAnimation(holder.itemView.context, R.anim.item_animation_fall_down)
        holder.itemView.startAnimation(animation)

        holder.itemView.setOnClickListener {
            val preferences: SharedPreferences = context.getSharedPreferences("product", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString("_id", data.getID())
            editor.putString("type", data.getType())
            editor.putString("quantite", data.getQuantite())
            editor.putString("prix", data.getPrix())
            editor.putString("image", data.getPhoto())
            editor.apply()

            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.update_product, null)
            val msg = CustomDialogs()
            msg.ShowDialogUpdateProduct(context, view)

            val fileOutputStream = ByteArrayOutputStream()
            ReadyFunction.ScreenShot(holder.itemView)?.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            val compressImage: ByteArray = fileOutputStream.toByteArray()
            val sEncodedImage: String = Base64.encodeToString(compressImage, Base64.DEFAULT)

            val preferencess = PreferenceManager.getDefaultSharedPreferences(context)
            val editorr = preferencess.edit()
            editorr.putString("ScreenShotAdmin", sEncodedImage)
            editorr.apply()
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null && constraint.length >= 1) {
                    val searchString = constraint.toString().toLowerCase().trim()
                    val filteredList = dataList.filter { it.type?.toLowerCase()?.startsWith(searchString) ?: true }
                    filterResults.count = filteredList.size
                    filterResults.values = filteredList
                } else {
                    filterResults.count = dataList.size
                    filterResults.values = ArrayList(dataList)
                }
                return filterResults
            }



            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (constraint.isNullOrEmpty() && results?.count == dataList.size) {
                    // Afficher les 10 premiers éléments de la liste
                    filteredPostsList = ArrayList(dataList.subList(0, minOf(limit, dataList.size)))
                } else {
                    val filteredList = results?.values as? ArrayList<Produit>
                    filteredPostsList.clear()
                    if (filteredList != null) {
                        filteredPostsList.addAll(filteredList)
                    }
                }
                notifyDataSetChanged()
            }


        }
    }
}
