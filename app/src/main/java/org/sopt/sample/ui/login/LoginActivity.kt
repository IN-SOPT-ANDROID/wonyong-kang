package org.sopt.sample.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.data.entity.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityLoginBinding
import org.sopt.sample.ui.MainActivity
import org.sopt.sample.ui.signup.SignUpActivity

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val user = result.data?.getSerializableExtra("user") as User
            loginViewModel.setUserInfo(user)
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
        collectLoginEvent()
        signUpButtonOnClick()
    }

    private fun collectLoginEvent() {
        loginViewModel.loginEvent.flowWithLifecycle(lifecycle)
            .onEach { isNextView ->
                if (isNextView) {
                    Toast.makeText(this, "로그인에 성공했습니다", Toast.LENGTH_SHORT).show()
                    Intent(this, MainActivity::class.java)
                        .apply {
                            putExtra("user", loginViewModel.userInfo.value)
                        }
                        .also { intent ->
                            startActivity(intent)
                            finish()
                        }
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun signUpButtonOnClick() {
        binding.btnLoginSignUp.setOnClickListener {
            Intent(this, SignUpActivity::class.java).also { intent ->
                startForResult.launch(intent)
            }
        }
    }
}
