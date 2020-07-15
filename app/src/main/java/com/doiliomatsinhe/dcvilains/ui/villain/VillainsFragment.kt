package com.doiliomatsinhe.dcvilains.ui.villain

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.doiliomatsinhe.dcvilains.R
import com.doiliomatsinhe.dcvilains.adapter.VillainAdapter
import com.doiliomatsinhe.dcvilains.adapter.VillainClickListener
import com.doiliomatsinhe.dcvilains.databinding.FragmentVillainsBinding
import com.doiliomatsinhe.dcvilains.model.Filters
import com.doiliomatsinhe.dcvilains.ui.filter.FilterFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VillainsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener,
    FilterFragment.FilterDialogListener {

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
        onFilter(villainsViewModel.filters)
        //fetchData(villainsViewModel.filters)

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.ic_filter) {
            openFilterDialog()
        }

        return true
    }

    private fun openFilterDialog() {
        val dialog = FilterFragment(
            villainsViewModel.filters
        )
        dialog.setTargetFragment(this, 1)
        dialog.show(parentFragmentManager, "Filter Dialog")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.power_stats_menu, menu)
    }

    private fun fetchData(filters: Filters) {
        binding.refreshLayout.isRefreshing = true
        villainsViewModel.getList(filters)
        villainsViewModel.villains.observe(viewLifecycleOwner, Observer {
            it?.let { listOfVillains ->
                if (listOfVillains.isNotEmpty()) {
                    villainAdapter.submitList(listOfVillains)
                    showRecyclerView()

                } else {
                    hideRecyclerView()
                }

                binding.refreshLayout.isRefreshing = false
            }
        })
    }

    private fun showRecyclerView() {
        binding.villainList.visibility = View.VISIBLE
        binding.textException.visibility = View.GONE
        binding.buttonRetry.visibility = View.GONE
    }

    private fun hideRecyclerView() {
        binding.villainList.visibility = View.GONE
        binding.textException.visibility = View.VISIBLE
        binding.buttonRetry.visibility = View.VISIBLE
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

        // Reseting filters
        binding.buttonRetry.setOnClickListener {
            val filters = Filters()
            onFilter(filters)
        }

        binding.refreshLayout.setOnRefreshListener(this)
    }

    private fun setupActionBar() {
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

        setHasOptionsMenu(true)
    }

    override fun onRefresh() {
        fetchData(villainsViewModel.filters)
    }

    override fun onFilter(filters: Filters) {
        fetchData(filters)

        villainsViewModel.filters = filters

    }


}