package com.example.e_shoping.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_shoping.Activity.Productdetailed
import com.example.e_shoping.Model.addProductModel
import com.example.e_shoping.databinding.ProductItemBinding

class productAdapter(val context: Context,val list:ArrayList<addProductModel>):RecyclerView.Adapter<productAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding:ProductItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): ProductViewHolder {
     val binding=ProductItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder , position: Int) {
       val data=list[position]
        Glide.with(context).load(data.productCoverImage).into(holder.binding.imageView2)
        holder.binding.textView6.text=data.productName
        holder.binding.textView7.text=data.productcategry
        holder.binding.textView8.text=data.productMrp
        holder.binding.button.text=data.productSp

        holder.itemView.setOnClickListener{
            val intent= Intent(context, Productdetailed::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }


}