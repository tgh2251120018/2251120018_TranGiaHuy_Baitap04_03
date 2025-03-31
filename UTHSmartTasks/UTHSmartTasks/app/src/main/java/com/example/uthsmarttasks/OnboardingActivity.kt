package com.example.uthsmarttasks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val nextButton: Button = findViewById(R.id.btn_next)
        nextButton.setOnClickListener {
            val intent = Intent(this, OnboardingActivity2::class.java)
            startActivity(intent)
        }
    }
}
