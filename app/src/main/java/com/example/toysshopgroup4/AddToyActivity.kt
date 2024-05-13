package com.example.toysshopgroup4

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddToyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_toy)

        var formToyBrand : EditText = findViewById(R.id.formToyBrand);
        var formToyName : EditText = findViewById(R.id.formToyName);
        var formToyDescription : EditText = findViewById(R.id.formToyDescription);
        var formToyPrice : EditText = findViewById(R.id.formToyPrice);
        var formToyImage : EditText = findViewById(R.id.formToyImage);

        var formAddButton : Button = findViewById(R.id.formAddButton);

        formAddButton.setOnClickListener {

            val databaseRef = FirebaseDatabase.getInstance().getReference("toys");

            databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var toyId = dataSnapshot.childrenCount+1;
                    databaseRef.child(toyId.toString()).child("name").setValue(formToyName.getText().toString());
                    databaseRef.child(toyId.toString()).child("brand").setValue(formToyBrand.getText().toString());
                    databaseRef.child(toyId.toString()).child("description").setValue(formToyDescription.getText().toString());
                    databaseRef.child(toyId.toString()).child("price").setValue(formToyPrice.getText().toString());
                    databaseRef.child(toyId.toString()).child("image").setValue(formToyImage.getText().toString())

                    Toast.makeText(this@AddToyActivity,"Toy Added Successfully...",Toast.LENGTH_SHORT).show();

                    val toMenu = Intent(this@AddToyActivity, AdminProductActivity::class.java);
                    startActivity(toMenu);
                }
                override fun onCancelled(databaseError: DatabaseError) {}

                })
        }
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