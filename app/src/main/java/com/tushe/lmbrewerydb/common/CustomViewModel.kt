package com.tushe.lmbrewerydb.common

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tushe.lmbrewerydb.ui.detail.DetailViewModel
import com.tushe.lmbrewerydb.ui.beers.BeersViewModel
import com.tushe.lmbrewerydb.ui.categories.CategoriesViewModel
import java.lang.IllegalArgumentException

/**
 * VIEWMODELPRIVIDER DEPRECATED PROBLEM
 */

@Suppress("UNCHECKED_CAST")
class CustomViewModel(private val application: Application): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(CategoriesViewModel::class.java) -> CategoriesViewModel(application)
                isAssignableFrom(BeersViewModel::class.java) -> BeersViewModel(application)
                isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(application)
                else -> throw IllegalArgumentException("Unkown ViewModel")
            }
        } as T
    }
}