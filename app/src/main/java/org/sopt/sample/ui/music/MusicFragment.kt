package org.sopt.sample.ui.music

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.sopt.sample.databinding.FragmentMusicBinding

@AndroidEntryPoint
class MusicFragment : Fragment() {
    private lateinit var musicAdapter: MusicAdapter
    private var _binding: FragmentMusicBinding? = null
    private val binding: FragmentMusicBinding
        get() = requireNotNull(_binding) { "${this.javaClass.name} binding error" }
    private val musicViewModel: MusicViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        collectMusics()
    }

    private fun initAdapter() {
        musicAdapter = MusicAdapter()
        binding.rvMusic.adapter = musicAdapter
    }

    private fun collectMusics() {
        musicViewModel.uiState.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach(musicAdapter::submitList)
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
