package com.doiliomatsinhe.dccharacters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.doiliomatsinhe.dccharacters.R
import com.doiliomatsinhe.dccharacters.adapter.PowerStatAdapter.ViewHolder.Companion.from
import com.doiliomatsinhe.dccharacters.databinding.PowerStatItemBinding
import com.doiliomatsinhe.dccharacters.model.Powerstats

class PowerStatAdapter : RecyclerView.Adapter<PowerStatAdapter.ViewHolder>() {

    var powerstats: Powerstats = Powerstats()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder constructor(var binding: PowerStatItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(powerstats: Powerstats, position: Int) {
            val context = binding.root.context
            when (position) {
                0 -> {
                    binding.powerstatValue.progressColor =
                        ContextCompat.getColor(context, R.color.colorIntelligence)
                    binding.powerstatText.text = context.getString(R.string.intelligence)
                    binding.powerstatValue.setProgress(powerstats.intelligence.toFloat(), true)
                }
                1 -> {
                    binding.powerstatValue.progressColor =
                        ContextCompat.getColor(context, R.color.colorCombat)
                    binding.powerstatText.text = context.getString(R.string.combat)
                    binding.powerstatValue.setProgress(powerstats.combat.toFloat(), true)
                }
                2 -> {

                    binding.powerstatValue.progressColor =
                        ContextCompat.getColor(context, R.color.colorDurability)
                    binding.powerstatText.text = context.getString(R.string.durability)
                    binding.powerstatValue.setProgress(powerstats.durability.toFloat(), true)
                }
                3 -> {

                    binding.powerstatValue.progressColor =
                        ContextCompat.getColor(context, R.color.colorPower)
                    binding.powerstatText.text = context.getString(R.string.power)
                    binding.powerstatValue.setProgress(powerstats.power.toFloat(), true)
                }
                4 -> {

                    binding.powerstatValue.progressColor =
                        ContextCompat.getColor(context, R.color.colorSpeed)
                    binding.powerstatText.text = context.getString(R.string.speed)
                    binding.powerstatValue.setProgress(powerstats.speed.toFloat(), true)
                }
                5 -> {

                    binding.powerstatValue.progressColor =
                        ContextCompat.getColor(context, R.color.colorStrength)
                    binding.powerstatText.text = context.getString(R.string.strength)
                    binding.powerstatValue.setProgress(powerstats.strength.toFloat(), true)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PowerStatItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return from(parent)

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(powerstats, position)
    }


    override fun getItemCount() = 6

}