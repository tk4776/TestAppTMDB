package com.marp.testapptmdb.single_movie

import androidx.lifecycle.LiveData
import com.marp.testapptmdb.data.api.TMDBInterface
import com.marp.testapptmdb.data.repository.MovieDetailsDataSource
import com.marp.testapptmdb.data.repository.NetworkState
import com.marp.testapptmdb.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MoviesRepository (private val apiService: TMDBInterface) {

    lateinit var MovieDetailsDataSource: MovieDetailsDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieId: Int): LiveData<MovieDetails> {
        MovieDetailsDataSource = MovieDetailsDataSource(apiService, compositeDisposable)
        MovieDetailsDataSource.fetchMovieDetails(movieId)

        return MovieDetailsDataSource.dlMovieResponse
    }

    fun getMovieDetailsState(): LiveData<NetworkState> {
        return MovieDetailsDataSource.networkState
    }

}