package com.example.e_shoping.Activity

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.e_shoping.MainActivity
import com.example.e_shoping.databinding.ActivityOtpAvtivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OtpAvtivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpAvtivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOtpAvtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.verifyOtpbtn.setOnClickListener{
            if(binding.userOtp.text!!.isEmpty()){
                Toast.makeText(this , "Please provide OTP" , Toast.LENGTH_SHORT).show()
            }else{
                verfyUser(binding.userOtp.text.toString())
            }
        }

    }

    private fun verfyUser(otp: String) {
        val credential = PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId")!!,
        otp)
        signInWithPhoneAuthCredential(credential)

    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
       FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val prefrence=this.getSharedPreferences("user", MODE_PRIVATE)
                    val edito=prefrence.edit()
                    edito.putString("number",intent.getStringExtra("number"))
                    edito.apply()

         startActivity(Intent(this,MainActivity::class.java))
                finish()
                } else {
                    Toast.makeText(this , "Somthing Went Wrong" , Toast.LENGTH_SHORT).show()
                }
            }
    }
}