package com.doiliomatsinhe.dcvilains.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.dcvilains.databinding.CharacterItemBinding
import com.doiliomatsinhe.dcvilains.model.Character

class CharacterViewHolder(private val binding: CharacterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Character, clickListener: CharacterClickListener) {
        binding.character = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): CharacterViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = CharacterItemBinding.inflate(inflater, parent, false)
            return CharacterViewHolder(binding)

        }
    }

}