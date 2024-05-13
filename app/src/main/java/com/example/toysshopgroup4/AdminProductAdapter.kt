package com.example.toysshopgroup4

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AdminProductAdapter (options: FirebaseRecyclerOptions<AdminProduct>) : FirebaseRecyclerAdapter<AdminProduct, AdminProductAdapter.MyViewHolder>(options){

    class MyViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.admin_row_layout, parent, false)) {
        val toyName: TextView = itemView.findViewById(R.id.toyName)
        val toyPrice: TextView = itemView.findViewById(R.id.toyPrice)
        val toyImage: ImageView = itemView.findViewById(R.id.toyImage)

        val btnEdit : Button = itemView.findViewById(R.id.btnEdit);
        val btnDelete : Button = itemView.findViewById(R.id.btnDelete);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context);
        return MyViewHolder(inflater,parent);
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: AdminProduct) {
        holder.toyName.text = model.name;
        holder.toyPrice.text = model.price;

        val storage: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(model.image)
        Glide.with(holder.toyImage.context)
            .load(storage)
            .into(holder.toyImage)

        holder.btnEdit.setOnClickListener{
            val childId = FirebaseDatabase.getInstance().reference.child("toys")
                .child(getRef(holder.absoluteAdapterPosition).key!!).toString()
            val toEditToyActivity = Intent(holder.itemView.context,EditToyActivity::class.java);
            toEditToyActivity.putExtra("toyBrand",model.brand);
            toEditToyActivity.putExtra("toyName",model.name);
            toEditToyActivity.putExtra("toyDescription",model.description);
            toEditToyActivity.putExtra("toyPrice",model.price);
            toEditToyActivity.putExtra("toyImage",model.image);
            toEditToyActivity.putExtra("childId",childId);

            holder.itemView.context.startActivity(toEditToyActivity);
        }

        holder.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(holder.toyName.getContext())
            builder.setTitle("Delete Data")
            builder.setMessage("Are you sure you want to delete?")

            builder.setPositiveButton(
                "Delete"
            ) { dialog, which ->
                FirebaseDatabase.getInstance().reference.child("toys")
                    .child(getRef(holder.absoluteAdapterPosition).key!!)
                    .removeValue()
                Toast.makeText(holder.toyName.getContext(),"Item deleted successfully...",Toast.LENGTH_LONG).show();
            }

            builder.setNegativeButton(
                "Cancel"
            ) { dialog, which ->
                Toast.makeText(
                    holder.toyName.getContext(),"Operation Cancelled",Toast.LENGTH_LONG).show();
            }
            builder.show()
        }
    }
}