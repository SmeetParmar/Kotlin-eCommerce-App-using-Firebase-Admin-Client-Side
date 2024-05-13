package com.example.toysshopgroup4

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions


import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.Serializable

class OrderAdapter(options: FirebaseRecyclerOptions<Order>) : FirebaseRecyclerAdapter<Order, OrderAdapter.MyViewHolder>(options) {

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.order_row_layout, parent, false)) {
        val orderFullName: TextView = itemView.findViewById(R.id.orderFullName)
        val orderMobileNumber: TextView = itemView.findViewById(R.id.orderMobileNumber)
        val orderShippingAddress: TextView = itemView.findViewById(R.id.orderShippingAddress)
        val orderGrandTotal: TextView = itemView.findViewById(R.id.orderGrandTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Order) {
        // Access the productDetails map from the Order model
        //val productDetails: Map<String, ProductDetails> = model.productDetails

        holder.orderFullName.text = "Name : " + model.fullName;
        holder.orderMobileNumber.text = "Mobile No. : " + model.mobileNumber;
        holder.orderShippingAddress.text = "Shipping Address : " + model.shippingAddress;
        holder.orderGrandTotal.text = "Total : " + model.grandTotal;

//        val productDetailsMap: Map<String, ProductDetails> = model.productDetails
//        val productDetailsList: ArrayList<ProductDetails> = ArrayList(productDetailsMap.values)
       // val productDetailsArray: Array<ProductDetails> = productDetailsMap.values.toTypedArray()

            holder.itemView.setOnClickListener {
                val toOrderDetailsActivity = Intent(holder.itemView.context, OrderDetailsActivity::class.java)

                toOrderDetailsActivity.putExtra("fullName",model.fullName);
                toOrderDetailsActivity.putExtra("mobileNumber",model.mobileNumber);
                toOrderDetailsActivity.putExtra("emailAddress",model.emailAddress);
                toOrderDetailsActivity.putExtra("cardNumber",model.cardNumber);
                toOrderDetailsActivity.putExtra("expiryDate",model.expiryDate);
                toOrderDetailsActivity.putExtra("securityCode",model.securityCode);
                toOrderDetailsActivity.putExtra("cardHolderName",model.cardHolderName);
                toOrderDetailsActivity.putExtra("grandTotal",model.grandTotal);
                toOrderDetailsActivity.putExtra("shippingAddress",model.shippingAddress);

                holder.itemView.context.startActivity(toOrderDetailsActivity)
            }
        }

}