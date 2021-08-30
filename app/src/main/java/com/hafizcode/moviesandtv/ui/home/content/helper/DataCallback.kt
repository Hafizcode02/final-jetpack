package com.hafizcode.moviesandtv.ui.home.content.helper

import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity

interface DataCallback {
    fun onItemClicked(dataMovie: MovieEntity?, dataTV: TVEntity?)
}