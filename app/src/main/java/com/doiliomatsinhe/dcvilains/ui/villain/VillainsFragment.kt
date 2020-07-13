package com.doiliomatsinhe.dcvilains.ui.villain

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
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
import com.doiliomatsinhe.dcvilains.model.Filters
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
        fetchData(villainsViewModel.filters)

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
        val dialog = FilterFragment(villainsViewModel.filters)
        dialog.setTargetFragment(this, 1)
        dialog.show(parentFragmentManager, "Filter Dialog")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.power_stats_menu, menu)
    }

    private fun fetchData(filters: Filters) {
        binding.refreshLayout.isRefreshing = true
        villainsViewModel.getList(filters).observe(viewLifecycleOwner, Observer {
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

        setHasOptionsMenu(true)
    }

    override fun onRefresh() {
        fetchData(villainsViewModel.filters)
    }

    override fun onFilter(filters: Filters) {
        fetchData(filters)

        Toast.makeText(activity, filters.order, Toast.LENGTH_SHORT).show()

        villainsViewModel.filters = filters

    }


}