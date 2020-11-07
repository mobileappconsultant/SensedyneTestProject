package com.android.sensyneapplication.ui.fragments

import android.content.Intent
import android.net.Uri
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
import com.android.sensyneapplication.presentation.SearchAction
import com.android.sensyneapplication.presentation.adapters.ClickActions
import com.android.sensyneapplication.presentation.adapters.HospitalListAdapter
import com.jakewharton.rxbinding4.widget.afterTextChangeEvents
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_hospital_list.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HospitalListFragment : Fragment(R.layout.fragment_hospital_list) {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var hospitalListAdapter: HospitalListAdapter
    private lateinit var connectivityLiveData: ConnectivityLiveData
    private var isLoading: Boolean = false
    private val debouncePeriod: Long = 500
    private var isSearching = false
    private  var compositeDisposable: CompositeDisposable = CompositeDisposable()
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
                    isLoading = true
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
                it?.getContentIfNotHandled()?.let { clickAction ->

                    resolveClickAction(clickAction)
                }
            }
        )
    }

    private fun resolveClickAction(clickAction: ClickActions) {
        when (clickAction) {
            is ClickActions.PhoneAction -> {
                clickAction.phoneNumber?.let { dialPhoneNumber(it) }
            }
            is ClickActions.WebAction -> {
                clickAction.webAddress?.let {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(clickAction.webAddress))
                    startActivity(browserIntent)
                }
            }
            is ClickActions.EmailAction -> {
                clickAction.emailAddress?.let {
                    var intent = Intent(
                        Intent.ACTION_SENDTO,
                        Uri.parse("mailto:".plus(clickAction.emailAddress))
                    )
                    activity?.startActivity(intent)
                }
            }
            is ClickActions.LocationAction -> {
                if (!clickAction.longitude.isNullOrEmpty() && !clickAction.longitude.isNullOrEmpty()) {
                    var geoString =
                        "geo:${clickAction.latitude},${clickAction.longitude}"
                    val gmmIntentUri = Uri.parse(geoString)
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    startActivity(mapIntent)
                }
            }
            is ClickActions.MainAction -> {
                clickAction.hospitalItem?.let {
                    findNavController().navigate(
                        HospitalListFragmentDirections.hospitalItemClicked(it)
                    )
                }
            }
        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        intent.resolveActivity(activity?.packageManager!!)?.let {
            startActivity(intent)
        }
    }

    private fun initialiseUIElements() {
       val disposable = searchEditText.afterTextChangeEvents().skipInitialValue()
            .debounce(debouncePeriod, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                   if (it.view.text.toString().length > 2) {
                    isSearching = true
                    mainViewModel.onSearchQuery(SearchAction.UserTypingAction(it.view.text.toString()))
                } else {
                    isSearching = false
                    mainViewModel.onSearchQuery(SearchAction.NoSearchStringAction())
                }
            }
        hospitalListAdapter = HospitalListAdapter({ hospitalItem ->
            mainViewModel.onHospitalClicked(hospitalItem)
        })
        hospitalsRecyclerView.apply {
            adapter = hospitalListAdapter
            hasFixedSize()
        }
        compositeDisposable.add(disposable)
    }

    private fun onHospitalLoadingState(state: LoadingState) {
        when (state) {
            LoadingState.LOADING -> {
                statusButton.visibility = View.GONE
                hospitalsRecyclerView.visibility = View.GONE
                loadingProgressBar.visibility = View.VISIBLE
                searchEditText.isEnabled = false
            }
            LoadingState.LOADED -> {
                searchEditText.isEnabled = true
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
