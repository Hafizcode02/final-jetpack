package com.hafizcode.moviesandtv.di

import android.content.Context
import com.hafizcode.moviesandtv.data.source.MovieRepository
import com.hafizcode.moviesandtv.data.source.local.LocalDataSource
import com.hafizcode.moviesandtv.data.source.local.room.MovieDatabase
import com.hafizcode.moviesandtv.data.source.remote.RemoteDataSource
import com.hafizcode.moviesandtv.utils.AppExecutors

object Injection {
    fun provideMovieRepository(context: Context): MovieRepository {
        val database = MovieDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()

        return MovieRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}