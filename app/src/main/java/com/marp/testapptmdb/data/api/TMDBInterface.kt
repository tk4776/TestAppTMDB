package com.marp.testapptmdb.data.api

import com.marp.testapptmdb.data.vo.MovieDetails
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBInterface {
    /*
    url for obtaining popular movies:
        https://api.themoviedb.org/3/movie/popular?api_key=MY_API_KEY
    url for obtaining one movie:
        https://api.themoviedb.org/3/movie/577922?api_key=MY_API_KEY&language=en-US
    general url:
        https://api.themoviedb.org/3/
     */

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}