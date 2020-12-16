package com.marp.testapptmdb.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.marp.testapptmdb.R
import com.marp.testapptmdb.data.api.MovieClient
import com.marp.testapptmdb.data.api.TMDBInterface
import com.marp.testapptmdb.data.repository.NetworkState
import com.marp.testapptmdb.ui.home.popular_movie.MoviePagedRepository
import com.marp.testapptmdb.ui.home.popular_movie.Movies
import com.marp.testapptmdb.ui.home.popular_movie.PopularMoviePagedListAdapter
import com.marp.testapptmdb.ui.home.single_movie.SingleMovie

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val btn: Button = root.findViewById(R.id.see_movies)
        btn.setOnClickListener{
            val intent = Intent(this@HomeFragment.context, SingleMovie::class.java)
            intent.putExtra("id",590706)
            startActivity(intent)
        }

        return root
    }
}