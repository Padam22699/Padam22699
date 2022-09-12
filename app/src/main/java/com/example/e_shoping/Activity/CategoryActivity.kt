package com.example.e_shoping.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.NotificationCompat.getCategory
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.e_shoping.Adapter.catogryproductAdapter
import com.example.e_shoping.Adapter.productAdapter
import com.example.e_shoping.Model.addProductModel
import com.example.e_shoping.R
import com.example.e_shoping.databinding.ActivityCategoryBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoryActivity : AppCompatActivity() {
   lateinit var binding:ActivityCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        getProduct(intent.getStringExtra("cat"))

    }

    private fun  getProduct(category: String?) {
        val list=ArrayList<addProductModel>()
        Firebase.firestore.collection("product").whereEqualTo("productcategry",category)  // from firebase productcatogry
            .get().addOnSuccessListener {
                list.clear()
                for(doc in  it.documents){
                    val data=doc.toObject(addProductModel::class.java)
                    list.add(data!!)
                }
                binding.iteamcatogryRecyclerView.adapter= catogryproductAdapter(this,list)
            }.addOnFailureListener{
                Toast.makeText(this,"Failed to get data", Toast.LENGTH_SHORT).show()
            } }

}