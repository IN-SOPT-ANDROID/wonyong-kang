package org.sopt.sample.ui.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.data.entity.User
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.ui.login.LoginActivity

class SignUpActivity : AppCompatActivity() {
    private val signUpViewModel: SignUpViewModel by viewModels()
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView<ActivitySignUpBinding?>(this, R.layout.activity_sign_up)
                .apply {
                    vm = signUpViewModel
                    lifecycleOwner = this@SignUpActivity
                }

        collectUiState()
    }

    private fun collectUiState() {
        signUpViewModel.signUpEvent.flowWithLifecycle(lifecycle)
            .onEach(this::eventHandler)
            .launchIn(lifecycleScope)
    }

    private fun eventHandler(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.Fail -> showSnackBar(getString(R.string.sign_up_fail))
            is UiEvent.Success -> {
                showSnackBar(getString(R.string.sign_up_success))
                sendUserInfo()
            }
        }
    }

    private fun sendUserInfo() {
        Intent(this, LoginActivity::class.java).apply {
            val user = User(
                id = signUpViewModel.idText.value,
                pw = signUpViewModel.pwText.value,
                mbti = signUpViewModel.mbtiText.value
            )
            putExtra("user", user)
        }.also { intent ->
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun showSnackBar(snackBarText: String) {
        Snackbar.make(binding.root, snackBarText, Snackbar.LENGTH_SHORT).show()
    }
}
