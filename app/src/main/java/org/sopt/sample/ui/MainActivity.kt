package org.sopt.sample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getUserContent()
    }

    private fun getUserContent() {
        intent.apply {
            binding.tvMainName.text = String.format("이름: ${getStringExtra("id")}")
            binding.tvMainMbti.text = String.format("이름: ${getStringExtra("mbti")}")
        }
    }
}
