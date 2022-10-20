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
import org.sopt.sample.databinding.ItemTitleBinding

class HomeAdapter : ListAdapter<Follower, RecyclerView.ViewHolder>(followerComparator) {
    private lateinit var inflater: LayoutInflater

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TITLE
            else -> CONTENT
        }
    }

    class FollowerViewHolder(
        private val binding: ItemFollowerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(follower: Follower) {
            binding.tvFollowerName.text = follower.name
            binding.ivFollowerProfile.load(follower.avatar) {
                fallback(R.drawable.ic_person)
                placeholder(R.drawable.ic_person)
                transformations(CircleCropTransformation())
            }
        }
    }

    class TitleViewHolder(binding: ItemTitleBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TITLE -> TitleViewHolder(ItemTitleBinding.inflate(inflater, parent, false))
            CONTENT -> FollowerViewHolder(ItemFollowerBinding.inflate(inflater, parent, false))
            else -> throw IllegalArgumentException("viewType : $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val current = getItem(position)
        when (holder) {
            is TitleViewHolder -> Unit
            is FollowerViewHolder -> holder.bind(current)
        }
    }

    companion object {
        const val TITLE = 0
        const val CONTENT = 1
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
