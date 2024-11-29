package com.example.quickmart

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.quickmart.databinding.IntroMainBinding

class IntroActivity : AppCompatActivity() {
    lateinit var binding: IntroMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = IntroMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.introScrinBtn.setOnClickListener {
            startActivity(Intent(this, LoginPage::class.java))
        }
    }
}