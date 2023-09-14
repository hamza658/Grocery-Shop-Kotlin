package com.example.shopp.Views.Fragement

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.shopp.Data.Promotion
import com.example.shopp.Network.UserApi
import com.example.shopp.Network.retrofit
import com.example.shopp.R
import com.example.shopp.Utils.CustomToast
import com.example.shopp.Utils.PromotionAdapter
import com.example.shopp.Utils.ReadyFunction
import com.google.android.material.internal.ContextUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import com.example.shopp.Utils.CustomDialogs
import java.util.ArrayList

class PromotionFragment : Fragment() {
    lateinit var recylcerPromo: RecyclerView
    lateinit var  PromotionAdapter: PromotionAdapter
    var BesoinModelsPromo: ArrayList<Promotion> = ArrayList<Promotion>()
    private lateinit var BtnAddPromo: Button
    private lateinit var SwipeRefreshSearch: SwipeRefreshLayout


    val ReadyFunction = ReadyFunction()

    private var ctx: Context? = null
    private var self: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ctx = container?.context
        self = LayoutInflater.from(ctx).inflate(R.layout.promotion_fragment, container, false)



        return self
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recylcerPromo = view.findViewById(R.id.recyclerPromo)

        BtnAddPromo=view.findViewById(R.id.ButtonAddPromo)
        SwipeRefreshSearch=view.findViewById(R.id.SwipeRefreshpromo)

        recylcerPromo.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        PromotionAdapter = PromotionAdapter(requireActivity().getApplicationContext())
        recylcerPromo.adapter = PromotionAdapter


        recylcerPromo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager
                val firstVisibleItemPositions = layoutManager.findFirstVisibleItemPositions(null)
                val lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null)

                val firstVisibleItemPosition = firstVisibleItemPositions.min() ?: 0
                val lastVisibleItemPosition = lastVisibleItemPositions.max() ?: 0

                for (i in firstVisibleItemPosition..lastVisibleItemPosition) {
                    val view = layoutManager.findViewByPosition(i)
                    view?.let {
                        if (it.alpha == 0f) {
                            val animation = AnimationUtils.loadAnimation(context, R.anim.layoutanim)
                            it.startAnimation(animation)
                        }
                    }
                }
            }

        })


        SwipeRefreshSearch.setOnRefreshListener {
            GetAllPromo()                // refresh your list contents somehow
            SwipeRefreshSearch.isRefreshing = false   // reset the SwipeRefreshLayout (stop the loading spinner)
        }


         BtnAddPromo.setOnClickListener {
             val factory = LayoutInflater.from(context)
             val view: View = factory.inflate(R.layout.addpromotion, null)
             val msg = CustomDialogs()
             msg.ShowDialogAddPromotion(context, view)
         }


        GetAllPromo() //Show only my Posts





    }

    fun GetAllPromo() {
        val retrofi: Retrofit = retrofit.getInstance()
        val service: UserApi = retrofi.create(UserApi::class.java)
        val call: Call<List<Promotion>> = service.GetAllPromotion()
        call.enqueue(object : Callback<List<Promotion>> {
            override fun onResponse(call: Call<List<Promotion>>, response: Response<List<Promotion>>) {
                BesoinModelsPromo = ArrayList(response.body())
                PromotionAdapter.setDataList(BesoinModelsPromo)
                //FournisseurAdapter.notifyDataSetChanged()

                println("somme : "+PromotionAdapter.itemCount)
                println(response.body())

            }
            @SuppressLint("RestrictedApi")
            override fun onFailure(call: Call<List<Promotion>>, t: Throwable) {
                ContextUtils.getActivity(context)?.runOnUiThread(java.lang.Runnable {
                    CustomToast(requireContext(), "Something went wrong!","RED").show()
                })
            }
        })
    }




}