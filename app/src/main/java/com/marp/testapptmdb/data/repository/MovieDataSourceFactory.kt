package com.marp.testapptmdb.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.marp.testapptmdb.data.api.TMDBInterface
import com.marp.testapptmdb.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val apiService: TMDBInterface,
    private val compositeDisposable: CompositeDisposable ): DataSource.Factory<Int, Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)

        return movieDataSource
    }


}