package com.example.toysshopgroup4

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ProductAdapter (options: FirebaseRecyclerOptions<Product>) : FirebaseRecyclerAdapter<Product, ProductAdapter.MyViewHolder>(options){

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.row_layout, parent, false)) {
        val toyName: TextView = itemView.findViewById(R.id.toyName)
        val toyPrice: TextView = itemView.findViewById(R.id.toyPrice)
        val toyImage: ImageView = itemView.findViewById(R.id.toyImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context);
        return MyViewHolder(inflater,parent);
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: Product) {
        holder.toyName.text = model.name;
        holder.toyPrice.text = model.price;

        val storage: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(model.image)
        Glide.with(holder.toyImage.context)
            .load(storage)
            .into(holder.toyImage)

        holder.itemView.setOnClickListener{
            val toDetailsActivity = Intent(holder.itemView.context,DetailActivity::class.java);
            toDetailsActivity.putExtra("toyBrand",model.brand);
            toDetailsActivity.putExtra("toyName",model.name);
            toDetailsActivity.putExtra("toyDescription",model.description);
            toDetailsActivity.putExtra("toyPrice",model.price);
            toDetailsActivity.putExtra("toyImage",model.image);

            holder.itemView.context.startActivity(toDetailsActivity);
        }
    }
}