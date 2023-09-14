package com.example.shopp.Views.Fragement
import RecommendedAdapter
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.shopp.R
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import com.example.shopp.Data.Category
import com.example.shopp.Data.Fournisseur
import com.example.shopp.Data.Produit
import com.example.shopp.Data.Promotion
import com.example.shopp.Network.UserApi
import com.example.shopp.Network.retrofit
import com.example.shopp.Utils.*
import tn.yassin.oneblood.DataMapList.CategoryAdapter


class FragmentHome : Fragment() , talk {
    ///
    val ReadyFunction = ReadyFunction()
    private lateinit var searchView: SearchView
    ///

    lateinit var recylcerCategory: RecyclerView
    lateinit var recylcerAdapterCategory: CategoryAdapter
    lateinit var recylcerRecommended: RecyclerView
    lateinit var AdapterRecommended: RecommendedAdapter
    //
    val ListCategory = ListCategory()
    //val ListRecommended = ListRecommended()
    //
    var PostsModels = ArrayList<Produit>()
    lateinit var mShimmerViewContainer: ShimmerFrameLayout
    lateinit var ShowMoreHome: TextView
    lateinit var txtRecomm:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //
        initView(view);
        OpenSearch();
        //Check()
        ///
        recylcerCategory = view.findViewById(R.id.recyclerCategory)
        ///
        recylcerRecommended = view.findViewById(R.id.recyclerRecommended)
        ///
        ListCategory.initListCategory()
        ///
        //ListRecommended.initListRecommended()
        recylcerAdapterCategory = CategoryAdapter(requireContext(),ListCategory.ListCategory as ArrayList<Category>)
        recylcerCategory.adapter = recylcerAdapterCategory
        recylcerCategory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        ////

        recylcerRecommended.setLayoutManager(StaggeredGridLayoutManager(2, 1))
        AdapterRecommended = RecommendedAdapter(requireContext(),10)
        recylcerRecommended.adapter = AdapterRecommended


       // ShowPostsRecommended()

        ShowAllPostRecomm()

        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        ShowMoreHome.setOnClickListener{

            val progressDialog = ProgressDialog(context,R.style.DialogStyle)
           // progressDialog.setTitle("Loading")
            //progressDialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.GRAY));
            progressDialog.setMessage("Please wait \uD83D\uDE07")
            progressDialog.getWindow()?.getAttributes()?.gravity = Gravity.BOTTOM;
            //progressDialog.setProgressStyle(android.R.attr.progressBarStyleInverse);
            progressDialog.setCancelable(true)
            progressDialog.show()

            recylcerRecommended.setLayoutManager(StaggeredGridLayoutManager(2, 1))
            AdapterRecommended = RecommendedAdapter(requireContext(),1000)
            recylcerRecommended.adapter = AdapterRecommended
            ShowAllPostRecomm()
            //
            val handler = Handler()
            val runnable = Runnable {
                progressDialog.hide()

            }
            handler.postDelayed(runnable, 3000)
        }


    }



    fun initView(view: View) {
        searchView = view.findViewById(R.id.searchViewHome)
        ShowMoreHome = view.findViewById(R.id.ShowMoreHome)
        txtRecomm = view.findViewById(R.id.txtRecomm)
    }


    ///

    fun ShowAllPostRecomm() {
        val retrofi: Retrofit = retrofit.getInstance()
        val service: UserApi = retrofi.create(UserApi::class.java)
        val call: Call<List<Produit>> = service.GetAllProduit()
        call.enqueue(object : Callback<List<Produit>> {
            override fun onResponse(call: Call<List<Produit>>, response: Response<List<Produit>>) {
                PostsModels = ArrayList(response.body())
                //println("Boddddyyyyyyyyyy "+response.body())
                //println("Size in fun "+PostsModels.size)
                AdapterRecommended.setDataList(PostsModels)
                AdapterRecommended.notifyDataSetChanged()
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
                //
                val Nb: Int = AdapterRecommended.itemCount
                txtRecomm.text = getString(R.string.recom) + " (" + Nb.toString() + ")"
            }

            @SuppressLint("RestrictedApi")
            override fun onFailure(call: Call<List<Produit>>, t: Throwable) {
                println("Message :" + t.stackTrace)
                Log.d("***", "Opppsss" + t.message)
            }
        })
    }


    private fun OpenSearch() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length >= 1) {
                    AdapterRecommended.filter.filter(newText)
                } else {
                    ShowAllPostRecomm()
                    AdapterRecommended.setDataList(PostsModels)
                    AdapterRecommended.notifyDataSetChanged()

                }
                return true
            }
        })
    }




    override fun onResume() {
        super.onResume()
        mShimmerViewContainer.startShimmerAnimation()
    }

     override fun onPause() {
        mShimmerViewContainer.stopShimmerAnimation()
        super.onPause()
    }

    override fun senddata(n: Fournisseur) {
       
    }

    override fun senddata(n: Promotion) {
    }
    override fun senddata(n: Produit) {
        AdapterRecommended.addDataList(n)
    }

}