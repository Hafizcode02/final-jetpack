package com.hafizcode.moviesandtv.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hafizcode.moviesandtv.data.entity.DataEntity
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.data.source.MovieRepository
import com.hafizcode.moviesandtv.utils.Helper.MOVIE_TYPE
import com.hafizcode.moviesandtv.vo.Resource

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val selectedIdMovie = MutableLiveData<Int>()
    private val selectedIdTV = MutableLiveData<Int>()
    private val typeData = MutableLiveData<String>()

    fun setSelectedMovieId(id: Int) {
        this.selectedIdMovie.value = id
    }

    fun selectedTVId(id: Int) {
        this.selectedIdTV.value = id
    }

    var detailMovie: LiveData<Resource<MovieEntity>> =
        Transformations.switchMap(selectedIdMovie) { id ->
            selectedIdMovie.value?.let { movieRepository.getMovieDetail(id) }
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

    var detailTV: LiveData<Resource<TVEntity>> =
        Transformations.switchMap(selectedIdTV) { id ->
            selectedIdTV.value?.let { movieRepository.getTVDetail(id) }
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
}