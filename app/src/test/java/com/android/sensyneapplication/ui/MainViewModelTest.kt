package com.android.sensyneapplication.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.android.sensyneapplication.TestCoroutineRule
import com.android.sensyneapplication.domain.search.QueryBuilder
import com.android.sensyneapplication.domain.repository.HospitalItemsQueryRepository
import com.android.sensyneapplication.framework.domain.model.HospitalItem
import com.android.sensyneapplication.framework.domain.model.HospitalQueryResponse
import com.android.sensyneapplication.presentation.MainViewModel
import com.bumptech.glide.load.engine.Resource
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest : TestCase() {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var repository: HospitalItemsQueryRepository

    @Mock
    lateinit var queryBuilder: QueryBuilder

    @Mock
    private lateinit var hospitalItemObserver: Observer<Resource<List<HospitalItem>>>

    @Test
    fun givenServerResponse200_whenFetch_shouldReturnSuccess() {
        // given

        testCoroutineRule.runBlockingTest {

            // when
            doReturn(HospitalQueryResponse(emptyList()).hospitals)
                .`when`(repository)
                .retrieveHospitals()
            val viewModel = MainViewModel(repository, queryBuilder)
            viewModel.fetchHospitals()
            viewModel.hospitalsLoadingStateLiveData.observeForever { hospitalItemObserver }

            // then
            //   assertEquals(viewModel.movieLoadingStateLiveData.value, LoadingState.INVALID_API_KEY)
            runBlocking {
                // Verify
                assertNotNull(viewModel.hospitalsLoadingStateLiveData.value)
            }
        }
    }

    @Test
    fun givenServerResponseError_whenFetch_shouldReturnFails() {
        // given

        testCoroutineRule.runBlockingTest {
            // when
            doReturn(Exception())
                .`when`(repository)
                .retrieveHospitals()
            val viewModel = MainViewModel(repository, queryBuilder)
            viewModel.fetchHospitals()
            viewModel.hospitalsLoadingStateLiveData.observeForever { hospitalItemObserver }

            // then
            // assertEquals(viewModel.movieLoadingStateLiveData.value, LoadingState.LOADING)
            runBlocking {
                // assertEquals(viewModel.movieLoadingStateLiveData.value, LoadingState.INVALID_API_KEY)
                // Verify
                assertNull(viewModel._hospitalListLiveData.value)
            }
            // verify(hospitalItemObserver).getUsers()
            //     verify(viewModel.movieLoadingStateLiveData).value = LoadingState.LOADING
        }
    }

    fun testGetMovieLoadingStateLiveData() {}

    fun testGetNavigateToDetails() {}

    fun testOnFragmentReady() {}

    fun testOnSearchQuery() {}

    fun testOnHospitalClicked() {}

    fun testOnCleared() {}
}
