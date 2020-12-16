package com.marp.testapptmdb.ui.home.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.marp.testapptmdb.data.api.POST_PER_PAGE
import com.marp.testapptmdb.data.api.TMDBInterface
import com.marp.testapptmdb.data.repository.MovieDataSource
import com.marp.testapptmdb.data.repository.MovieDataSourceFactory
import com.marp.testapptmdb.data.repository.NetworkState
import com.marp.testapptmdb.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedRepository(private val apiService: TMDBInterface) {
    lateinit var moviePagedList: LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        movieDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return  Transformations.switchMap<MovieDataSource, NetworkState>(
            movieDataSourceFactory.moviesLiveDataSource, MovieDataSource::networkState)
    }

}