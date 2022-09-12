package com.example.e_shoping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.example.e_shoping.Activity.LoginActivity
import com.example.e_shoping.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    var i =0
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(FirebaseAuth.getInstance().currentUser==null){
            startActivity(Intent(this,LoginActivity::class.java))
        }

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.FragmenrContainer)
        val navController=navHostFragment!!.findNavController()

        val popuMenu=PopupMenu(this,null)
        popuMenu.inflate(R.menu.bottomnav)
        binding.bottomBar.setupWithNavController(popuMenu.menu,navController)

        navController.addOnDestinationChangedListener(object :NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController ,
                destination: NavDestination ,
                arguments: Bundle? ,
            ) {
              title=when(destination.id){
                  R.id.cardFragment -> "My Cart"
                  R.id.moreFragment -> "My DeshBoard"
                  else ->"E-Shopping"
              }
            }

        })
        binding.bottomBar.onItemSelected={
            when(it){
              0->{
                  i = 0;
                  navController.navigate(R.id.homeFragment)
              }
                1-> i=1
                2->i=2
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(i==0){
           finish()
        }
    }
}