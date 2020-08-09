package com.doiliomatsinhe.dccharacters.ui.characterdetail

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
import com.doiliomatsinhe.dccharacters.R
import com.doiliomatsinhe.dccharacters.adapter.PowerStatAdapter
import com.doiliomatsinhe.dccharacters.databinding.FragmentCharacterDetailBinding
import com.doiliomatsinhe.dccharacters.model.Character
import com.doiliomatsinhe.dccharacters.repository.CharacterRepository
import com.doiliomatsinhe.dccharacters.utils.ColorUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailBinding
    private lateinit var viewModel: CharacterDetailViewModel
    private lateinit var arguments: CharacterDetailFragmentArgs
    private lateinit var adapter: PowerStatAdapter

    @Inject
    lateinit var repository: CharacterRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)

        arguments = CharacterDetailFragmentArgs.fromBundle(requireArguments())

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

    private fun populateUI(character: Character) {
        Glide.with(this).load(character.images.sm).placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder).into(binding.profileCharacter)

        // Populate Appearance
        val appearance = character.appearance
        val villainRace = when (appearance.race) {
            null -> " with unknown race"
            else -> " ${appearance.race}"
        }
        val villainHair = when (appearance.hairColor) {
            "-" -> "."
            "No Hair" -> " and ${appearance.hairColor}."
            else -> " and ${appearance.hairColor} hair."
        }
        val villainEyeColor = when (appearance.eyeColor) {
            "-" -> ""
            else -> ", has ${appearance.eyeColor} eyes"
        }

        val appearanceText =
            "${character.name} is a ${appearance.gender}$villainRace, who is ${appearance.height[1]} tall, weights ${appearance.weight[1]}$villainEyeColor$villainHair"
        binding.textAppearance.text = appearanceText

        //  Populate Biography
        val biography = character.biography
        val work = character.work

        val fullName = when (biography.fullName) {
            "" -> "Fullname is unknown.\n"
            else -> "Fullname is ${biography.fullName}.\n"
        }
        val placeOfBirth = when (biography.placeOfBirth) {
            "-" -> "Place of birth is unknown.\n"
            else -> "Born in ${biography.placeOfBirth}.\n"
        }

        val aliasesText: String
        if (biography.aliases[0] == "-") {
            aliasesText = ""
        } else {
            var aliases = ""
            for (name in biography.aliases) {
                aliases += "* $name\n"
            }
            aliasesText = "Usually goes by:\n$aliases\n"
        }

        val occupation = when (work.occupation) {
            "-" -> "Unknown\n"
            else -> "${work.occupation}\n"

        }

        val base = when (work.base) {
            "-" -> "Unknown"
            else -> work.base

        }

        val biographyText =
            "$fullName$placeOfBirth${aliasesText}First appeared in ${biography.firstAppearance} published by ${biography.publisher}.\nOcupation: ${occupation}Based at: $base".trim()
        binding.textBiography.text = biographyText

        // Populate Affiliations

        val connections = character.connections

        val affiliationsText = when (connections.groupAffiliation) {
            "-" -> "No known affiliations."
            else -> {

                var result = ""
                if (connections.groupAffiliation.contains("; ")) {
                    for (affiliation in connections.groupAffiliation.split("; ")) {
                        result += "* $affiliation\n"
                    }
                } else {
                    for (affiliation in connections.groupAffiliation.split(", ")) {
                        result += "* $affiliation\n"
                    }
                }
                "Affiliated with: \n$result"
            }
        }

        binding.textAffiliations.text = affiliationsText.trim()

        // Populate Relatives

        val relativesText = when (connections.relatives) {
            "-" -> "No known relatives.\n"
            else -> {

                var result = ""
                if (connections.relatives.contains("; ")) {
                    for (relative in connections.relatives.split("; ")) {
                        result += "* $relative\n"
                    }
                } else {
                    for (relative in connections.relatives.split(", ")) {
                        result += "* $relative\n"
                    }
                }
                result
            }
        }

        binding.textRelatives.text = relativesText.trim()

        // Populate Power Stats
        adapter.powerstats = character.powerstats
        binding.powerstatsView.adapter = adapter

    }


    private fun setupActionBar(arguments: CharacterDetailFragmentArgs) {
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

        binding.apply {
            titleAffiliations.setTextColor(arguments.cardColor)
            titleAppearance.setTextColor(arguments.cardColor)
            titleBiography.setTextColor(arguments.cardColor)
            titlePowerStats.setTextColor(arguments.cardColor)
            titleRelatives.setTextColor(arguments.cardColor)

        }

    }

    private fun initComponents() {
        val factory = CharacterDetailViewModelFactory(repository, arguments.villainId)
        viewModel = ViewModelProvider(this, factory).get(CharacterDetailViewModel::class.java)

        binding.powerstatsView.hasFixedSize()
        adapter = PowerStatAdapter()
    }


}