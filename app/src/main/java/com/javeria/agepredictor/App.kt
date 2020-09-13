package com.javeria.agepredictor

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import java.util.*

class App : Application(){

    var TAG = "App"

    init {
        instance = this
    }

    companion object{

        private var instance: App? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

        fun application(): Application{
            return instance!!
        }
    }


    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this);
    }
}