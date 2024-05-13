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

class RegisterUserActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth;
    var validEmail = true;
    var validPassword = true;
    var validConfirmPassword = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        val toLogin: TextView = findViewById(R.id.toLogin);
        toLogin.setOnClickListener {
            val toLoginUserActivity = Intent(this, LoginUserActivity::class.java);
            startActivity(toLoginUserActivity);
        }

        auth = FirebaseAuth.getInstance();

        val registerBtn : Button = findViewById(R.id.btnRegister);
        registerBtn.setOnClickListener{
            val userEmail : EditText = findViewById(R.id.userEmail);
            val userPassword : EditText = findViewById(R.id.userPassword);
            val userConfirmPassword : EditText = findViewById(R.id.userConfirmPassword);

            val email = userEmail.text.toString();
            val password = userPassword.text.toString();
            val confirmPassword = userConfirmPassword.text.toString();

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
                userPassword.error = "Password cannot be empty..."
                validPassword = false
            } else if (!password.matches("^[a-zA-Z0-9]{6,}\$".toRegex())) {
                userPassword.requestFocus()
                userPassword.error = "Password cannot be less than 6 characters or numbers..."
                validPassword = false
            } else {
                validPassword = true
            }

            if (confirmPassword.length == 0) {
                userConfirmPassword.requestFocus()
                userConfirmPassword.error = "Card number cannot be empty..."
                validConfirmPassword = false
            } else if (!confirmPassword.matches("^[a-zA-Z0-9]{6,}\$".toRegex())) {
                userConfirmPassword.requestFocus()
                userConfirmPassword.error = "Password cannot be less than 6 characters or numbers..."
                validConfirmPassword = false
            } else if(password != confirmPassword) {
                userConfirmPassword.requestFocus()
                userConfirmPassword.error = "Password and Confirm Password must be same..."
                validConfirmPassword = false
            }
            else {
                validConfirmPassword = true
            }

            if(validEmail && validPassword && validConfirmPassword)
            {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val toProducts = Intent(this, ProductActivity::class.java)
                            startActivity(toProducts);
                            Toast.makeText(this, "Registered Successfully...", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "You are already registered. Try Logging In...", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        }
    }
}