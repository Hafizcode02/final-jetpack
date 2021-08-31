package com.hafizcode.moviesandtv.ui.home.content.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.hafizcode.moviesandtv.data.entity.DataEntity
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.data.source.MovieRepository
import com.hafizcode.moviesandtv.vo.Resource

class DataViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = movieRepository.getMovies()
    fun getTvs(): LiveData<Resource<PagedList<TVEntity>>> = movieRepository.getTVs()

    fun getBookmarkedMovies(): LiveData<PagedList<MovieEntity>> = movieRepository.getBookmarkedMovies()
    fun getBookmarkedTVs(): LiveData<PagedList<TVEntity>> = movieRepository.getBookmarkedTVs()
}