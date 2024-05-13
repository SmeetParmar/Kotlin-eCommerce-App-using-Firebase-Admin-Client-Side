package com.example.toysshopgroup4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.toysshopgroupclass.CartAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartActivity : AppCompatActivity() {

    private var adapter: CartAdapter? = null
    var gTotal = 0.00
    val currentSignedInuser = FirebaseAuth.getInstance().currentUser?.uid.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val grandTotal : TextView = findViewById(R.id.grandTotal);

        val btnCheckout : Button = findViewById(R.id.btnCheckout);


        val query = FirebaseDatabase.getInstance().reference.child("cart").child(currentSignedInuser).orderByChild("removed").equalTo("false");
        val options = FirebaseRecyclerOptions.Builder<Cart>().setQuery(query, Cart::class.java).build()
        adapter = CartAdapter(options);
        val cartRecyclerView : RecyclerView = findViewById(R.id.cartRecyclerView);

        cartRecyclerView.layoutManager = LinearLayoutManager(this);

        cartRecyclerView.adapter = adapter;

        var FirebaseCart = FirebaseDatabase.getInstance().reference.child("cart").child(currentSignedInuser);
        FirebaseCart.addListenerForSingleValueEvent(object : ValueEventListener {
            var stringPrice: String? = null
            var stringQuantity: String? = null
            var price: String? = null
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.childrenCount.toInt() == 0) {
                    Toast.makeText(this@CartActivity, "Your cart is empty...", Toast.LENGTH_LONG).show()
                }
                else
                {
                    for (i in 1 until snapshot.childrenCount+1) {
                        var toyId = "toy$i"
                        if(snapshot.child(toyId).child("removed").value.toString() == "false")
                        {
                            stringPrice = snapshot.child(toyId).child("price").value.toString()
                            stringQuantity = snapshot.child(toyId).child("quantity").value.toString()
                            price = stringPrice!!.substring(0, stringPrice!!.indexOf("$"))
                            //Log.i("Price",price);
                            gTotal += price!!.toDouble() * stringQuantity!!.toDouble();
                        }
                    }
                    if(gTotal == 0.00)
                    {
                        Toast.makeText(this@CartActivity, "Your cart is empty...", Toast.LENGTH_LONG).show()
                    }
                    grandTotal.setText("Grand Total : " +  String.format("%.2f", gTotal) + "$");
                }

            }

            override fun onCancelled(error: DatabaseError) {}
        })
        btnCheckout.setOnClickListener{
            val toCheckout = Intent(this, CheckoutActivity::class.java)
            toCheckout.putExtra("grandTotal",gTotal.toString());
            startActivity(toCheckout);
            //Log.i("msg",gTotal.toString());
        }
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