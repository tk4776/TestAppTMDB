package com.marp.testapptmdb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.marp.testapptmdb.data.repository.NetworkState
import com.marp.testapptmdb.data.vo.Movie
import com.marp.testapptmdb.ui.popular_movie.MoviePagedRepository
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(private val movieRepository: MoviePagedRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<Movie>> by lazy {
        movieRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}