package com.hafizcode.moviesandtv.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.vo.Resource

interface MovieDataSource {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>>
    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>>
    fun getTVs(): LiveData<Resource<PagedList<TVEntity>>>
    fun getTVDetail(tvId: Int): LiveData<Resource<TVEntity>>

    fun getBookmarkedMovies(): LiveData<PagedList<MovieEntity>>
    fun getBookmarkedTVs(): LiveData<PagedList<TVEntity>>

    fun setBookmarkedMovies(movie: MovieEntity, state: Boolean)
    fun setBookmarkedTVs(tv: TVEntity, state: Boolean)
}