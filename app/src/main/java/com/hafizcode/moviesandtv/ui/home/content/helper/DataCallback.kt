package com.hafizcode.moviesandtv.ui.home.content.helper

import com.hafizcode.moviesandtv.data.entity.DataEntity

interface DataCallback {
    fun onItemClicked(data: DataEntity)
}