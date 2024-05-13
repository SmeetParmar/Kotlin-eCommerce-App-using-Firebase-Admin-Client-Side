package com.example.toysshopgroup4

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class AdminProductActivity : AppCompatActivity() {
    private var adapter: AdminProductAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_product)

        val query = FirebaseDatabase.getInstance().reference.child("toys");
        val options = FirebaseRecyclerOptions.Builder<AdminProduct>().setQuery(query, AdminProduct::class.java).build()
        adapter = AdminProductAdapter(options);

        val adminRView : RecyclerView = findViewById(R.id.adminRView);
        val orientation = resources.configuration.orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            adminRView.layoutManager = GridLayoutManager(this,2);
        }
        else{
            adminRView.layoutManager = GridLayoutManager(this,4);
        }

        adminRView.adapter = adapter;
    }
    override fun onStart() {
        super.onStart();
        adapter?.startListening();
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_admin_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id === R.id.optionMenu) {
            val toAdminProductActivity = Intent(this, AdminProductActivity::class.java)
            startActivity(toAdminProductActivity)
        }
        if (id === R.id.optionAdd) {
            val toAddToyActivity = Intent(this, AddToyActivity::class.java)
            startActivity(toAddToyActivity)
        }
        if (id === R.id.optionLogout) {
            val toLogin = Intent(this, LoginUserActivity::class.java)
            startActivity(toLogin)
            Toast.makeText(applicationContext, "Logged Out Successfully....", Toast.LENGTH_SHORT).show()
        }
        return true
    }
}