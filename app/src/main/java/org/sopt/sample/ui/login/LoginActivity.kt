package org.sopt.sample.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivityLoginBinding
import org.sopt.sample.ui.MainActivity
import org.sopt.sample.ui.signup.SignUpActivity
import org.sopt.sample.util.showSnackBar
import org.sopt.sample.util.showToast

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val isShowSnackBar = result.data?.getBooleanExtra("isShowSnackBar", false) ?: false
            if (isShowSnackBar) binding.root.showSnackBar(getString(R.string.sign_up_success))
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
        loginViewModel.isAutoLogin()
    }

    private fun collectLoginEvent() {
        loginViewModel.loginEvent.flowWithLifecycle(lifecycle)
            .onEach { isNextView ->
                if (isNextView) {
                    showToast(getString(R.string.sign_up_success))
                    Intent(this, MainActivity::class.java)
                        .also { intent ->
                            startActivity(intent)
                            finish()
                        }
                } else {
                    showToast(getString(R.string.sign_up_fail))
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
