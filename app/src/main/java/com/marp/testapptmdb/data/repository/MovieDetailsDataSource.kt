package com.marp.testapptmdb.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marp.testapptmdb.data.api.TMDBInterface
import com.marp.testapptmdb.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieDetailsDataSource (
        private val apiService : TMDBInterface,
        private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _dlMovieResponse = MutableLiveData<MovieDetails>()
    val dlMovieResponse: LiveData<MovieDetails>
        get() = _dlMovieResponse

    fun fetchMovieDetails(movieId: Int){
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                    apiService.getMovieDetails(movieId)
                            .subscribeOn(Schedulers.io())
                            .subscribe(
                                    {
                                        _dlMovieResponse.postValue(it)
                                        _networkState.postValue(NetworkState.LOADED)
                                    },
                                    {
                                        _networkState.postValue(NetworkState.ERROR)
                                        Log.e("Movie Details Source", it.message)
                                    })
            )

        } catch (e: Exception) {
            Log.e("Movie Details Source", e.message)
        }
    }
}