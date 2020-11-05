package com.android.sensyneapplication

import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.sensyneapplication.ui.activities.MainActivity
import com.android.sensyneapplication.presentation.MainViewModel
import com.zavamed.makeitfree.TestSensyneApplication
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    private lateinit var mainViewModel: MainViewModel

    @Before
    open fun setUp() {
        TestSensyneApplication.testAppComponent.inject(this)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var loadingIdlingResource: LoadingIdlingResource

    @Test
    fun appLaunchesSuccessfully() {
        val mainActivityScenario = ActivityScenario.launch(MainActivity::class.java)
        mainActivityScenario.onActivity { activity ->
            loadingIdlingResource =
                LoadingIdlingResource(activity)
            IdlingRegistry.getInstance().register(loadingIdlingResource)
            TestSensyneApplication.testAppComponent.inject(activity)

            mainViewModel =
                ViewModelProvider(activity, viewModelFactory).get(MainViewModel::class.java)
        }
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(loadingIdlingResource)
    }
}
