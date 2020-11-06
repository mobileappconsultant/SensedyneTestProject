package com.android.sensyneapplication.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.android.sensyneapplication.R
import com.android.sensyneapplication.SensyneApplication.Companion.application
import com.android.sensyneapplication.common.ConnectivityLiveData
import com.android.sensyneapplication.common.EndlessRecyclerOnScrollListener
import com.android.sensyneapplication.presentation.LoadingState
import com.android.sensyneapplication.presentation.MainViewModel
import com.android.sensyneapplication.ui.adapters.HospitalListAdapter
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import kotlinx.android.synthetic.main.fragment_hospital_list.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HospitalListFragment : Fragment(R.layout.fragment_hospital_list) {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var hospitalListAdapter: HospitalListAdapter
    private lateinit var connectivityLiveData: ConnectivityLiveData
    private var isLoading: Boolean = false
    private val debouncePeriod: Long = 500
    private val MINIMUX_SEARCH_TEXT_LENGTH = 2
    private var isSearching = false

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityLiveData = ConnectivityLiveData(application)
        application.appComponent.inject(this)
        mainViewModel = ViewModelProvider(requireActivity(), viewModelFactory).get(
            MainViewModel::class.java
        )
    }

    private fun initialiseScrollListener() {
        hospitalsRecyclerView.addOnScrollListener(
            object : EndlessRecyclerOnScrollListener() {
                override fun onLoadMore() {
                    if (isSearching) return
                    mainViewModel.loadMoreData()
                }
            }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialiseObservers()
        initialiseUIElements()
        initialiseScrollListener()
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
                onHospitalLoadingState(it)
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

        searchEditText.afterTextChangeEvents()
            .debounce(debouncePeriod, TimeUnit.MILLISECONDS)
            .subscribe {
                if (it.view.text.isNotEmpty()) isSearching = true
                mainViewModel.onSearchQuery(it.view.text.toString())
            }
        hospitalListAdapter = HospitalListAdapter({ hospitalItem ->
            mainViewModel.onHospitalClicked(hospitalItem)
        })
        hospitalsRecyclerView.apply {
            adapter = hospitalListAdapter
            hasFixedSize()
        }
    }

    private fun onHospitalLoadingState(state: LoadingState) {
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
