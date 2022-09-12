package com.example.e_shoping.Model

data class addProductModel (
    val productName:String?="",
    val productDescription:String?="",
    val productCoverImage:String?="",
    val productcategry:String?="",
    val productId:String?="",
    val productMrp:String?="",
    val productSp:String?="",
    val productImage:ArrayList<String> = ArrayList()
    )