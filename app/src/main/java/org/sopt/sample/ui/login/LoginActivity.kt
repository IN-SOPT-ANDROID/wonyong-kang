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
            loginViewModel.idText.value = id
            loginViewModel.pwText.value = pw
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
    }

    private fun signUpButtonOnClick() {
        binding.btnLoginSignUp.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also { intent ->
                startForResult.launch(intent)
            }
        }
    }
}
