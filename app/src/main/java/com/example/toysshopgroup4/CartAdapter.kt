package com.example.toysshopgroupclass

import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.toysshopgroup4.Cart
import com.example.toysshopgroup4.CartActivity
import com.example.toysshopgroup4.EditCartActivity
import com.example.toysshopgroup4.EditToyActivity
import com.example.toysshopgroup4.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class CartAdapter (options: FirebaseRecyclerOptions<Cart>) : FirebaseRecyclerAdapter<Cart, CartAdapter.MyViewHolder>(options){
    val currentSignedInUser = FirebaseAuth.getInstance().currentUser?.uid.toString()

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.cart_row_layout, parent, false)) {
        val toyName: TextView = itemView.findViewById(R.id.toyName)
        val toyPrice: TextView = itemView.findViewById(R.id.toyPrice)
        val toyImage: ImageView = itemView.findViewById(R.id.toyImage)
        val toyQuantity: TextView = itemView.findViewById(R.id.toyQuantity);
        val toyTotal: TextView = itemView.findViewById(R.id.toyTotal);
        val deleteToyFromCart : ImageView = itemView.findViewById(R.id.deleteToyFromCart);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context);
        return MyViewHolder(inflater,parent);
    }

     override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Cart) {

         if(model.removed == "false")
         {
             holder.toyName.text = model.name;
             holder.toyPrice.text = "Price : "+model.price;
             holder.toyQuantity.text = "Qunatity : "+model.quantity;

             var price = model.price!!.substring(0,model.price!!.indexOf("$"))
             var total = price.toDouble() * model.quantity!!.toDouble();

             holder.toyTotal.text = "Total : " +  String.format("%.2f", total) + "$";

             holder.itemView.setOnClickListener{
                 val childId = FirebaseDatabase.getInstance().reference.child("cart").child(currentSignedInUser)
                     .child(getRef(holder.absoluteAdapterPosition).key!!).toString()
                 Log.i("msg",childId);
                 val toEditCartActivity = Intent(holder.itemView.context, EditCartActivity::class.java);
                 toEditCartActivity.putExtra("toyName",model.name);
                 toEditCartActivity.putExtra("toyPrice",model.price);
                 toEditCartActivity.putExtra("toyQuantity",model.quantity);
                 toEditCartActivity.putExtra("toyImage",model.image);
                 toEditCartActivity.putExtra("childId",childId);

                 holder.itemView.context.startActivity(toEditCartActivity);
             }

             holder.deleteToyFromCart.setOnClickListener {
                 val builder = AlertDialog.Builder(holder.toyName.getContext())
                 builder.setTitle("Delete Toy")
                 builder.setMessage("Are you sure you want to remove toy from cart ?")

                 builder.setPositiveButton(
                     "Delete"
                 ) { dialog, which ->
                     FirebaseDatabase.getInstance().reference.child("cart").child(currentSignedInUser)
                         .child(getRef(holder.absoluteAdapterPosition).key!!)
                         .child("removed").setValue("true");
                     Toast.makeText(holder.toyName.getContext(),"Toy removed successfully...", Toast.LENGTH_LONG).show();
                     val toCartActivity = Intent(holder.itemView.context, CartActivity::class.java);
                     holder.itemView.context.startActivity(toCartActivity);
                 }

                 builder.setNegativeButton(
                     "Cancel"
                 ) { dialog, which ->
                     Toast.makeText(
                         holder.toyName.getContext(),"Cancelled", Toast.LENGTH_LONG).show();
                 }
                 builder.show()
             }

             val storage: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(model.image)
             Glide.with(holder.toyImage.context)
                 .load(storage)
                 .into(holder.toyImage)
         }

    }
}