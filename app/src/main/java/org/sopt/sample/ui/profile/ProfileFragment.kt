package org.sopt.sample.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.sample.databinding.FragmentProfileBinding
import org.sopt.sample.util.ContentUriRequestBody

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = requireNotNull(_binding) { "${this.javaClass.name} binding error" }
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var pickVisualMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        pickVisualMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    profileViewModel.setUri(uri)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectImgBtnOnClick()
        postBtnOnClick()
        collectMusicImg()
    }

    private fun collectMusicImg() {
        profileViewModel.musicImg.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { img ->
                binding.ivSelectedMusicImg.load(img)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun selectImgBtnOnClick() {
        binding.btnImgSelect.setOnClickListener {
            pickVisualMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun postBtnOnClick() {
        binding.btnPostImg.setOnClickListener {
            profileViewModel.postMusic(
                ContentUriRequestBody(
                    requireContext(),
                    profileViewModel.musicImg.value
                ).toFormData()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
