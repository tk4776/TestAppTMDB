package com.marp.testapptmdb.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.marp.testapptmdb.data.repository.NetworkState
import com.marp.testapptmdb.data.vo.Movie
import com.marp.testapptmdb.ui.home.popular_movie.MoviePagedRepository
import io.reactivex.disposables.CompositeDisposable

class HomeViewModel(): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

}