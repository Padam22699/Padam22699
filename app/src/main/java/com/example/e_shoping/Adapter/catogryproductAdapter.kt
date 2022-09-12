package com.example.e_shoping.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_shoping.Activity.Productdetailed
import com.example.e_shoping.Model.addProductModel
import com.example.e_shoping.databinding.ItemCatogryproductBinding

class catogryproductAdapter(val context: Context,val list:ArrayList<addProductModel>)
    :RecyclerView.Adapter<catogryproductAdapter.categoryproductViewHolder>()
{


    inner class categoryproductViewHolder(val binding:ItemCatogryproductBinding)
        :RecyclerView.ViewHolder(binding.root){

        }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): categoryproductViewHolder {
      val binding=ItemCatogryproductBinding.inflate(LayoutInflater.from(context),parent,false)
        return categoryproductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: categoryproductViewHolder , position: Int) {
       Glide.with(context).load(list[position].productCoverImage).into(holder.binding.imageView3)
        holder.binding.textView5.text=list[position].productName
        holder.binding.textView9.text=list[position].productSp

        holder.itemView.setOnClickListener{
            val intent=Intent(context,Productdetailed::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }
}