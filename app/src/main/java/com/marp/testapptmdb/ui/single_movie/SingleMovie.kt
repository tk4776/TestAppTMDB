package com.marp.testapptmdb.ui.single_movie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.marp.testapptmdb.R
import com.marp.testapptmdb.data.api.MovieClient
import com.marp.testapptmdb.data.api.POSTER_BASE_URL
import com.marp.testapptmdb.data.api.TMDBInterface
import com.marp.testapptmdb.data.repository.NetworkState
import com.marp.testapptmdb.data.vo.MovieDetails
import java.text.NumberFormat
import java.util.*

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var moviesRepository: MoviesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)


        val progress_bar: ProgressBar = findViewById(R.id.progress_bar) as ProgressBar
        val text_error: TextView = findViewById(R.id.text_error) as TextView

        //To start activity, test only
        val movieId: Int = intent.getIntExtra("id", 1)

        val apiService: TMDBInterface = MovieClient.getClient()
        moviesRepository =
            MoviesRepository(apiService)

        viewModel = getViewModel(movieId)

        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            text_error.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
    }

    fun bindUI(it: MovieDetails) {

        val movie_title: TextView = findViewById(R.id.movie_title) as TextView
        val movie_tagline: TextView = findViewById(R.id.movie_tagline) as TextView
        val movie_release_date: TextView = findViewById(R.id.movie_release_date) as TextView
        val movie_rating: TextView = findViewById(R.id.movie_rating) as TextView
        val movie_runtime: TextView = findViewById(R.id.movie_runtime) as TextView
        val movie_overview: TextView = findViewById(R.id.movie_overview) as TextView
        val movie_budget: TextView = findViewById(R.id.movie_overview) as TextView
        val movie_revenue: TextView = findViewById(R.id.movie_title) as TextView
        val movie_poster: ImageView = findViewById(R.id.movie_poster) as ImageView

        movie_title.text = it.title
        movie_tagline.text = it.tagline
        movie_release_date.text = it.releaseDate
        movie_rating.text = it.voteAverage.toString()
        movie_runtime.text = it.runtime.toString() + "minutes"
        movie_overview.text = it.overview

        val formatCurrency: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
        movie_budget.text = formatCurrency.format(it.budget)
        movie_revenue.text = formatCurrency.format(it.revenue)

        val moviePosterURL: String = POSTER_BASE_URL + it.posterPath
        Glide.with(this)
            .load(moviePosterURL)
            .into(movie_poster);
    }

    /**
     * NOTE: View Model Providers is depricated, the new standard say one must use VIEWMODELPROVIDER
     * despite for using parameters there's need to create an AUX CLASS that handle the View Model Factory
     * then create the function that uses the class and pass the parameter.
     *
     * E.G.
     * private fun getViewModel(movieId: Int): SingleMovieViewModel {
     *      return ViewModelProvider(this@SingleMovie).get(SingleMovieViewModel()::class.java)
     *      }
     *
     *
     * In order to 'simplify' and finish as much as I could, I implement "lifecycle-providers" in Gradle
     *
     *
     **/
    private fun getViewModel(movieId: Int): SingleMovieViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory{
            override fun <T: ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SingleMovieViewModel(moviesRepository, movieId) as T
            }
        })[SingleMovieViewModel::class.java]
    }
}