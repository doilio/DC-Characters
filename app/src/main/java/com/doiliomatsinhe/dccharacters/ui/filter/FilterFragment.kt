package com.doiliomatsinhe.dccharacters.ui.filter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDialogFragment
import com.doiliomatsinhe.dccharacters.R
import com.doiliomatsinhe.dccharacters.model.Filters
import timber.log.Timber

class FilterFragment(private val recoveredFilters: Filters) : AppCompatDialogFragment() {

    private lateinit var spinnerSortBy: Spinner
    private lateinit var spinnerFilterRace: Spinner
    private lateinit var spinnerFilterGender: Spinner
    private lateinit var cancelButton: Button
    private lateinit var applyButton: Button
    private lateinit var filterListener: FilterDialogListener
    private lateinit var sortAdapter: ArrayAdapter<String>
    private lateinit var raceAdapter: ArrayAdapter<String>
    private lateinit var genderAdapter: ArrayAdapter<String>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity(), R.style.AlertDialogTheme)
        val inflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.filter_dialog_layout, null)

        // Init components
        initComponents(view)

        configureSpinners()

        recoverSpinnerPositions()

        builder.setView(view)
        return builder.create()
    }

    private fun recoverSpinnerPositions() {
        val sortSpinnerPosition = sortAdapter.getPosition(recoveredFilters.order)
        spinnerSortBy.setSelection(sortSpinnerPosition)
        spinnerSortBy.isEnabled = false //TODO Enable after finding a clean way to Implement sorting

        val genderSpinnerPosition = if (recoveredFilters.gender == "-") {
            genderAdapter.getPosition("Unknown")
        } else {
            genderAdapter.getPosition(recoveredFilters.gender)
        }
        spinnerFilterGender.setSelection(genderSpinnerPosition)

        val raceSpinnerPosition = raceAdapter.getPosition(recoveredFilters.race)
        spinnerFilterRace.setSelection(raceSpinnerPosition)
    }

    private fun configureSpinners() {
        // Sort Spinner
        val sortList = resources.getStringArray(R.array.sort_by)
        sortAdapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            sortList
        )
        sortAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinnerSortBy.adapter = sortAdapter


        // Race Spinner
        val raceList = resources.getStringArray(R.array.filter_by_race)
        raceAdapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item,
            raceList
        )
        raceAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinnerFilterRace.adapter = raceAdapter

        // Gender Spinner
        val genderList = resources.getStringArray(R.array.filter_by_gender)
        genderAdapter = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item, genderList
        )
        genderAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinnerFilterGender.adapter = genderAdapter

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        filterListener = try {
            (targetFragment as FilterDialogListener)
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement FilterDialogListener")
        }

    }

    private fun onApplyClicked() {
        filterListener.onFilter(getFilters())
    }

    interface FilterDialogListener {
        fun onFilter(filters: Filters)
    }

    private fun initComponents(view: View) {
        spinnerSortBy = view.findViewById(R.id.spinner_stats)
        spinnerFilterRace = view.findViewById(R.id.filter_race)
        spinnerFilterGender = view.findViewById(R.id.filter_gender)
        cancelButton = view.findViewById(R.id.filter_cancel)
        applyButton = view.findViewById(R.id.fitler_apply)

        // Set Click Listeners
        cancelButton.setOnClickListener { dismiss() }
        applyButton.setOnClickListener {
            onApplyClicked()
            dismiss()
        }
    }

    private fun getSortOrder(): String {
        val selected = spinnerSortBy.selectedItem as String
        Timber.d("Selected order $selected")
        return selected
    }

    private fun getGender(): String {
        val gender = when (val selected = spinnerFilterGender.selectedItem as String) {
            "Any Gender" -> ""
            "Unknown" -> "-"
            else -> selected
        }

        Timber.d("Selected gender $gender")
        return gender
    }

    private fun getRace(): String {
        val selected = spinnerFilterRace.selectedItem as String
        val race = if (selected == "Any Race") {
            ""
        } else {
            selected
        }
        Timber.d("Selected race $race")
        return race
    }

    private fun getFilters(): Filters {
        val filters = Filters()
        filters.order = getSortOrder()
        filters.gender = getGender()
        filters.race = getRace()

        return filters
    }

}