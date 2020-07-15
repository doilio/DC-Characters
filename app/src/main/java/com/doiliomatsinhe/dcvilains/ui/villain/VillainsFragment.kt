package com.doiliomatsinhe.dcvilains.ui.villain

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.doiliomatsinhe.dcvilains.R
import com.doiliomatsinhe.dcvilains.adapter.VillainAdapter
import com.doiliomatsinhe.dcvilains.adapter.VillainClickListener
import com.doiliomatsinhe.dcvilains.databinding.FragmentVillainsBinding
import com.doiliomatsinhe.dcvilains.model.Filters
import com.doiliomatsinhe.dcvilains.ui.filter.FilterFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VillainsFragment : Fragment(),
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
        lifecycleScope.launch {
            villainsViewModel.getList(filters).collectLatest { pagedList ->
                villainAdapter.submitData(pagedList)
            }
        }
    }

    private fun initComponents() {

        // Adapter
        villainAdapter = VillainAdapter(VillainClickListener {
            villainsViewModel.onVillainClicked(it)
        }).apply {
            addLoadStateListener {
                binding.loadState = it.refresh
            }
        }

        binding.lifecycleOwner = viewLifecycleOwner


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

    override fun onFilter(filters: Filters) {
        fetchData(filters)

        villainsViewModel.filters = filters

    }


}