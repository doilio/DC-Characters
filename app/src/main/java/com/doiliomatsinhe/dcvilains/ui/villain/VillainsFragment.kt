package com.doiliomatsinhe.dcvilains.ui.villain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.doiliomatsinhe.dcvilains.R
import com.doiliomatsinhe.dcvilains.adapter.VillainAdapter
import com.doiliomatsinhe.dcvilains.adapter.VillainClickListener
import com.doiliomatsinhe.dcvilains.databinding.FragmentVillainsBinding

class VillainsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentVillainsBinding
    private lateinit var viewModel: VillainsViewModel
    private lateinit var villainAdapter: VillainAdapter

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
        viewModel.villainsList.observe(viewLifecycleOwner, Observer {
            it?.let { villainList ->
                villainAdapter.submitList(villainList)
                binding.refreshLayout.isRefreshing = false
            }
        })
    }

    private fun initComponents() {

        // ViewModel
        viewModel = ViewModelProvider(this).get(VillainsViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Adapter
        villainAdapter = VillainAdapter(VillainClickListener {
            Toast.makeText(requireContext(), "Clicked: ${it.name}", Toast.LENGTH_SHORT).show()
        })

        // RecyclerView
        binding.villainList.apply {
            adapter = villainAdapter
            hasFixedSize()
            layoutManager =
                StaggeredGridLayoutManager(
                    resources.getInteger(R.integer.span_count),
                    StaggeredGridLayoutManager.VERTICAL
                )
        }

        // SwipeRefreshLayout
        binding.refreshLayout.setOnRefreshListener(this)
    }

    override fun onRefresh() {
        fetchData()
    }


}