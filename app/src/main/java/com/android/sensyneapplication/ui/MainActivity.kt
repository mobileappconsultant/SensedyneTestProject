package com.android.sensyneapplication.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.sensyneapplication.R
import com.android.sensyneapplication.SensyneApplication
import javax.inject.Inject

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        // Switch to AppTheme for displaying the activity
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SensyneApplication.application.appComponent.inject(this)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }
}
