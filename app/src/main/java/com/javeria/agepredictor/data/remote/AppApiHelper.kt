package com.javeria.agepredictor.data.remote

import androidx.annotation.VisibleForTesting
import com.javeria.agepredictor.data.remote.model.response.UserAgeResponse
import io.reactivex.Single

class AppApiHelper(val apiService: ApiService) : ApiHelper {

    companion object {
        private var INSTANCE: AppApiHelper? = null

        @JvmStatic
        fun getInstance(apiService: ApiService): AppApiHelper {
            if (INSTANCE == null) {
                synchronized(AppApiHelper::javaClass) {
                    INSTANCE = AppApiHelper(apiService)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }

    override fun getUserAge(name: String): Single<UserAgeResponse> {
        return apiService.getAge(name)
    }

}