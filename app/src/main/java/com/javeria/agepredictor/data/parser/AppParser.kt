package com.javeria.agepredictor.data.parser

import androidx.annotation.VisibleForTesting
import com.javeria.agepredictor.data.remote.model.UserDetails
import com.javeria.agepredictor.data.remote.model.response.UserAgeResponse

class AppParser : IParser{

    companion object {
        private var INSTANCE: IParser? = null

        @JvmStatic
        fun getInstance(): IParser {
            if (INSTANCE == null) {
                synchronized(AppParser::javaClass) {
                    INSTANCE = AppParser()
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

    override fun parseUserAge(userAgeResponse: UserAgeResponse): UserDetails? {
        return if (userAgeResponse != null)
        {
            val userDetails = UserDetails()
            userDetails.age = userAgeResponse.age
            userDetails.name = userAgeResponse.name
            userDetails.count = userAgeResponse.count
            userDetails
        }else null
    }
}