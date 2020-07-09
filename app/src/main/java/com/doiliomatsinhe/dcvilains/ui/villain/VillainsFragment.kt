package com.doiliomatsinhe.dcvilains.ui.villain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.doiliomatsinhe.dcvilains.R
import com.doiliomatsinhe.dcvilains.adapter.VillainAdapter
import com.doiliomatsinhe.dcvilains.adapter.VillainClickListener
import com.doiliomatsinhe.dcvilains.database.VillainsDao
import com.doiliomatsinhe.dcvilains.database.VillainsDatabase
import com.doiliomatsinhe.dcvilains.databinding.FragmentVillainsBinding
import com.doiliomatsinhe.dcvilains.network.ApiService
import com.doiliomatsinhe.dcvilains.repository.VillainRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class VillainsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: FragmentVillainsBinding
    private val viewModel: VillainsViewModel by viewModels()
    private lateinit var villainAdapter: VillainAdapter
    @Inject
    lateinit var database: VillainsDatabase
    @Inject
    lateinit var villainsDao: VillainsDao
    @Inject
    lateinit var villainsService: ApiService
    @Inject
    lateinit var repository: VillainRepository


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
        viewModel.villains.observe(viewLifecycleOwner, Observer {
            it?.let { listOfVillains ->
                villainAdapter.submitList(listOfVillains)
                binding.refreshLayout.isRefreshing = false
            }
        })
    }

    private fun initComponents() {

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