package com.javeria.agepredictor
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.javeria.agepredictor.Injection
import com.javeria.agepredictor.MainViewModel

class BaseViewModel() : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(Injection.provideApplication(), Injection.provideDataRepository()) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}