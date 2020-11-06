package com.android.sensyneapplication.presentation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.sensyneapplication.domain.search.QueryBuilder
import com.android.sensyneapplication.domain.repository.HospitalItemsQueryRepository
import com.android.sensyneapplication.framework.domain.model.HospitalItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: HospitalItemsQueryRepository,
    private val queryBuilder: QueryBuilder
) :
    ViewModel() {

    val _hospitalListLiveData = MutableLiveData<List<HospitalItem>>()
    val hospitalListMediatorData = MediatorLiveData<List<HospitalItem>>()
    private var searchJob: Job? = null
    private var _searchHospitalsLiveData: LiveData<List<HospitalItem>>
    private var hospitalQueryResponse = emptyList<HospitalItem>()
    val hospitalsLoadingStateLiveData = MutableLiveData<LoadingState>()
    private val _searchFieldTextLiveData = MutableLiveData<String>()
    private val _navigateToDetails = MutableLiveData<Event<HospitalItem>>()
    private val MAX_NUMBER_OF_ITEMS = 10
    private var numberOfPageRequests = 0
    private val START_INDEX = 0
    private var subListsOfHospitalResponse: List<List<HospitalItem>> = emptyList()
    val navigateToDetails: LiveData<Event<HospitalItem>>
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
                    _hospitalListLiveData.postValue(subListsOfHospitalResponse.get(START_INDEX))
                    hospitalsLoadingStateLiveData.postValue(LoadingState.LOADED)
                }
            } catch (e: Exception) {
                hospitalsLoadingStateLiveData.postValue(LoadingState.UNkNOWN_ERROR)
            }
        }
    }

    fun processNextPagedList(): List<HospitalItem> {
        numberOfPageRequests++
        return subListsOfHospitalResponse.subList(START_INDEX, numberOfPageRequests.plus(1)).flatten()
    }

    private fun fetchHospitalByQuery(query: String): LiveData<List<HospitalItem>> {
        val liveData = MutableLiveData<List<HospitalItem>>()

        var query = queryBuilder.generateQuery(query)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) {
                    hospitalsLoadingStateLiveData.value = LoadingState.LOADING
                }

                val hospitalQueryResponse = repository.queryHospitals(query)
                if (hospitalQueryResponse.isEmpty().not()) {
                    liveData.postValue(hospitalQueryResponse)
                }
                hospitalsLoadingStateLiveData.postValue(LoadingState.LOADED)
            } catch (e: Exception) {
                hospitalsLoadingStateLiveData.postValue(LoadingState.UNkNOWN_ERROR)
            }
        }
        return liveData
    }

    fun onHospitalClicked(hospitalItem: HospitalItem) {
        _navigateToDetails.value = Event(hospitalItem)
    }

    override fun onCleared() {
        super.onCleared()
        searchJob?.cancel()
    }
}
