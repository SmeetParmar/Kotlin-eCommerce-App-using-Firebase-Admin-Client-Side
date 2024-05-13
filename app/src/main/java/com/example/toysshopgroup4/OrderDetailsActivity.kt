package com.example.toysshopgroup4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class OrderDetailsActivity : AppCompatActivity() {
    val currentSignedInUser = FirebaseAuth.getInstance().currentUser?.uid.toString()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_details)

        var orderDetails: TextView = findViewById(R.id.orderID);
        var detailFullName: TextView = findViewById(R.id.detailFullName);
        var detailMobileNumber: TextView = findViewById(R.id.detailMobileNumber);
        var detailEmailAddress: TextView = findViewById(R.id.detailEmailAddress);
        var detailShippingAddress: TextView = findViewById(R.id.detailShippingAddress);
        var detailCardNumber: TextView = findViewById(R.id.detailCardNumber);
        var detailExpiryDate: TextView = findViewById(R.id.detailExpiryDate);
        var detailSecurityCode: TextView = findViewById(R.id.detailSecurityCode);
        var detailCardHolderName: TextView = findViewById(R.id.detailCardHolderName);
        var grandTotal: TextView = findViewById(R.id.detailGrandTotal);
        var productDetails: TextView = findViewById(R.id.productDetails);

        orderDetails.text = "Order 1";
        detailFullName.text = intent.getStringExtra("fullName");
        detailMobileNumber.text = intent.getStringExtra("mobileNumber");
        detailEmailAddress.text = intent.getStringExtra("emailAddress");
        detailCardNumber.text = intent.getStringExtra("cardNumber");
        detailExpiryDate.text = intent.getStringExtra("expiryDate");
        detailSecurityCode.text = intent.getStringExtra("securityCode");
        detailCardHolderName.text = intent.getStringExtra("cardHolderName");
        grandTotal.text = intent.getStringExtra("grandTotal");
        detailShippingAddress.text = intent.getStringExtra("shippingAddress");

        var databaseRef = FirebaseDatabase.getInstance().reference.child("orders").child(currentSignedInUser).child("order1");
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
               
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}