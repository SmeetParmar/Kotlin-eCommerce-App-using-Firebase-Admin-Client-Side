package com.example.toysshopgroup4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class DetailActivity : AppCompatActivity() {


    val currentSignedInUser = FirebaseAuth.getInstance().currentUser?.uid.toString()
    var imageURL : String? =  "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toyBrand: TextView = findViewById(R.id.toyBrand);
        val toyName: TextView = findViewById(R.id.toyName);
        val toyDescription: TextView = findViewById(R.id.toyDescription);
        val toyPrice: TextView = findViewById(R.id.toyPrice);
        val toyImage: ImageView = findViewById(R.id.toyImage);
        val btnAddToCart: Button = findViewById(R.id.btnAddToCart);
        val quantity: TextView = findViewById(R.id.quantity);
        val quantityPlus: Button = findViewById(R.id.quantityPlus);
        val quantityMinus: Button = findViewById(R.id.quantityMinus);
        var quantityText: Int = quantity.text.toString().toInt();

        var intentToyName: String ?=  intent.getStringExtra("toyName");
        var intentToyPrice: String ?= intent.getStringExtra("toyPrice");
        imageURL = intent.getStringExtra("toyImage");

        toyBrand.text = "Brand : "+intent.getStringExtra("toyBrand");
        toyName.text = "Name : "+intentToyName;
        toyDescription.text = intent.getStringExtra("toyDescription");
        toyPrice.text = "Price : "+intentToyPrice;

        imageURL = intent.getStringExtra("toyImage");
        val finalImageURL: String = imageURL ?: "";

        val storage: StorageReference = FirebaseStorage.getInstance().getReferenceFromUrl(finalImageURL);
        Glide.with(toyImage.context)
            .load(storage)
            .into(toyImage);

        quantityPlus.setOnClickListener{
            if(quantityText>=10) Toast.makeText(this, "Cannot add more than 10...", Toast.LENGTH_SHORT).show() else quantityText++;
            quantity.text = quantityText.toString();
        }

        quantityMinus.setOnClickListener{
            if(quantityText<=1) Toast.makeText(this, "Need to add at least 1...", Toast.LENGTH_SHORT).show() else quantityText--;
            quantity.text = quantityText.toString();
        }

        btnAddToCart.setOnClickListener{

            var databaseRef = FirebaseDatabase.getInstance().reference.child("cart").child(currentSignedInUser);

            //var itemToAdd = Cart(intentToyName,finalImageURL,intentToyPrice,quantityText.toString());

            databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {

                var isExist = false;
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val totalToys = dataSnapshot.childrenCount;
                    //Log.i("msg",totalToys.toString());
                    if (totalToys > 0) {
                        for (i in 1 until totalToys + 1) {
                            var id = "toy" + i;
                            if (dataSnapshot.child(id).child("name").getValue()
                                    .toString() == intentToyName
                                &&
                                dataSnapshot.child(id).child("price").getValue()
                                    .toString() == intentToyPrice
                                &&
                                dataSnapshot.child(id).child("image").getValue()
                                    .toString() == finalImageURL
                            ) {
                                isExist = true
                                //Log.i("SAME","Same");
                                break
                            } else {
                                isExist = false
                            }
                        }
                    }

                        if(isExist)
                        {
                            Toast.makeText(this@DetailActivity,"This toy is already in your cart....",Toast.LENGTH_SHORT).show() ;
                        }
                        else
                        {
                            //Toast.makeText(this@DetailActivity,"Added to cart....",Toast.LENGTH_SHORT).show() ;
//                            val id = totalToys+1;
//                            databaseRef.child("toy"+id.toString()).setValue(itemToAdd)
//                                .addOnSuccessListener {
//                                    Toast.makeText(this@DetailActivity, "Product added successfully", Toast.LENGTH_SHORT).show()
//                                }
//                                .addOnFailureListener {
//                                    Toast.makeText(this@DetailActivity, "Failed to add product", Toast.LENGTH_SHORT).show()
//                                }

                            val id = totalToys+1;
                            databaseRef.child("toy"+id.toString()).child("name").setValue(intentToyName);
                            databaseRef.child("toy"+id.toString()).child("image").setValue(finalImageURL);
                            databaseRef.child("toy"+id.toString()).child("price").setValue(intentToyPrice);
                            databaseRef.child("toy"+id.toString()).child("quantity").setValue(quantityText.toString());
                            databaseRef.child("toy"+id.toString()).child("removed").setValue("false");
                            Toast.makeText(this@DetailActivity,"Toy added successfully in cart....",Toast.LENGTH_SHORT).show() ;
                        }
                    }

                override fun onCancelled(databaseError: DatabaseError) { }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id === R.id.optionMenu) {
            val toMenu = Intent(this, ProductActivity::class.java)
            startActivity(toMenu)
        }
        if (id === R.id.optionCart) {
            val toCart = Intent(this, CartActivity::class.java)
            startActivity(toCart)
        }
        if (id === R.id.optionOrder) {
            val toOrderActivity = Intent(this, OrderActivity::class.java)
            startActivity(toOrderActivity)
        }
        if (id === R.id.optionLogout) {
            val toLogin = Intent(this, LoginUserActivity::class.java)
            startActivity(toLogin)
            Toast.makeText(applicationContext, "Logged Out Successfully....", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}