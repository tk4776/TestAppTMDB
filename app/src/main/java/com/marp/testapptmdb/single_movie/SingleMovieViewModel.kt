package com.marp.testapptmdb.single_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.marp.testapptmdb.data.repository.NetworkState
import com.marp.testapptmdb.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(
    private val movieRepository: MoviesRepository,
    movieId: Int ): ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val movieDetails: LiveData<MovieDetails> by lazy {
        movieRepository.fetchSingleMovieDetails(compositeDisposable, movieId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        movieRepository.getMovieDetailsState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}