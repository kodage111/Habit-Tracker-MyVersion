package com.kod.habittracker.ui.mainactivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.kod.habittracker.R
import com.kod.habittracker.databinding.ActivityMainBinding
import com.kod.habittracker.repository.DataStoreRepositoryImp
import com.kod.habittracker.ui.introscreen.IntroScreenActivity
import com.kod.habittracker.ui.viewmodels.datastoreviewmodel.DataStoreViewModel
import com.kod.habittracker.ui.viewmodels.datastoreviewmodel.DataStoreViewModelFactory

class MainActivity : AppCompatActivity() {
    private val sharedPrefs: SharedPreferences by lazy {
        getSharedPreferences("App_usage_state", Context.MODE_PRIVATE)
    }
    private var userFirstTime = true
    lateinit var dataStoreViewModel: DataStoreViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dataStoreViewModelFactory = DataStoreViewModelFactory(DataStoreRepositoryImp(sharedPrefs))
        dataStoreViewModel = ViewModelProvider(this, dataStoreViewModelFactory)[DataStoreViewModel::class.java]
        userFirstTime = loadUsageData()

        verifyingFirstUsage()
        setupActionBarWithNavController(findNavController(R.id.navHostfragment))
    }

    private fun verifyingFirstUsage(){
        Log.e("loading", userFirstTime.toString())
        if (userFirstTime){
            userFirstTime = false
            saveUsageData()
            startActivity(Intent(this, IntroScreenActivity::class.java))
            finish()
        }
    }
    private fun loadUsageData(): Boolean{
        return dataStoreViewModel.firstTime
    }

    private fun saveUsageData(){
        dataStoreViewModel.saveFirstTime(userFirstTime)
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.navHostfragment).navigateUp() || super.onSupportNavigateUp()

}