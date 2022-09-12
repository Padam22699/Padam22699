package com.example.e_shoping.Fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.example.e_shoping.Adapter.catagoryAdapter
import com.example.e_shoping.Adapter.productAdapter
import com.example.e_shoping.Model.addProductModel
import com.example.e_shoping.Model.categoryModel
import com.example.e_shoping.R
import com.example.e_shoping.databinding.FragmentHomeBinding
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private lateinit var diloag: Dialog
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle? ,
    ): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater)

        val prefrence=requireActivity().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        if(prefrence.getBoolean("isCart" ,false))
            findNavController().navigate(R.id.action_homeFragment_to_cardFragment2)


        diloag= Dialog(requireContext())
        diloag.setContentView(R.layout.progresslyout)
        diloag.setCancelable(false)

        getsliderImage()
        getcategort()
         getProduct()

        return binding.root
    }

    private fun getsliderImage() {
       Firebase.firestore.collection("slider").document("item") //data from firebase
           .get().addOnSuccessListener {
         Glide.with(requireContext()).load(it.get("img")).into(binding.slider)  //img declear on firebase stote
           }
           .addOnFailureListener{

           }
    }

    private fun getProduct() {
        val list=ArrayList<addProductModel>()
        Firebase.firestore.collection("product")  // from firebase
            .get().addOnSuccessListener {
                list.clear()
                for(doc in  it.documents){
                    val data=doc.toObject(addProductModel::class.java)
                    list.add(data!!)
                }
                binding.ProductRecycler.adapter= productAdapter(requireActivity(),list)
            }.addOnFailureListener{
                Toast.makeText(requireContext(),"Failed to get data",Toast.LENGTH_SHORT).show()
            }

    }

    private fun getcategort() {
        diloag.show()
        val list=ArrayList<categoryModel>()
        Firebase.firestore.collection("category") // from firebase
            .get().addOnSuccessListener {
                diloag.dismiss()
                list.clear()
                for(doc in  it.documents){
                    val data=doc.toObject(categoryModel::class.java)
                    list.add(data!!)
                }
                binding.CatogryRecycler.adapter= catagoryAdapter(requireActivity(),list)
            }.addOnFailureListener{
                Toast.makeText(requireContext(),"Failed to get data",Toast.LENGTH_SHORT).show()
            }
    }

}
