package com.example.toysshopgroup4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class EditCartActivity : AppCompatActivity() {


    var imageURL : String? =  "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_cart)


        val toyName: TextView = findViewById(R.id.toyName);
        val toyPrice: TextView = findViewById(R.id.toyPrice);
        val toyImage: ImageView = findViewById(R.id.toyImage);
        val btnUpdateCart: Button = findViewById(R.id.btnUpdateCart);
        val quantity: TextView = findViewById(R.id.quantity);
        val quantityPlus: Button = findViewById(R.id.quantityPlus);
        val quantityMinus: Button = findViewById(R.id.quantityMinus);
        quantity.text = intent.getStringExtra("toyQuantity");
        var quantityText: Int = quantity.text.toString().toInt();

        var intentToyName: String ?=  intent.getStringExtra("toyName");
        var intentToyPrice: String ?= intent.getStringExtra("toyPrice");
        imageURL = intent.getStringExtra("toyImage");

        toyName.text = "Name : "+intentToyName;
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

        btnUpdateCart.setOnClickListener {

            val map: MutableMap<String, Any> = HashMap()
            map["quantity"] = quantityText.toString();

            FirebaseDatabase.getInstance().getReferenceFromUrl(intent.getStringExtra("childId")!!)
                .updateChildren(map)
                .addOnSuccessListener {
                    Toast.makeText(this,"Cart Updated Successfully.",Toast.LENGTH_SHORT).show()
                    val toCartActivity = Intent(this,CartActivity::class.java)
                    startActivity(toCartActivity)
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Error while updating cart.",Toast.LENGTH_SHORT).show()
                }
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