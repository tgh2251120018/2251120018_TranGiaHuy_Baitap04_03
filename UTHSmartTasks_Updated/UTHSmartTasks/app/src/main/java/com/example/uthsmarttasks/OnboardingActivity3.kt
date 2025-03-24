package com.example.uthsmarttasks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class OnboardingActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding3)

        val backButton: Button = findViewById(R.id.btn_back3)
        val nextButton: Button = findViewById(R.id.btn_next3)

        backButton.setOnClickListener {
            finish()
        }

        nextButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
