package com.javeria.agepredictor.data.parser

import com.javeria.agepredictor.data.remote.model.UserDetails
import com.javeria.agepredictor.data.remote.model.response.UserAgeResponse

interface IParser{

    fun parseUserAge(userAgeResponse: UserAgeResponse) : UserDetails?
}