package org.sopt.sample.ui.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.data.entity.Music
import org.sopt.sample.R
import org.sopt.sample.databinding.ItemMusicBinding

class MusicAdapter : ListAdapter<Music, MusicAdapter.MusicViewHolder>(musicComparator) {
    private lateinit var inflater: LayoutInflater

    class MusicViewHolder(
        private val binding: ItemMusicBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(music: Music) {
            binding.music = music
            binding.ivMusicImg.load(music.image) {
                fallback(R.drawable.ic_person)
                placeholder(R.drawable.ic_person)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        return MusicViewHolder(ItemMusicBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    companion object {
        private val musicComparator = object : DiffUtil.ItemCallback<Music>() {
            override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
                return oldItem == newItem
            }
        }
    }
}
