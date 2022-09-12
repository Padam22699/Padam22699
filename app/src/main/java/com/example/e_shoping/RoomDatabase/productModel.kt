package com.example.e_shoping.RoomDatabase

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class productModel(
    @PrimaryKey
    @NonNull
    val productId:String,

 @ColumnInfo(name = "productName")
    val productname:String?="",

    @ColumnInfo(name = "productImage")
    val productImage:String?="",

    @ColumnInfo(name = "productSp")
    val productSp:String?="",


)