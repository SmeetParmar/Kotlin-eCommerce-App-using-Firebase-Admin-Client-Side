package com.example.toysshopgroup4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getStarted:Button = findViewById(R.id.getStartedBtn);
        getStarted.setOnClickListener{
            val toLoginActivity = Intent(this,LoginUserActivity::class.java);
            startActivity(toLoginActivity);
        }
    }
}