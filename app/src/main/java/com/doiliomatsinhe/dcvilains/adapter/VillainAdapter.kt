package com.doiliomatsinhe.dcvilains.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.dcvilains.adapter.VillainAdapter.ViewHolder.Companion.from
import com.doiliomatsinhe.dcvilains.databinding.VillainsItemBinding
import com.doiliomatsinhe.dcvilains.model.Villain

class VillainAdapter(private val clickListener: VillainClickListener) :
    ListAdapter<Villain, VillainAdapter.ViewHolder>(VillainDiffUtilCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return from(parent)
    }

    class ViewHolder private constructor(private val binding: VillainsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Villain, clickListener: VillainClickListener) {
            binding.villain = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = VillainsItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding)

            }
        }
    }

}

class VillainDiffUtilCallback : DiffUtil.ItemCallback<Villain>() {
    override fun areItemsTheSame(oldItem: Villain, newItem: Villain): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Villain, newItem: Villain): Boolean {
        return oldItem == newItem
    }
}

class VillainClickListener(val clickListener: (villain: Villain) -> Unit) {
    fun onClick(villain: Villain) = clickListener(villain)
}