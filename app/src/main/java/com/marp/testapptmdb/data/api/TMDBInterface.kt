package com.marp.testapptmdb.data.api

import com.marp.testapptmdb.data.vo.MovieDetails
import com.marp.testapptmdb.data.vo.PopularMovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBInterface {
    /*
    url for obtaining popular movies:
        https://api.themoviedb.org/3/movie/popular?api_key=MY_API_KEY&page=1
    url for obtaining one movie:
        https://api.themoviedb.org/3/movie/577922?api_key=MY_API_KEY&language=en-US
    general url:
        https://api.themoviedb.org/3/
     */
    @GET("movie/popular")
    fun getPopularMovie(@Query("Page") page: Int): Single<PopularMovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}