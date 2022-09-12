package com.example.e_shoping.Activity

import android.content.Intent
import android.icu.text.CaseMap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.e_shoping.MainActivity
import com.example.e_shoping.R
import com.example.e_shoping.RoomDatabase.AppDatabase
import com.example.e_shoping.RoomDatabase.productDao
import com.example.e_shoping.RoomDatabase.productModel
import com.example.e_shoping.databinding.ActivityProductdetailedBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Productdetailed : AppCompatActivity() {
    lateinit var binding:ActivityProductdetailedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProductdetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
          getproductdetail(intent.getStringExtra("id"))

    }

    private fun getproductdetail(productid: String?) {
     Firebase.firestore.collection("product")
         .document(productid!!).get().addOnSuccessListener {
           val list =it.get("productImage") as ArrayList<String>
             val name=it.getString("productName")
                 val productSp=it.getString("productSp")
                     val productDesc=it.getString("productDescription")


             binding.Title.text=name
             binding.Price.text=productSp
             binding.Description.text=productDesc

             val Slidelist=ArrayList<SlideModel>()
             for(data in list){
                 Slidelist.add(SlideModel(data, scaleType = ScaleTypes.FIT))
             }

             cartAction(productid,name,productSp,it.getString("productCoverImage"))

               binding.imageSlider.setImageList(Slidelist)

         }.addOnFailureListener{
  Toast.makeText(this,"somthing Went Wrong",Toast.LENGTH_SHORT).show()
         }
    }

    private fun cartAction(productid: String , name: String? , productSp: String? , cover: String?) {
      val productDao=AppDatabase.getinstance(this).productdao()

        if(productDao.isExite(productid) != null){

            binding.AddCart.text="Go to Cart"
        }else{
            binding.AddCart.text="Add to Cart"
        }
        binding.AddCart.setOnClickListener{
            if(productDao.isExite(productid) != null){
              opencart()
            }else{
              addToCart(productDao,productid,name,productSp,cover)
            }
        }

    }

    private fun addToCart(productDao: productDao , productid: String , name: String? , productSp: String? , cover: String?) {
   val data =productModel(productid,name,cover,productSp)
        lifecycleScope.launch(Dispatchers.IO){
            productDao.insertProduct(data)
            binding.AddCart.text="Go to Cart"
        }
    }

    private fun opencart() {
      val prefrence=this.getSharedPreferences("info", MODE_PRIVATE)
        val editor=prefrence.edit()
        editor.putBoolean("isCart",true)
        editor.apply()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}