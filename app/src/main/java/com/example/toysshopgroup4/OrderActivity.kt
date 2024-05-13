package com.example.toysshopgroup4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toysshopgroupclass.CartAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class OrderActivity : AppCompatActivity() {
    private var adapter: OrderAdapter? = null
    val currentSignedInuser = FirebaseAuth.getInstance().currentUser?.uid.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val query = FirebaseDatabase.getInstance().reference.child("orders").child(currentSignedInuser);
        val options = FirebaseRecyclerOptions.Builder<Order>().setQuery(query, Order::class.java).build()
        adapter = OrderAdapter(options);
        val cartRecyclerView : RecyclerView = findViewById(R.id.orderRecyclerView);

        cartRecyclerView.layoutManager = LinearLayoutManager(this);

        cartRecyclerView.adapter = adapter;
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