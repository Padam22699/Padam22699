package com.example.e_shoping.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface productDao {

    @Insert
   suspend fun insertProduct(product:productModel)

    @Delete
    suspend fun deleteProduct(product: productModel)

    @Query("SELECT * FROM products")
    fun allProduct():LiveData<List<productModel>>

    @Query("SELECT * FROM  products WHERE productId = :id")
    fun isExite(id:String):productModel


}