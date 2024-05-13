package com.example.toysshopgroup4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginUserActivity : AppCompatActivity() {

    var auth = FirebaseAuth.getInstance();
    var validEmail = true;
    var validPassword = true;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_user)

        val toRegister: TextView = findViewById(R.id.toRegister);
        toRegister.setOnClickListener{
            val toRegisterUserActivity = Intent(this,RegisterUserActivity::class.java);
            startActivity(toRegisterUserActivity);
        }

        val loginBtn: Button = findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener{
            val userEmail : EditText = findViewById(R.id.userEmail);
            val userPassword : EditText = findViewById(R.id.userPassword);

            val email = userEmail.text.toString();
            val password = userPassword.text.toString();

            if (email.length == 0) {
                userEmail.requestFocus()
                userEmail.error = "Email cannot be empty..."
                validEmail = false
            } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())) {
                userEmail.requestFocus()
                userEmail.error = "Please enter a valid email..."
                validEmail = false
            } else {
                validEmail = true
            }

            if (password.length == 0) {
                userPassword.requestFocus()
                userPassword.error = "Pass cannot be empty..."
                validPassword = false
            } else if (!password.matches("^[a-zA-Z0-9]{6,}\$".toRegex())) {
                userPassword.requestFocus()
                userPassword.error = "Password cannot be less than 6 characters or numbers..."
                validPassword = false
            } else {
                validPassword = true
            }

            if(validEmail && validPassword)
            {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            if(auth.currentUser?.uid.toString() == "wmf3AQRseTdU7E0ilzQDkty0EOC3")
                            {
                                val toAdminProducts = Intent(this, AdminProductActivity::class.java)
                                startActivity(toAdminProducts);
                                Toast.makeText(this, "Welcome Admin !!!", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                val toProducts = Intent(this, ProductActivity::class.java)
                                startActivity(toProducts);
                                Toast.makeText(this, "Logged In Successfully...", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else
                        {
                            Toast.makeText(this, "Incorrect Credentials. Try Again...", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        }
    }
}