package com.javeria.agepredictor

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.javeria.agepredictor.data.DataInterface
import com.javeria.agepredictor.data.DataRepository
import com.javeria.agepredictor.data.remote.Resource
import com.javeria.agepredictor.data.remote.Status
import com.javeria.agepredictor.data.remote.model.UserDetails

class MainViewModel constructor(application: Application, private val dataInterface: DataInterface) :
    AndroidViewModel(application) {

    var userDetails: LiveData<Resource<UserDetails>>?= null


    init {
        userDetails = (dataInterface as DataRepository).userDetails
    }

    fun getUserAge(name: String) {
        val res =  userDetails?.value
        res?.status = Status.LOADING
        dataInterface.getUserName(name)

    }
}