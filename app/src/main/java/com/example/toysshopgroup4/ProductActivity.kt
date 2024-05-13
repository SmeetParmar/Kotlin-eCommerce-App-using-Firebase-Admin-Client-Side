package com.example.toysshopgroup4

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class ProductActivity : AppCompatActivity() {

    private var adapter: ProductAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val query = FirebaseDatabase.getInstance().reference.child("toys");
        val options = FirebaseRecyclerOptions.Builder<Product>().setQuery(query, Product::class.java).build()
        adapter = ProductAdapter(options);

        val rView : RecyclerView = findViewById(R.id.rView);
        val orientation = resources.configuration.orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            rView.layoutManager = GridLayoutManager(this,2);
        }
        else{
            rView.layoutManager = GridLayoutManager(this,4);
        }

        rView.adapter = adapter;
    }

    override fun onStart() {
        super.onStart();
        adapter?.startListening();
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