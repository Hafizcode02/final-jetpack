package com.hafizcode.moviesandtv.ui.home.content.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hafizcode.moviesandtv.data.entity.DataEntity
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.data.source.MovieRepository
import com.hafizcode.moviesandtv.vo.Resource

class DataViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getMovies(): LiveData<Resource<List<MovieEntity>>> = movieRepository.getMovies()
    fun getTvs(): LiveData<Resource<List<TVEntity>>> = movieRepository.getTVs()

    fun getBookmarkedMovies(): LiveData<List<MovieEntity>> = movieRepository.getBookmarkedMovies()
    fun getBookmarkedTVs(): LiveData<List<TVEntity>> = movieRepository.getBookmarkedTVs()
}