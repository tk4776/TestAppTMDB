package com.marp.testapptmdb.data.repository

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status, val msg: String) {
    companion object {
        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val ENDOFLIST: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success")
            LOADING = NetworkState(Status.RUNNING, "Wait a Second")
            ERROR = NetworkState(Status.FAILED, "Something Went Wrong")
            ENDOFLIST = NetworkState(Status.FAILED, "You have reached the end")
        }
    }
}