package com.doiliomatsinhe.dccharacters.ui.character

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
import com.doiliomatsinhe.dccharacters.R
import com.doiliomatsinhe.dccharacters.adapter.CharacterAdapter
import com.doiliomatsinhe.dccharacters.adapter.CharacterClickListener
import com.doiliomatsinhe.dccharacters.databinding.FragmentCharactersBinding
import com.doiliomatsinhe.dccharacters.model.Filters
import com.doiliomatsinhe.dccharacters.ui.filter.FilterFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CharactersFragment : Fragment(),
    FilterFragment.FilterDialogListener {

    private lateinit var binding: FragmentCharactersBinding
    private val charactersViewModel: CharactersViewModel by viewModels()
    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var interstitialAd: InterstitialAd

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCharactersBinding.inflate(inflater, container, false)

        setupActionBar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
        onFilter(charactersViewModel.filters)

        charactersViewModel.navigateToCharacterDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                // If Ad is ready to be displayed, then display it
                if (interstitialAd.isLoaded) {
                    interstitialAd.show()
                } else {
                    Timber.d("Ad wasn't loaded yet!")
                }

                findNavController().navigate(
                    CharactersFragmentDirections.actionVillainsFragmentToVillainDetailFragment(
                        it.id,
                        it.name,
                        it.dominantcolor
                    )
                )
                charactersViewModel.onCharacterDetailNavigated()
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
            charactersViewModel.filters
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
            charactersViewModel.getList(filters).collectLatest { pagedList ->
                characterAdapter.submitData(pagedList)
            }
        }
    }

    private fun initComponents() {

        // Adapter
        characterAdapter = CharacterAdapter(CharacterClickListener {
            charactersViewModel.onCharacterClicked(it)
        }).apply {
            addLoadStateListener {
                binding.loadState = it.refresh
            }
        }

        binding.lifecycleOwner = viewLifecycleOwner


        binding.characterList.adapter = characterAdapter
        binding.characterList.hasFixedSize()
        binding.characterList.layoutManager = StaggeredGridLayoutManager(
            resources.getInteger(R.integer.span_count),
            StaggeredGridLayoutManager.VERTICAL
        )

        // Reseting filters
        binding.buttonRetry.setOnClickListener {
            val filters = Filters()
            onFilter(filters)
        }

        // Initialize Ad
        interstitialAd = InterstitialAd(requireContext())
        interstitialAd.adUnitId = "ca-app-pub-3005109827350902/5784846047"
        interstitialAd.loadAd(AdRequest.Builder().build())


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

        charactersViewModel.filters = filters

    }


}