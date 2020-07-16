package com.doiliomatsinhe.dcvilains.adapter


import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.dcvilains.model.Character

class CharacterAdapter (private val clickListener: CharacterClickListener) :
    PagingDataAdapter<Character, RecyclerView.ViewHolder>(CharacterDiffUtilCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as CharacterViewHolder).bind(it, clickListener) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CharacterViewHolder.from(parent)
    }

}

class CharacterDiffUtilCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}

class CharacterClickListener (val clickListener: (character: Character) -> Unit) {
    fun onClick(character: Character) = clickListener(character)
}