package com.doiliomatsinhe.dcvilains.ui.villain

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.doiliomatsinhe.dcvilains.R
import com.doiliomatsinhe.dcvilains.adapter.VillainAdapter
import com.doiliomatsinhe.dcvilains.adapter.VillainClickListener
import com.doiliomatsinhe.dcvilains.databinding.FragmentVillainsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VillainsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentVillainsBinding
    private val villainsViewModel: VillainsViewModel by viewModels()
    private lateinit var villainAdapter: VillainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVillainsBinding.inflate(inflater, container, false)

        setupActionBar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()

        fetchData()

        villainsViewModel.navigateToVillainDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(
                    VillainsFragmentDirections.actionVillainsFragmentToVillainDetailFragment(
                        it.id,
                        it.name,
                        it.dominantcolor
                    )
                )
                villainsViewModel.onVillainDetailNavigated()
            }
        })

    }

    private fun fetchData() {
        binding.refreshLayout.isRefreshing = true
        villainsViewModel.villains.observe(viewLifecycleOwner, Observer {
            it?.let { listOfVillains ->
                villainAdapter.submitList(listOfVillains)
                binding.refreshLayout.isRefreshing = false
            }
        })
    }

    private fun initComponents() {

        // Adapter
        villainAdapter = VillainAdapter(VillainClickListener {
            villainsViewModel.onVillainClicked(it)
        })

        binding.apply {
            viewModel = villainsViewModel
            lifecycleOwner = this.lifecycleOwner
        }

        binding.villainList.adapter = villainAdapter
        binding.villainList.hasFixedSize()
        binding.villainList.layoutManager = StaggeredGridLayoutManager(
            resources.getInteger(R.integer.span_count),
            StaggeredGridLayoutManager.VERTICAL
        )

        binding.refreshLayout.setOnRefreshListener(this)
    }

    private fun setupActionBar() {
        //TODO: Look for a better way to handle this
        ((activity as AppCompatActivity).supportActionBar)?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    activity as AppCompatActivity, R.color.colorPrimary
                )
            )
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((activity as AppCompatActivity).window)?.statusBarColor = ContextCompat.getColor(
                activity as AppCompatActivity, R.color.colorPrimaryDark
            )
        }
    }

    override fun onRefresh() {
        fetchData()
    }

}