package com.doiliomatsinhe.dcvilains.ui.villain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.doiliomatsinhe.dcvilains.adapter.VillainAdapter
import com.doiliomatsinhe.dcvilains.databinding.FragmentVillainsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VillainsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentVillainsBinding
    private val villainsViewModel: VillainsViewModel by viewModels()

    @Inject
    lateinit var villainAdapter: VillainAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVillainsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()

        fetchData()

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

        binding.apply {
            viewModel = villainsViewModel
            lifecycleOwner = this.lifecycleOwner
            adapter = villainAdapter
        }
        binding.refreshLayout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        fetchData()
    }

}