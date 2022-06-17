package com.kod.habittracker.ui.viewmodels.datastoreviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kod.habittracker.repository.DataStoreRepositoryImp
import kotlinx.coroutines.launch

class DataStoreViewModel(private val dataStoreRepositoryImp: DataStoreRepositoryImp): ViewModel() {
    val firstTime = dataStoreRepositoryImp.readFirstUsage()

    fun saveFirstTime(firstTime: Boolean){
        viewModelScope.launch{
            dataStoreRepositoryImp.saveFirstUsage(firstTime)
        }
    }
}


