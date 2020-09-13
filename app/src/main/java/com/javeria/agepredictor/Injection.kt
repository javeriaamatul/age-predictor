package com.javeria.agepredictor

import android.app.Application
import com.javeria.agepredictor.data.DataInterface
import com.javeria.agepredictor.data.DataRepository
import com.javeria.agepredictor.data.parser.AppParser
import com.javeria.agepredictor.data.remote.ApiService
import com.javeria.agepredictor.data.remote.AppApiHelper


object Injection {
    fun provideDataRepository(): DataInterface {
        return DataRepository.getInstance(
            AppApiHelper(ApiService.create()),
            AppParser.getInstance()
        )
    }

    fun provideApplication(): Application {
        return App.application()
    }
}