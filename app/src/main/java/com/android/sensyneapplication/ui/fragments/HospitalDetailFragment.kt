package com.android.sensyneapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.android.sensyneapplication.R
import kotlinx.android.synthetic.main.fragment_hospital_details.*

class HospitalDetailFragment : Fragment(R.layout.fragment_hospital_details) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialiseUIElements()
    }

    private fun initialiseUIElements() {
        arguments?.let {
            var currentHospital = HospitalDetailFragmentArgs.fromBundle(it)
            hospitalNameTextView.text = currentHospital.currentHospital.OrganisationName
        }
    }
}
