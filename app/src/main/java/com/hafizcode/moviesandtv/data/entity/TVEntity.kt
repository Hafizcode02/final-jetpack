package com.hafizcode.moviesandtv.data.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_tv_show")
data class TVEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "genre")
    var genre: String? = null,

    @ColumnInfo(name = "releasedYear")
    var releasedYear: String? = null,

    @ColumnInfo(name = "ratingFor")
    var ratingFor: String? = null,

    @ColumnInfo(name = "ratingFilm")
    var ratingFilm: String? = null,

    @ColumnInfo(name = "playedHour")
    var playedHour: String? = null,

    @ColumnInfo(name = "imgPoster")
    var imgPoster: String,

    @ColumnInfo(name = "bookmarked")
    var bookmarked: Boolean = false
)
