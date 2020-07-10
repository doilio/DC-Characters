package com.doiliomatsinhe.dcvilains.ui.villaindetail

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.doiliomatsinhe.dcvilains.databinding.FragmentVillainDetailBinding
import com.doiliomatsinhe.dcvilains.model.Villain
import com.doiliomatsinhe.dcvilains.repository.VillainRepository
import com.doiliomatsinhe.dcvilains.utils.ColorUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VillainDetailFragment : Fragment() {

    private lateinit var binding: FragmentVillainDetailBinding
    private lateinit var viewModel: VillainDetailViewModel
    private lateinit var arguments: VillainDetailFragmentArgs

    @Inject
    lateinit var repository: VillainRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVillainDetailBinding.inflate(inflater, container, false)

        arguments = VillainDetailFragmentArgs.fromBundle(requireArguments())

        setupActionBar(arguments)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()

        viewModel.getVillain().observe(viewLifecycleOwner, Observer {
            it?.let {
                populateUI(it)
            }
        })

    }

    private fun populateUI(villain: Villain) {
        Glide.with(this).load(villain.images.sm).into(binding.profileVillain)

        // Populate Appearance
        val appearance = villain.appearance
        val villainRace = when (appearance.race) {
            null -> " with unknown race"
            else->" ${appearance.race}"
        }
        val villainHair = when (appearance.hairColor) {
            "-" -> "."
            "No Hair" -> " and ${appearance.hairColor}."
            else->" and ${appearance.hairColor} hair."
        }
        val villainEyeColor = when (appearance.eyeColor) {
            "-" -> ""
            else->", has ${appearance.eyeColor} eyes"
        }

        val appearanceText = "${villain.name} is a ${appearance.gender}$villainRace, who is ${appearance.height[1]} tall, weights ${appearance.weight[1]}$villainEyeColor$villainHair"
        binding.textAppearance.text = appearanceText

        // TODO Populate Biography
        // TODO Populate Affiliations
        // TODO Populate Relatives
    }


    private fun setupActionBar(arguments: VillainDetailFragmentArgs) {
        ((activity as AppCompatActivity).supportActionBar)?.title = arguments.villainName
        ((activity as AppCompatActivity).supportActionBar)?.setBackgroundDrawable(
            ColorDrawable(
                arguments.cardColor
            )
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((activity as AppCompatActivity).window)?.statusBarColor = ColorUtils.manipulateColor(
                arguments.cardColor, 0.50f
            )
        }
    }

    private fun initComponents() {
        val factory = VillainDetailViewModelFactory(repository, arguments.villainId)
        viewModel = ViewModelProvider(this, factory).get(VillainDetailViewModel::class.java)
    }


}