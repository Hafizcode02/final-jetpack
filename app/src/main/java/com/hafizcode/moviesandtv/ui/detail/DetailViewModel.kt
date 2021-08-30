package com.hafizcode.moviesandtv.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.data.source.MovieRepository
import com.hafizcode.moviesandtv.utils.Helper.MOVIE_TYPE
import com.hafizcode.moviesandtv.utils.Helper.TV_TYPE
import com.hafizcode.moviesandtv.vo.Resource

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private lateinit var detailMovie: LiveData<Resource<MovieEntity>>
    private lateinit var detailTV: LiveData<Resource<TVEntity>>

    fun setData(id: Int, type: String) {
        when (type) {
            MOVIE_TYPE -> {
                detailMovie = movieRepository.getMovieDetail(id)
            }
            TV_TYPE -> {
                detailTV = movieRepository.getTVDetail(id)
            }
        }
    }

    fun setBookmarkedMovie() {
        val movie = detailMovie.value
        if (movie != null) {
            val movieEntity = movie.data
            if (movieEntity != null) {
                val newState = !movieEntity.bookmarked
                movieRepository.setBookmarkedMovies(movieEntity, newState)
            }
        }
    }

    fun setBookmarkedTV() {
        val tv = detailTV.value
        if (tv != null) {
            val tvEntity = tv.data
            if (tvEntity != null) {
                val newState = !tvEntity.bookmarked
                movieRepository.setBookmarkedTVs(tvEntity, newState)
            }
        }
    }

    fun detailMovie() = detailMovie
    fun detailTV() = detailTV
}