package com.marp.testapptmdb.ui.home.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.marp.testapptmdb.data.repository.NetworkState
import com.marp.testapptmdb.data.vo.Movie
import com.marp.testapptmdb.ui.home.popular_movie.MoviePagedRepository
import io.reactivex.disposables.CompositeDisposable

class MoviesViewModel (private val movieRepository: MoviePagedRepository) : ViewModel(){

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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}