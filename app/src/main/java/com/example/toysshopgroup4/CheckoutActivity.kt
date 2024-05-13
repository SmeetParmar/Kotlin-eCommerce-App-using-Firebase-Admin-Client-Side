package com.example.toysshopgroup4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CheckoutActivity : AppCompatActivity() {

    val currentSignedInUser = FirebaseAuth.getInstance().currentUser?.uid.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val firstName : EditText = findViewById(R.id.firstName);
        val lastName : EditText = findViewById(R.id.lastName);
        val mobileNumber : EditText = findViewById(R.id.mobileNumber);
        val emailAddress : EditText = findViewById(R.id.emailAddress);

        val address : EditText = findViewById(R.id.address);
        val city : EditText = findViewById(R.id.city);
        val province : EditText = findViewById(R.id.province);
        val country : EditText = findViewById(R.id.country);
        val postalCode : EditText = findViewById(R.id.postalCode);

        val cardNumber : EditText = findViewById(R.id.cardNumber);
        val expiryDate : EditText = findViewById(R.id.expiryDate);
        val securityCode : EditText = findViewById(R.id.securityCode);
        val cardHolderName : EditText = findViewById(R.id.cardHolderName);

        val placeOrder : Button = findViewById(R.id.placeOrder);

        val grandTotal = intent.getStringExtra("grandTotal").toString();

        var validFirstName = true;
        var validLastName = true;
        var validMobileNumber = true;
        var validEmailAddress = true;

        var validAddress = true;
        var validCity = true;
        var validProvince = true;
        var validCountry = true;
        var validPostalCode = true;

        var validCardNumber = true;
        var validExpiryDate = true;
        var validSecurityCode = true;
        var validCardHolderName = true;

        placeOrder.setOnClickListener{
            if (firstName.text.length == 0) {
                firstName.requestFocus()
                firstName.error = "First name cannot be empty..."
                validFirstName = false
            } else if (!firstName.text.toString().matches("[a-zA-Z]+".toRegex())) {
                firstName.requestFocus()
                firstName.error = "Please enter only alphabets..."
                validFirstName = false
            } else {
                validFirstName = true
            }

            if (lastName.text.length == 0) {
                lastName.requestFocus()
                lastName.error = "Last name cannot be empty..."
                validLastName = false
            } else if (!lastName.text.toString().matches("[a-zA-Z]+".toRegex())) {
                lastName.requestFocus()
                lastName.error = "Please enter only alphabets..."
                validLastName = false
            } else {
                validLastName = true
            }

            val mobile = mobileNumber.text.toString()
            if (mobile.length == 0) {
                mobileNumber.requestFocus()
                mobileNumber.error = "Mobile Number cannot be empty..."
                validMobileNumber = false
            } else if (!mobile.matches("^(\\+?1\\s?)?(\\()?\\d{3}(\\))?(-|\\s)?\\d{3}(-|\\s)\\d{4}$".toRegex())) {
                mobileNumber.requestFocus()
                mobileNumber.error = "Please enter a valid Canadian mobile number..."
                validMobileNumber = false
            } else {
                validMobileNumber = true
            }

            if (emailAddress.text.length == 0) {
                emailAddress.requestFocus()
                emailAddress.error = "Email cannot be empty..."
                validEmailAddress = false
            } else if (!emailAddress.text.toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()) ) {
                emailAddress.requestFocus()
                emailAddress.error = "Please enter a valid email address..."
                validEmailAddress = false
            } else {
                validEmailAddress = true
            }



            if (address.text.length == 0) {
                address.requestFocus()
                address.error = "Address cannot be empty..."
                validAddress = false
            } else if (!address.text.toString().matches("\\d+\\s+[a-zA-Z]+\\s+[a-zA-Z]+".toRegex())) {
                address.requestFocus()
                address.error = "Please enter valid address"
                validAddress = false
            } else {
                validAddress = true
            }

            if (city.text.length == 0) {
                city.requestFocus()
                city.error = "City cannot be empty..."
                validCity = false
            } else if (!city.text.toString().matches("[a-zA-Z]+".toRegex())) {
                city.requestFocus()
                city.error = "City cannot contain digits..."
                validCity = false
            } else {
                validCity = true
            }

            if (province.text.length == 0) {
                province.requestFocus()
                province.error = "Province cannot be empty..."
                validProvince = false
            } else if (!province.text.toString().matches("[a-zA-Z]+".toRegex())) {
                province.requestFocus()
                province.error = "Province cannot contain digits..."
                validProvince = false
            } else {
                validProvince = true
            }

            if (country.text.length == 0) {
                country.requestFocus()
                country.error = "Country cannot be empty..."
                validCountry = false
            } else if (!country.text.toString().matches("[a-zA-Z]+".toRegex())) {
                country.requestFocus()
                country.error = "Country cannot contain digits..."
                validCountry = false
            } else {
                validCountry = true
            }

            if (postalCode.text.length == 0) {
                postalCode.requestFocus()
                postalCode.error = "Postal code cannot be empty..."
                validPostalCode = false
            } else if (!postalCode.text.toString().matches("[A-Za-z]\\d[A-Za-z] \\d[A-Za-z]\\d".toRegex())) {
                postalCode.requestFocus()
                postalCode.error = "Please enter a valid postal code..."
                validPostalCode = false
            } else {
                validPostalCode = true
            }



            if (cardNumber.text.length == 0) {
                cardNumber.requestFocus()
                cardNumber.error = "Card number cannot be empty..."
                validCardNumber = false
            } else if (!cardNumber.text.toString().matches("\\d{16}".toRegex())) {
                cardNumber.requestFocus()
                cardNumber.error = "Please enter a valid card number of 16 digits..."
                validCardNumber = false
            } else {
                validCardNumber = true
            }

            if (expiryDate.text.length == 0) {
                expiryDate.requestFocus()
                expiryDate.error = "Expiry date cannot be empty..."
                validExpiryDate = false
            } else if (!expiryDate.text.toString().matches("(0[1-9]|1[0-2])/(\\d{2})".toRegex())) {
                expiryDate.requestFocus()
                expiryDate.error = "Please enter a valid expiry date in MM/YY format..."
                validExpiryDate = false
            } else {
                validExpiryDate = true
            }

            if (securityCode.text.length == 0) {
                securityCode.requestFocus()
                securityCode.error = "Security Code cannot be empty..."
                validSecurityCode = false
            } else if (!securityCode.text.toString().matches("\\d{3}".toRegex())) {
                securityCode.requestFocus()
                securityCode.error = "Please enter a valid security code in 3 digits..."
                validSecurityCode = false
            } else {
                validSecurityCode = true
            }

            if (cardHolderName.text.length == 0) {
                cardHolderName.requestFocus()
                cardHolderName.error = "Cardholder name cannot be empty..."
                validCardHolderName = false
            } else if (cardHolderName.text.toString().matches("[a-zA-Z]".toRegex())) {
                cardHolderName.requestFocus()
                cardHolderName.error = "Card holder name cannot contain number..."
                validCardHolderName = false
            } else {
                validCardHolderName = true
            }

            if (true) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Order Summary")
                builder.setMessage("Are you sure you want to place your order?")
                builder.setPositiveButton("Place Order")
                { dialog, which ->
                    val builder2 = AlertDialog.Builder(this)
                    builder2.setTitle("Order Placed")
                    builder2.setMessage("Congratulations! Your order has been placed.")
                    builder2.setPositiveButton("Ok")
                    { dialog, which ->
                        val toMenu = Intent(this,ProductActivity::class.java)
                        startActivity(toMenu)
                        FirebaseDatabase.getInstance().reference.child("cart").child(currentSignedInUser).removeValue();
                    }

                    val dialog2 = builder2.create()
                    dialog2.show()

                    var databaseRef = FirebaseDatabase.getInstance().reference.child("orders").child(currentSignedInUser);
                    databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val totalOrders = dataSnapshot.childrenCount;

                            val orderId = totalOrders+1;
                            var orderDetails = databaseRef.child("order"+orderId.toString());
                            orderDetails.child("fullName").setValue(firstName.text.toString()+ " " +lastName.text.toString());
                            orderDetails.child("mobileNumber").setValue(mobileNumber.text.toString());
                            orderDetails.child("emailAddress").setValue(emailAddress.text.toString());

                            var shippingAdress = address.text.toString() + ", " + city.text.toString() + ", " + province.text.toString() + ", " + country.text.toString() + ", " + postalCode.text.toString();
                            orderDetails.child("shippingAddress").setValue(shippingAdress);

                            orderDetails.child("cardNumber").setValue(cardNumber.text.toString());
                            orderDetails.child("expiryDate").setValue(expiryDate.text.toString());
                            orderDetails.child("securityCode").setValue(securityCode.text.toString());
                            orderDetails.child("cardHolderName").setValue(cardHolderName.text.toString());
                            orderDetails.child("grandTotal").setValue(grandTotal);

                            var databaseRef2 =  FirebaseDatabase.getInstance().reference.child("cart").child(currentSignedInUser);
                            databaseRef2.addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot2: DataSnapshot) {
                                    var totalToys = dataSnapshot2.childrenCount;
                                    Log.i("msg",totalToys.toString());
                                    var id = 1;
                                    for (i in 1 until totalToys + 1) {
                                        var toyId = i;
                                        if(dataSnapshot2.child("toy"+toyId).child("removed").getValue().toString() == "false")
                                        {
                                            orderDetails.child("productDetails").child("toy"+id).child("name").setValue(dataSnapshot2.child("toy"+toyId).child("name").getValue());
                                            orderDetails.child("productDetails").child("toy"+id).child("image").setValue(dataSnapshot2.child("toy"+toyId).child("image").getValue());
                                            orderDetails.child("productDetails").child("toy"+id).child("price").setValue(dataSnapshot2.child("toy"+toyId).child("price").getValue());
                                            orderDetails.child("productDetails").child("toy"+id).child("quantity").setValue(dataSnapshot2.child("toy"+toyId).child("quantity").getValue());
                                            id++;
                                        }
                                    }

                                }

                                override fun onCancelled(databaseError: DatabaseError) {}

                                }
                            )


                        }
                        override fun onCancelled(databaseError: DatabaseError) { }


                    })


                }
                builder.setNegativeButton("Cancel")
                { dialog, which ->
                    Toast.makeText(this,"Order Cancelled...",Toast.LENGTH_LONG).show()
                }
                val dialog = builder.create()
                dialog.show()
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