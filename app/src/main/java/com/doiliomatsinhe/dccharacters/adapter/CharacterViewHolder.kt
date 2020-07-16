package com.doiliomatsinhe.dccharacters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.dccharacters.databinding.CharacterItemBinding
import com.doiliomatsinhe.dccharacters.model.Character

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