package com.example.androidmessenger

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidmessenger.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
        }

        binding.signUpBtn.setOnClickListener{
            val email = binding.emailEt.text.toString()
            val password = binding.passEt.text.toString()
            val username = binding.usernameEt.text.toString()
            val chats = ""
            if (email.isEmpty()||password.isEmpty()||username.isEmpty()){
                Toast.makeText(applicationContext,"Fields cannot be empty",Toast.LENGTH_SHORT).show()
            } else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            val userInfo = hashMapOf(
                                "email" to email,
                                "username" to username,
                                "chats" to chats
                            )
                            FirebaseDatabase.getInstance().reference.child("Users").child(FirebaseAuth.getInstance().currentUser!!.uid)
                                .setValue(userInfo)

                            startActivity(Intent(this,MainActivity::class.java))
                        }
                    }
            }
        }
    }
}