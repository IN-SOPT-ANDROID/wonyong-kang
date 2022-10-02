package org.sopt.sample.ui.signup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.sample.R
import org.sopt.sample.databinding.ActivitySignUpBinding

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
            is UiEvent.ShowSnackBar -> showSnackBar()
            is UiEvent.Success -> finish()
        }
    }

    private fun showSnackBar() {
        Snackbar.make(binding.root, getString(R.string.sign_up_success), Snackbar.LENGTH_SHORT).show()
    }
}
