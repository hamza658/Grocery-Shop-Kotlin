package com.example.shopp.Views.Fragement

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.shopp.R
import com.example.shopp.Data.Fournisseur
import com.example.shopp.Data.Produit
import com.example.shopp.Data.Promotion
import com.example.shopp.Network.UserApi
import com.example.shopp.Network.retrofit
import com.example.shopp.Utils.CustomToast
import com.example.shopp.Utils.FournisseurAdapter
import com.example.shopp.Utils.ReadyFunction
import com.google.android.material.internal.ContextUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import com.example.shopp.Utils.CustomDialogs
import com.example.shopp.Utils.talk
import java.util.*

class FournisseurFragment : Fragment(), talk {
    lateinit var recylcerNeedy: RecyclerView
     lateinit var  FournisseurAdapter: FournisseurAdapter
    var BesoinModels: ArrayList<Fournisseur> = ArrayList<Fournisseur>()
    private lateinit var SwipeRefreshSearch: SwipeRefreshLayout
    private lateinit var searchView:SearchView
    private lateinit var btnShowMyPosts:Button
    private lateinit var BtnAddNeedy: Button
    private lateinit var gifImageView: ImageView

    val ReadyFunction = ReadyFunction()

    private var ctx: Context? = null
    private var self: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ctx = container?.context
        self = LayoutInflater.from(ctx).inflate(R.layout.fournisseur_fragment, container, false)



        return self
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recylcerNeedy = view.findViewById(R.id.recyclerSearch)
        SwipeRefreshSearch=view.findViewById(R.id.SwipeRefreshSearch)
        gifImageView = view.findViewById(R.id.gifImageView)


        BtnAddNeedy=view.findViewById(R.id.ButtonAddPromo)

        searchView=view.findViewById(R.id.searchView)
        recylcerNeedy.setLayoutManager(StaggeredGridLayoutManager(2, 1))
        FournisseurAdapter = FournisseurAdapter(requireActivity().getApplicationContext())
        recylcerNeedy.adapter = FournisseurAdapter

        BtnAddNeedy.setOnClickListener {
            val factory = LayoutInflater.from(context)
            val view: View = factory.inflate(R.layout.addfournisseur, null)
            val msg = CustomDialogs(this)
            msg.ShowDialogAddNeedy(context, view)
        }


            GetAllBesoin() //Show only my Posts

        SwipeRefreshSearch.setOnRefreshListener {
            GetAllBesoin()                // refresh your list contents somehow
            SwipeRefreshSearch.isRefreshing = false   // reset the SwipeRefreshLayout (stop the loading spinner)
        }
        OnSearch()




    }

    fun GetAllBesoin() {
        val retrofi: Retrofit = retrofit.getInstance()
        val service: UserApi = retrofi.create(UserApi::class.java)
        val call: Call<List<Fournisseur>> = service.GetAllfournisseur()
        call.enqueue(object : Callback<List<Fournisseur>> {
            override fun onResponse(call: Call<List<Fournisseur>>, response: Response<List<Fournisseur>>) {
                BesoinModels = ArrayList(response.body())
                FournisseurAdapter.setDataList(BesoinModels)

                println("somme : "+FournisseurAdapter.itemCount)
                println(response.body())

            }
            @SuppressLint("RestrictedApi")
            override fun onFailure(call: Call<List<Fournisseur>>, t: Throwable) {
                ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                    CustomToast(requireContext(), "Something went wrong!","RED").show()
                })
            }
        })
    }

    override fun senddata(n: Fournisseur) {
        FournisseurAdapter.addDataList(n)
    }

    override fun senddata(n: Promotion) {
        TODO("Not yet implemented")
    }
    override fun senddata(n: Produit) {
        TODO("Not yet implemented")
    }


    fun OnSearch() {

        searchView.setIconified(false);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // ne rien faire ici
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length >= 1) {
                    // appliquer le filtre de recherche sur la liste de fournisseurs
                    FournisseurAdapter.filter.filter(newText)
                    // Vérifier si la liste filtrée est vide
                    if (FournisseurAdapter.itemCount == 0) {
                        // Afficher l'image animée GIF
                        Glide.with(requireContext())
                            .load(R.drawable.error)
                            .into(gifImageView)
                        gifImageView.visibility = View.VISIBLE
                    } else {
                        // Masquer l'image animée GIF
                        Glide.with(requireContext())
                            .clear(gifImageView)
                        gifImageView.visibility = View.GONE                    }
                }

                else {
                    // réinitialiser la liste de fournisseurs
                    val allFournisseurs = GetAllBesoin()
                    FournisseurAdapter.setDataList(BesoinModels)
                    FournisseurAdapter.notifyDataSetChanged()
                    Glide.with(requireContext())
                        .clear(gifImageView)
                    gifImageView.visibility = View.GONE                }
                return true
            }
        })

    }



}