package com.android.sensyneapplication.ui.hospitals

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.sensyneapplication.R
import com.android.sensyneapplication.SensyneApplication.Companion.application
import com.android.sensyneapplication.connectivity.ConnectivityLiveData
import com.android.sensyneapplication.framework.domain.model.HospitalItem
import com.android.sensyneapplication.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_hospital_list.*
import javax.inject.Inject

class HospitalListFragment : Fragment(R.layout.fragment_hospital_list) {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var hospitalListAdapter: HospitalListAdapter
    private lateinit var connectivityLiveData: ConnectivityLiveData

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val searchTextWatcher = object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            mainViewModel.onSearchQuery(editable.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.i("", "")
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            Log.i("", "")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityLiveData = ConnectivityLiveData(application)
        application.appComponent.inject(this)
        mainViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(
            MainViewModel::class.java
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialiseObservers()
        initialiseUIElements()
    }

    private fun initialiseObservers() {
        mainViewModel.hospitalListMediatorData.observe(
            viewLifecycleOwner,
            {
                hospitalListAdapter.updateData(it)
            }
        )

        mainViewModel.hospitalsLoadingStateLiveData.observe(
            viewLifecycleOwner,
            {
                onMovieLoadingStateChanged(it)
            }
        )

        connectivityLiveData.observe(
            viewLifecycleOwner,
            { isAvailable ->
                when (isAvailable) {
                    true -> {
                        mainViewModel.onFragmentReady()
                        statusButton.visibility = View.GONE
                        hospitalsRecyclerView.visibility = View.VISIBLE
                        searchEditText.visibility = View.VISIBLE
                    }
                    false -> {
                        statusButton.visibility = View.VISIBLE
                        hospitalsRecyclerView.visibility = View.GONE
                        searchEditText.visibility = View.GONE
                    }
                }
            }
        )

        mainViewModel.navigateToDetails.observe(
            viewLifecycleOwner,
            {
                it?.getContentIfNotHandled()?.let { hospitalTitle ->
                    findNavController().navigate(
                        HospitalListFragmentDirections.hospitalItemClicked(hospitalTitle)
                    )
                }
            }
        )
    }

    private fun initialiseUIElements() {
        searchEditText.addTextChangedListener(searchTextWatcher)
        hospitalListAdapter = HospitalListAdapter({ movie ->
            mainViewModel.onHospitalClicked(movie as HospitalItem)
        })
        hospitalsRecyclerView.apply {
            adapter = hospitalListAdapter
            hasFixedSize()
        }
    }

    private fun onMovieLoadingStateChanged(state: LoadingState) {
        when (state) {
            LoadingState.LOADING -> {
                statusButton.visibility = View.GONE
                hospitalsRecyclerView.visibility = View.GONE
                loadingProgressBar.visibility = View.VISIBLE
            }
            LoadingState.LOADED -> {
                connectivityLiveData.value?.let {
                    if (it) {
                        statusButton.visibility = View.GONE
                        hospitalsRecyclerView.visibility = View.VISIBLE
                    } else {
                        statusButton.visibility = View.VISIBLE
                        hospitalsRecyclerView.visibility = View.GONE
                    }
                }
                loadingProgressBar.visibility = View.GONE
            }
            LoadingState.ERROR -> {
                statusButton.visibility = View.VISIBLE
                context?.let {
                    statusButton.setCompoundDrawables(
                        null,
                        ContextCompat.getDrawable(it, R.drawable.no_internet),
                        null,
                        null
                    )
                }
                hospitalsRecyclerView.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
            }
            LoadingState.UNkNOWN_ERROR -> {
                statusButton.visibility = View.VISIBLE
                statusButton.text = getString(R.string.unknown_error_key)
                statusButton.setCompoundDrawables(null, null, null, null)
                hospitalsRecyclerView.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
            }
        }
    }
}
