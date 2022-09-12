package com.example.e_shoping.Activity

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.e_shoping.R
import com.example.e_shoping.databinding.ActivityAddresBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddresActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddresBinding
    private lateinit var prefrence:SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddresBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefrence=this.getSharedPreferences("user", MODE_PRIVATE)

        loadUserInfo()
        binding.CheckOutbtn.setOnClickListener{
            validateData(
                binding.UserNumber.text.toString(),
                binding.userName.text.toString(),
                binding.Pincode.text.toString(),
            binding.City.text.toString(),
            binding.State.text.toString(),
            binding.Village.text.toString(),
                )

        }

    }

    private fun validateData(number: String , name: String , pincode: String , userCity: String , userState: String , village: String) {
       if(number.isEmpty()||name.isEmpty()||pincode.isEmpty()||userCity.isEmpty()||userState.isEmpty()||village.isEmpty()){
           Toast.makeText(this , "please fill all field" , Toast.LENGTH_SHORT).show()
       } else
           storedata(pincode,userCity,userState,village)
    }

    private fun storedata( pincode: String , userCity: String , userState: String , village: String) {
      val map= hashMapOf<String,Any>()
      map["village"] =village
        map["state"]=userState
        map["city"]=userCity
        map["pinCode"]=pincode

        Firebase.firestore.collection("users")
            .document(prefrence.getString("number","")!!)
            .update(map).addOnSuccessListener {

                val intent = Intent(this , CheckOut::class.java)
                intent.putExtra("productids" , intent.getStringArrayExtra("productids"))

            startActivity(intent)
            }.addOnFailureListener{
                Toast.makeText(this , "Somthing Went Wrong" , Toast.LENGTH_SHORT).show()
            }

    }


    private fun loadUserInfo() {

      Firebase.firestore.collection("users")

          .document(prefrence.getString("number","0")!!)

          .get().addOnSuccessListener {
              binding.userName.setText(it.getString("userName"))
              binding.UserNumber.setText(it.getString("userPhoneNumber"))
              binding.Village.setText(it.getString("village"))
              binding.State.setText(it.getString("state"))
              binding.City.setText(it.getString("city"))
              binding.Pincode.setText(it.getString("pinCode"))

          }
    }
}