package org.sopt.sample.ui.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignUpBinding
import org.sopt.sample.ui.login.LoginActivity
import org.sopt.sample.util.showSnackBar

@AndroidEntryPoint
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
            is UiEvent.Fail -> binding.root.showSnackBar(getString(R.string.sign_up_fail))
            is UiEvent.Success -> sendUserInfo()
        }
    }

    private fun sendUserInfo() {
        Intent(this, LoginActivity::class.java).apply {
            putExtra("isShowSnackBar", true)
        }.also { intent ->
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
