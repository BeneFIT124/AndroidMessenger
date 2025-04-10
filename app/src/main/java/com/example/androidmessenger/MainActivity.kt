package com.example.androidmessenger

import android.app.Activity
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.view.inputmethod.InputBinding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.androidmessenger.bottomnav.chats.ChatFragment
import com.example.androidmessenger.bottomnav.new_chat.NewChatFragment
import com.example.androidmessenger.bottomnav.profile.ProfileFragment
import com.example.androidmessenger.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if( FirebaseAuth.getInstance().currentUser==null){
            startActivity(Intent(this, LoginActivity::class.java))
        }


        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, ChatFragment())
            .commit()

        binding.bottomNav.selectedItemId = R.id.chats

        val fragmentMap = mapOf(
            R.id.chats to ChatFragment(),
            R.id.new_chat to NewChatFragment(),
            R.id.profile to ProfileFragment()
        )

        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment = fragmentMap[item.itemId]
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, it)
                    .commit()
            }
            true
        }
    }
}