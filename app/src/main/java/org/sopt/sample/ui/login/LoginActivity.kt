package org.sopt.sample.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityLoginBinding
import org.sopt.sample.ui.MainActivity
import org.sopt.sample.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val id = result.data?.getStringExtra("id") ?: ""
            val pw = result.data?.getStringExtra("pw") ?: ""
            val mbti = result.data?.getStringExtra("mbti") ?: ""
            loginViewModel.idText.value = id
            loginViewModel.pwText.value = pw
            loginViewModel.mbtiText.value = mbti
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivityLoginBinding?>(this, R.layout.activity_login)
                .apply {
                    vm = loginViewModel
                    lifecycleOwner = this@LoginActivity
                }
        signUpButtonOnClick()
        loginButtonOnClick()
    }

    private fun loginButtonOnClick() {
        binding.btnLogin.setOnClickListener {
            Intent(this, MainActivity::class.java)
                .apply {
                    putExtra("id", loginViewModel.idText.value)
                    putExtra("mbti", loginViewModel.mbtiText.value)
                }
                .also { intent ->
                    startActivity(intent)
                    finish()
                }
        }
    }

    private fun signUpButtonOnClick() {
        binding.btnLoginSignUp.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also { intent ->
                startForResult.launch(intent)
            }
        }
    }
}
