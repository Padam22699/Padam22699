package com.example.e_shoping.Activity

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.e_shoping.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var binnding:ActivityLoginBinding
        private lateinit var builder:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binnding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binnding.root)


        builder= AlertDialog.Builder(this)
            .setTitle("Otp Sending....")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()

        binnding.SignUnL.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }

        binnding.SignInL.setOnClickListener{
         if(binnding.userNumberL.text!!.isEmpty()){
             Toast.makeText(this , "please provide number" , Toast.LENGTH_SHORT).show()
         }else{
             sendOtp(binnding.userNumberL.text.toString())
         }

        }

    }

    private fun sendOtp(number: String) {
        builder.show()

        val option=PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91 $number")
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(option)
    }
    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

        }

        override fun onVerificationFailed(e: FirebaseException) {

        }
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            builder.dismiss()
            val intent=Intent(this@LoginActivity,OtpAvtivity::class.java)
            intent.putExtra("verificationId",verificationId)
            intent.putExtra("number",binnding.userNumberL.text.toString())
            startActivity(intent)
        }
    }
}