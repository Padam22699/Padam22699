package com.example.e_shoping.Activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.e_shoping.MainActivity
import com.example.e_shoping.R
import com.example.e_shoping.RoomDatabase.AppDatabase
import com.example.e_shoping.RoomDatabase.productModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.PaymentResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject


class CheckOut : AppCompatActivity() ,PaymentResultListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

       val checkout = CheckOut()
      //  checkout.setKeyID("<>")

        try {
            val options = JSONObject()
            options.put("name" , "E-Shopping")
            options.put("description" , "Reference No. #123456")
            options.put("image" , "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color" , "#3399cc")
            options.put("currency" , "INR")
            options.put("amount" , "500000") //pass amount in currency subunits
            options.put("prefill.email" , "padam@wedigtech.com")
            options.put("prefill.contact" , "9988776655")

        //    checkout.open(this , options)
        } catch (e: Exception) {
            Log.e(TAG , "Error in starting Razorpay Checkout" , e)
        }
        uploaddata()

    }

    private fun uploaddata() {
       val id= intent.getShortArrayExtra("productids")
        for(curentId in id!!){
            fatechdata(curentId.toString())
        }
    }

    private fun fatechdata(productId: String) {
        val dao=AppDatabase.getinstance(this).productdao()
      lifecycleScope.launch(Dispatchers.IO){
          dao.deleteProduct(productModel(productId))
      }

        Firebase.firestore.collection("product")
            .document(productId!!).get().addOnSuccessListener {


                saveData(it.getString("productName"),it.getString("productId"),productId)
            }

    }

    private fun saveData(name: String? , price: String? , productId: String) {
        val Prefrence=this.getSharedPreferences("user", MODE_PRIVATE)
val data= hashMapOf<String ,Any>()
        data["name"]=name!!
        data["porice"]=price!!
        data["productid"]=productId!!
        data["status"]="Ordered"
        data["userId"]=Prefrence.getString("number","")!!

        val fireStore=Firebase.firestore.collection("allOrders")
        val key =fireStore.document().id
        data["orderId"]=key

        fireStore.add(data).addOnSuccessListener {
            Toast.makeText(this , "Order Placed" , Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
        }.addOnFailureListener {   Toast.makeText(this , "Somthing Went Wrong" , Toast.LENGTH_SHORT).show()  }

    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this , "Payment Success" , Toast.LENGTH_SHORT).show()
    }

    override fun onPaymentError(p0: Int , p1: String?) {
        Toast.makeText(this , "Payment Failed" , Toast.LENGTH_SHORT).show()

    }
}