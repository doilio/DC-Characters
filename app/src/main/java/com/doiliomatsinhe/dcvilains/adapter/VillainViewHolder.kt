package com.doiliomatsinhe.dcvilains.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.dcvilains.databinding.VillainsItemBinding
import com.doiliomatsinhe.dcvilains.model.Villain

class VillainViewHolder(private val binding: VillainsItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Villain, clickListener: VillainClickListener) {
        binding.villain = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): VillainViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = VillainsItemBinding.inflate(inflater, parent, false)
            return VillainViewHolder(binding)

        }
    }

}