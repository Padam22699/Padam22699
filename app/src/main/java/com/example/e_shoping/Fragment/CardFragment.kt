package com.example.e_shoping.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.e_shoping.Activity.AddresActivity
import com.example.e_shoping.Adapter.CartAdapter
import com.example.e_shoping.RoomDatabase.AppDatabase
import com.example.e_shoping.RoomDatabase.productModel
import com.example.e_shoping.databinding.FragmentCardBinding


class CardFragment : Fragment() {
    lateinit var binding: FragmentCardBinding
    private lateinit var list:ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle? ,
    ): View? {
        binding = FragmentCardBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment

        val prefrence =
            requireContext().getSharedPreferences("info" , AppCompatActivity.MODE_PRIVATE)
        val editor = prefrence.edit()
        editor.putBoolean("isCart" , false)
        editor.apply()
        val dao = AppDatabase.getinstance(requireContext()).productdao()

        list=ArrayList()

        dao.allProduct().observe(requireActivity()) {
            binding.cartRecyclerview.adapter = CartAdapter(requireContext() , it)

            list.clear()
            for(data in it){
                list.add(data.productId)
            }

            totalCost(it)
        }

        return binding.root


    }

    private fun totalCost(data: List<productModel>?) {
        var total = 0
        for (item in data!!) {
            total += item.productSp!!.toInt()
        }
        binding.totalitem.text = "Total item in cart ${data.size}"
        binding.totacost.text = "Total cost $total"

        binding.CheckOutbtn.setOnClickListener {


            val intent = Intent(context , AddresActivity::class.java)
            intent.putExtra("totalCost" , total)
            intent.putExtra("productids",list)
            startActivity(intent)
        }
    }
}