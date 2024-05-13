package com.example.toysshopgroup4

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class EditToyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_toy)

        var formToyBrand : EditText = findViewById(R.id.formToyBrand);
        var formToyName : EditText = findViewById(R.id.formToyName);
        var formToyDescription : EditText = findViewById(R.id.formToyDescription);
        var formToyPrice : EditText = findViewById(R.id.formToyPrice);
        var formToyImage : EditText = findViewById(R.id.formToyImage);

        var formUpdateButton : Button = findViewById(R.id.formUpdateButton);

        formToyBrand.setText(intent.getStringExtra("toyBrand"));
        formToyName.setText(intent.getStringExtra("toyName"));
        formToyDescription.setText(intent.getStringExtra("toyDescription"));
        formToyPrice.setText(intent.getStringExtra("toyPrice"));
        formToyImage.setText(intent.getStringExtra("toyImage"));

        formUpdateButton.setOnClickListener {

            val map: MutableMap<String, Any> = HashMap()
            map["name"] = formToyName.getText().toString()

            FirebaseDatabase.getInstance().getReferenceFromUrl(intent.getStringExtra("childId")!!)
                .updateChildren(map)
                .addOnSuccessListener {
                    Toast.makeText(this,"Data Updated Successfully.",Toast.LENGTH_SHORT).show()
                    val toAdminProductActivity = Intent(this,AdminProductActivity::class.java)
                    startActivity(toAdminProductActivity)
                }
                .addOnFailureListener {
                    Toast.makeText(this,"Error while updating data.",Toast.LENGTH_SHORT).show()
                }
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