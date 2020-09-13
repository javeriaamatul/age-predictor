package com.javeria.agepredictor.data.remote

import com.javeria.agepredictor.data.remote.model.response.UserAgeResponse
import io.reactivex.Single

interface ApiHelper{

    fun getUserAge(name: String) : Single<UserAgeResponse>
}