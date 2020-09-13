package com.javeria.agepredictor.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.javeria.agepredictor.Const
import com.javeria.agepredictor.data.parser.IParser
import com.javeria.agepredictor.data.remote.ApiHelper
import com.javeria.agepredictor.data.remote.AppApiHelper
import com.javeria.agepredictor.data.remote.Resource
import com.javeria.agepredictor.data.remote.Status
import com.javeria.agepredictor.data.remote.model.UserDetails
import io.reactivex.schedulers.Schedulers

class DataRepository(private val appApiHelper: ApiHelper, private val  iParser: IParser) : DataInterface{


    private val _userDetails = MutableLiveData<Resource<UserDetails>>()
    val userDetails: LiveData<Resource<UserDetails>>
        get() = _userDetails


    init {
        initializeFieldsWithUnknownStatus()
    }

    private fun initializeFieldsWithUnknownStatus() {
        _userDetails.postValue(Resource(Status.UNKNOWN, UserDetails(), ""))
    }

    companion object {

        private var INSTANCE: DataRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.
         */
        @JvmStatic
        fun getInstance(
            appApiHelper: AppApiHelper,
            parser: IParser
        ): DataRepository {
            return INSTANCE ?: DataRepository( appApiHelper, parser)
                .apply { INSTANCE = this }
        }

        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic
        fun destroyInstance() {
            INSTANCE = null
        }
    }

    override fun getUserName(name: String) {
        val res = appApiHelper.getUserAge(name).subscribeOn(Schedulers.io())
            .subscribe ({res ->
                if (res != null) {
                    val userDetails = iParser.parseUserAge(res)
                    _userDetails.postValue(Resource(Status.SUCCESS, userDetails ?: UserDetails(), ""))
                }
                else {
                    _userDetails.postValue(Resource(Status.ERROR, UserDetails(), "User not found"))
                }
            }, {
                _userDetails.postValue(
                    Resource(
                        Status.ERROR,
                        UserDetails(),
                        Const.STATUS_MSG_SOME_ERROR_OCCURRED
                    )
                )
            })
    }
}