package com.example.e_shoping.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.e_shoping.Model.UserModel
import com.example.e_shoping.R
import com.example.e_shoping.databinding.ActivityRegisterBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SignInR.setOnClickListener {
            startActivity(Intent(this , LoginActivity::class.java))
            finish()
        }
        binding.signupR.setOnClickListener{
            validateuser()
        }

    }

    private fun validateuser() {
       if(binding.UserName.text!!.isEmpty() || binding.userNumberR.text!!.isEmpty()){
           Toast.makeText(this,"plese fill all fields ",Toast.LENGTH_SHORT).show()
       }else{
           storedata()
       }
    }

    private fun storedata() {
      val builder=AlertDialog.Builder(this)
          .setTitle("Loading....")
          .setMessage("Please Wait")
          .setCancelable(false)
          .create()
        builder.show()

   val prefrence=this.getSharedPreferences("user", MODE_PRIVATE)
        val edito=prefrence.edit()
        edito.putString("number",binding.UserName.text.toString())
        edito.putString("name", binding.userNumberR.text.toString())
        edito.apply()

        val data = UserModel(binding.UserName.text.toString(),
            binding.userNumberR.text.toString())

        Firebase.firestore.collection("users").document(binding.userNumberR.text.toString())
            .set(data).addOnSuccessListener {
                Toast.makeText(this,"User registered ",Toast.LENGTH_SHORT).show()
                 builder.dismiss()
                  openLogin()

            }.addOnFailureListener{
                builder.dismiss()
                Toast.makeText(this,"Somthing went Wrong ",Toast.LENGTH_SHORT).show()

            }
    }

    private fun openLogin() {
        startActivity(Intent(this , LoginActivity::class.java))
        finish()
    }
}