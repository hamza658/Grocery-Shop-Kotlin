package com.example.shopp.Utils

import com.example.shopp.R
import com.example.shopp.Data.Category

class ListCategory {

    var ListCategory: MutableList<Category> = ArrayList()


    fun initListCategory()
    {
        ListCategory.add(
            Category(
                NomCategory = "Fruits",
                IconCategory = R.drawable.lii
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "Milk",
                IconCategory = R.drawable.mii
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "Meat",
                IconCategory = R.drawable.vii
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "Yogurt",
                IconCategory = R.drawable.yag
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "Fruits",
                IconCategory = R.drawable.fdfd
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "Fruits",
                IconCategory = R.drawable.fdfd
            )
        )
        ListCategory.add(
            Category(
                NomCategory = "Fruits",
                IconCategory = R.drawable.fdfd
            )
        )




    }
}