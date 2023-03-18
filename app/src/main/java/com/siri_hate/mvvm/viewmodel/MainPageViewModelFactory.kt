package com.siri_hate.mvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class MainPageViewModelFactory(
    private val currencyAbbreviation: Array<String>,
    private val currencyFlags: IntArray,
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainPageViewModel(currencyAbbreviation, currencyFlags) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}