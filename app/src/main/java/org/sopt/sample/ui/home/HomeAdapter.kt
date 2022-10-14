package org.sopt.sample.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.data.entity.Follower
import org.sopt.sample.R
import org.sopt.sample.databinding.ItemFollowerBinding

class HomeAdapter : ListAdapter<Follower, HomeAdapter.FollowerViewHolder>(followerComparator) {
    private lateinit var inflater: LayoutInflater

    class FollowerViewHolder(
        private val binding: ItemFollowerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(follower: Follower) {
            binding.tvFollowerName.text = follower.name
            binding.ivFollowerProfile.load(follower.avatar) {
                placeholder(R.drawable.ic_person)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        val binding = ItemFollowerBinding.inflate(inflater, parent, false)
        return FollowerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    companion object {
        private val followerComparator = object : DiffUtil.ItemCallback<Follower>() {
            override fun areItemsTheSame(oldItem: Follower, newItem: Follower): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Follower, newItem: Follower): Boolean {
                return oldItem == newItem
            }
        }
    }
}
