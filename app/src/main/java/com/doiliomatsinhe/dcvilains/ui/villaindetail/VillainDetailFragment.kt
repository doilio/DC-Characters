package com.doiliomatsinhe.dcvilains.ui.villaindetail

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.doiliomatsinhe.dcvilains.databinding.FragmentVillainsBinding
import com.doiliomatsinhe.dcvilains.repository.VillainRepository
import com.doiliomatsinhe.dcvilains.utils.ColorUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VillainDetailFragment : Fragment() {

    private lateinit var binding: FragmentVillainsBinding
    private lateinit var viewModel: VillainDetailViewModel
    private lateinit var arguments: VillainDetailFragmentArgs

    @Inject
    lateinit var repository: VillainRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVillainsBinding.inflate(inflater, container, false)

        arguments = VillainDetailFragmentArgs.fromBundle(requireArguments())

        setupActionBar(arguments)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()

        viewModel.getVillain().observe(viewLifecycleOwner, Observer {
            it?.let {

                Toast.makeText(activity, it.name, Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun setupActionBar(arguments: VillainDetailFragmentArgs) {
        ((activity as AppCompatActivity).supportActionBar)?.title = arguments.villainName
        ((activity as AppCompatActivity).supportActionBar)?.setBackgroundDrawable(ColorDrawable(arguments.cardColor))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((activity as AppCompatActivity).window)?.statusBarColor = ColorUtils.manipulateColor(
                arguments.cardColor, 0.50f)
        }
    }
    private fun initComponents() {

        val factory = VillainDetailViewModelFactory(repository, arguments.villainId)
        viewModel = ViewModelProvider(this, factory).get(VillainDetailViewModel::class.java)
    }


}