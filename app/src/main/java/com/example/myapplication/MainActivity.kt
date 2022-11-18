package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtReadMe.setOnClickListener {
            if (binding.txtReadMeContent.visibility == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(binding.cardReadMe, AutoTransition())
                binding.txtReadMeContent.visibility = View.GONE
            } else {
                TransitionManager.beginDelayedTransition(binding.cardReadMe, AutoTransition())
                binding.txtReadMeContent.visibility = View.VISIBLE
            }
        }

        binding.txtWeb.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
}