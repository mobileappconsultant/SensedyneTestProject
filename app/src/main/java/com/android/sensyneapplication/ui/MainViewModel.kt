package com.android.sensyneapplication.ui
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import com.android.sensyneapplication.Event
import com.android.sensyneapplication.framework.domain.HospitalItemsQueryRepository
import com.android.sensyneapplication.framework.domain.model.HospitalItem
import com.android.sensyneapplication.ui.hospitals.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: HospitalItemsQueryRepository) :
    ViewModel() {

    val _hospitalListLiveData = MutableLiveData<List<HospitalItem>>()
    val hospitalListMediatorData = MediatorLiveData<List<HospitalItem>>()
    private var searchJob: Job? = null
    private var _searchHospitalsLiveData: LiveData<List<HospitalItem>>
    private var hospitalQueryResponse = emptyList<HospitalItem>()
    val hospitalsLoadingStateLiveData = MutableLiveData<LoadingState>()
    private val _searchFieldTextLiveData = MutableLiveData<String>()
    private val _navigateToDetails = MutableLiveData<Event<String>>()
    private val MAX_NUMBER_OF_ITEMS = 10
    private var numberOfPageRequests = 0
    private var subListsOfHospitalResponse: List<List<HospitalItem>> = emptyList()
    val navigateToDetails: LiveData<Event<String>>
        get() = _navigateToDetails

    init {
        _searchHospitalsLiveData = Transformations.switchMap(_searchFieldTextLiveData) {
            fetchHospitalByQuery(it)
        }

        hospitalListMediatorData.addSource(_hospitalListLiveData) {
            hospitalListMediatorData.value = it
        }

        hospitalListMediatorData.addSource(_searchHospitalsLiveData) {
            hospitalListMediatorData.value = it
        }
    }

    fun onFragmentReady() {
        if (_hospitalListLiveData.value.isNullOrEmpty()) {
            fetchHospitals()
        }
    }

    fun loadMoreData() {
        _hospitalListLiveData.postValue(processNextPagedList())
    }

    fun onSearchQuery(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _searchFieldTextLiveData.value = query
        }
    }

    fun fetchHospitals() {
        hospitalsLoadingStateLiveData.value = LoadingState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.retrieveHospitals()?.let {
                    hospitalQueryResponse = it
                    subListsOfHospitalResponse = hospitalQueryResponse.windowed(
                        size = MAX_NUMBER_OF_ITEMS,
                        step = MAX_NUMBER_OF_ITEMS,
                        partialWindows = true
                    )
                    _hospitalListLiveData.postValue(processNextPagedList())
                    hospitalsLoadingStateLiveData.postValue(LoadingState.LOADED)
                }
            } catch (e: Exception) {
                hospitalsLoadingStateLiveData.postValue(LoadingState.UNkNOWN_ERROR)
            }
        }
    }

    fun processNextPagedList(): List<HospitalItem> {
        numberOfPageRequests++
        return subListsOfHospitalResponse.get(numberOfPageRequests)
    }

    private fun fetchHospitalByQuery(query: String): LiveData<List<HospitalItem>> {

        // jump in here
        // get string
        // run it thru filters of regex
        // retun name
        // build query
        val liveData = MutableLiveData<List<HospitalItem>>()

        // Beginning of query string
        var queryString = "SELECT * FROM RoomHospitalItem"
        var query = generateQuery(queryString)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    hospitalsLoadingStateLiveData.value = LoadingState.LOADING
                }

                val hospitalQueryResponse = repository.queryHospitals(query)
                liveData.postValue(hospitalQueryResponse)
                hospitalsLoadingStateLiveData.postValue(LoadingState.LOADED)
            } catch (e: Exception) {
                hospitalsLoadingStateLiveData.postValue(LoadingState.UNkNOWN_ERROR)
            }
        }
        return liveData
    }

    private fun generateQuery(query: String): SimpleSQLiteQuery {
        // List of bind parameters
        val args = emptyList<String>()
        val query = SimpleSQLiteQuery(query, args.toTypedArray())
        return query
    }

    fun onHospitalClicked(hospitalItem: HospitalItem) {
        hospitalItem.Address1?.let {
            _navigateToDetails.value = Event(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}
