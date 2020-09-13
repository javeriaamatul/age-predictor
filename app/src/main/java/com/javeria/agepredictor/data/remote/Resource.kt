package com.javeria.agepredictor.data.remote

data class Resource<T>(var status: Status, var data: Any?, var message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> unknown(data: T? = null): Resource<T> = Resource(Status.UNKNOWN, data, null)
    }
}