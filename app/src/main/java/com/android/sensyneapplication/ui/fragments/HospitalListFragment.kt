package com.android.sensyneapplication.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.sensyneapplication.R
import com.android.sensyneapplication.SensyneApplication.Companion.application
import com.android.sensyneapplication.common.ConnectivityLiveData
import com.android.sensyneapplication.framework.domain.model.HospitalItem
import com.android.sensyneapplication.presentation.MainViewModel
import com.android.sensyneapplication.ui.adapters.HospitalListAdapter
import com.android.sensyneapplication.presentation.LoadingState
import com.jakewharton.rxbinding4.widget.textChangeEvents
import kotlinx.android.synthetic.main.fragment_hospital_list.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HospitalListFragment : Fragment(R.layout.fragment_hospital_list) {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var hospitalListAdapter: HospitalListAdapter
    private lateinit var connectivityLiveData: ConnectivityLiveData
    private var isLoading: Boolean = false
    private var debouncePeriod: Long = 500
    private val MINIMUX_SEARCH_TEXT_LENGTH = 2

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
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    if (!isLoading) {
                        linearLayoutManager?.let {
                            linearLayoutManager.findLastCompletelyVisibleItemPosition() ==
                                hospitalListAdapter.getItemCount().minus(1)
                        }
                    }
                }
            }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initialiseScrollListener()
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

        searchEditText.textChangeEvents()
            .filter { it.text.toString().length > MINIMUX_SEARCH_TEXT_LENGTH }
            .debounce(debouncePeriod, TimeUnit.MILLISECONDS)
            .subscribe {
                mainViewModel.onSearchQuery(it.text.toString())
            }
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
