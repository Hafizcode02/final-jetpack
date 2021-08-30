package com.hafizcode.moviesandtv.data.source

import androidx.lifecycle.LiveData
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.vo.Resource

interface MovieDataSource {
    fun getMovies(): LiveData<Resource<List<MovieEntity>>>
    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>>
    fun getTVs(): LiveData<Resource<List<TVEntity>>>
    fun getTVDetail(tvId: Int): LiveData<Resource<TVEntity>>

    fun getBookmarkedMovies(): LiveData<List<MovieEntity>>
    fun getBookmarkedTVs(): LiveData<List<TVEntity>>

    fun setBookmarkedMovies(movie: MovieEntity, state: Boolean)
    fun setBookmarkedTVs(tv: TVEntity, state: Boolean)
}