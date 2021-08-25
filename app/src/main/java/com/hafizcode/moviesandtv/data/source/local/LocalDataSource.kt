package com.hafizcode.moviesandtv.data.source.local

import androidx.lifecycle.LiveData
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.data.source.local.room.MovieDao

class LocalDataSource private constructor(private val mMovieDao: MovieDao) {

    fun getAllMovies(): LiveData<List<MovieEntity>> = mMovieDao.getMovies()

    fun getAllTvs(): LiveData<List<TVEntity>> = mMovieDao.getTVs()

    fun getBookmarkedMovies(): LiveData<List<MovieEntity>> = mMovieDao.getBookmarkedMovies()

    fun getBookmarkedTVs(): LiveData<List<TVEntity>> = mMovieDao.getBookmarkedTVs()

    fun getDetailMovie(id: String): LiveData<MovieEntity> = mMovieDao.getDetailMovie(id)

    fun getDetailTVs(id: String): LiveData<TVEntity> = mMovieDao.getDetailTVs(id)

    fun insertMovies(movies: List<MovieEntity>) = mMovieDao.insertMovies(movies)

    fun insertTVs(tvs: List<TVEntity>) = mMovieDao.insertTVs(tvs)

    fun updateMovie(movie: MovieEntity) = mMovieDao.updateMovie(movie)

    fun updateTV(tv: TVEntity) = mMovieDao.updateTVs(tv)

    fun setBookmarkedMovie(movie: MovieEntity, newState: Boolean) {
        movie.bookmarked = newState
        mMovieDao.updateMovie(movie)
    }

    fun setBookmarkedTV(tv: TVEntity, newState: Boolean) {
        tv.bookmarked = newState
        mMovieDao.updateTVs(tv)
    }

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }
}