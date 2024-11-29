package com.example.quickmart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quickmart.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.intentLoginPage.setOnClickListener {
            startActivity(Intent(this, LoginPage::class.java))
        }

        auth = FirebaseAuth.getInstance()

        binding.signINBtn.setOnClickListener {
            val name = binding.nameEdt.text.toString().trim()
            val email = binding.edtSignupEmail.text.toString().trim()
            val password = binding.edtSignupPassword.text.toString().trim()


            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,ChooseLocationActivity::class.java))
                    finish()
                }
                .addOnFailureListener { exception ->

                    Toast.makeText(this, "Sign up failed: ${exception.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }


    }
}