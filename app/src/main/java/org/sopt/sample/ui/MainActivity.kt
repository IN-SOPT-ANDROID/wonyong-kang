package org.sopt.sample.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.data.datasource.local.UserDataSource
import com.example.data.entity.User
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var localDataSource: UserDataSource
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getUserContent()
        logOutOnClick()
    }

    private fun getUserContent() {
        intent.apply {
            val user = getSerializableExtra("user") as User
            binding.tvMainName.text = String.format("이름: ${user.id}")
            binding.tvMainMbti.text = String.format("mbti: ${user.mbti}")
        }
    }

    private fun logOutOnClick() {
        binding.btnMainLogOut.setOnClickListener {
            localDataSource.setAutoLogin(false)
            Toast.makeText(this, "자동 로그인 해제", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
