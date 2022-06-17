package com.kod.habittracker.ui.viewmodels.datastoreviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kod.habittracker.repository.DataStoreRepositoryImp

class DataStoreViewModelFactory(private val dataStore: DataStoreRepositoryImp): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DataStoreViewModel::class.java)) return DataStoreViewModel(dataStore) as T
        throw IllegalArgumentException ("Unknown ViewModel Class")
    }
}