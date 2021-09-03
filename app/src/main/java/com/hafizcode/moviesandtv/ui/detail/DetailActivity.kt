package com.hafizcode.moviesandtv.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hafizcode.moviesandtv.R
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.databinding.ActivityDetailBinding
import com.hafizcode.moviesandtv.utils.Helper.IMAGE_API_ENDPOINT
import com.hafizcode.moviesandtv.utils.Helper.MOVIE_TYPE
import com.hafizcode.moviesandtv.utils.Helper.TV_TYPE
import com.hafizcode.moviesandtv.viewmodel.ViewModelFactory
import com.hafizcode.moviesandtv.vo.Status

class DetailActivity : AppCompatActivity() {
    companion object {
        const val DATA_ID = "DATA_ID"
        const val DATA_TYPE = "DATA_TYPE"
    }

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var menu: Menu? = null
    private var dataType = ""
    private var state: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        val dataId = intent.getStringExtra(DATA_ID).orEmpty()
        dataType = intent.getStringExtra(DATA_TYPE)!!

        supportActionBar?.title =
            if (dataType == MOVIE_TYPE) "Detail Movie" else "Detail TV"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(applicationContext)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        if (intent.extras != null) {
            if (dataType.equals(MOVIE_TYPE, true)) {
                viewModel.setData(dataId.toInt(), MOVIE_TYPE)
                viewModel.detailMovie().observe(this, {
                    when (it.status) {
                        Status.SUCCESS -> {
                            if (it.data != null) {
                                state = it.data.bookmarked
                                displayContentMovie(it.data)
                            }
                        }
                        Status.ERROR -> Toast.makeText(
                            applicationContext,
                            it.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        Status.LOADING -> Log.d("LOADING", "Data Is Loading.....")
                    }
                })
            } else if (dataType.equals(TV_TYPE, true)) {
                viewModel.setData(dataId.toInt(), TV_TYPE)
                viewModel.detailTV().observe(this, {
                    when (it.status) {
                        Status.SUCCESS -> {
                            if (it.data != null) {
                                state = it.data.bookmarked
                                displayContentTV(it.data)
                            }
                        }
                        Status.ERROR -> Toast.makeText(
                            applicationContext,
                            it.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        Status.LOADING -> Log.d("LOADING", "Data Is Loading.....")
                    }
                })
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        setBookmarkState(state)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_fav) {
            when (dataType) {
                MOVIE_TYPE -> {
                    viewModel.setBookmarkedMovie()
                    if (state) {
                        Toast.makeText(
                            applicationContext,
                            "Cancel Movie to Select as Favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Movie Selected as Favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    setBookmarkState(!state)
                }
                TV_TYPE -> {
                    viewModel.setBookmarkedTV()
                    if (state) {
                        Toast.makeText(
                            applicationContext,
                            "Cancel TV Show to Select as Favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "TV Show Selected as Favorite",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    setBookmarkState(!state)
                }
                else -> Toast.makeText(applicationContext, "Something is Wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBookmarkState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_fav)
        if (state) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_star_24)
        } else {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_white)
        }
    }

    private fun displayContentMovie(data: MovieEntity) {
        Glide.with(applicationContext).load(IMAGE_API_ENDPOINT + data.imgPoster).apply(
            RequestOptions.placeholderOf(R.drawable.ic_loading_black)
                .error(R.drawable.ic_error_image)
        ).apply(RequestOptions().override(700, 700)).into(activityDetailBinding.imageItem)

        activityDetailBinding.textTitle.text = data.title
        activityDetailBinding.textRatingFilm.text = data.ratingFilm
        activityDetailBinding.textGenre.text = when (data.genre?.isNotEmpty()) {
            true -> data.genre
            else -> getString(R.string.no_genres)
        }

        val tempRatingFor = when (data.ratingFor?.isNotEmpty()) {
            true -> data.ratingFor.toString()
            else -> getString(R.string.dashes)
        }
        val tempPlayedHour = when (data.playedHour?.isNotEmpty()) {
            true -> data.playedHour.toString()
            else -> getString(R.string.dashes)
        }
        activityDetailBinding.textRatingHour.text =
            getString(R.string.rating_hour, tempRatingFor, tempPlayedHour)

        activityDetailBinding.textDate.text = when (data.releasedYear?.isNotEmpty()) {
            true -> data.releasedYear
            else -> getString(R.string.dashes)
        }

        activityDetailBinding.textDescription.text = when (data.description?.isNotEmpty()) {
            true -> data.description
            else -> getString(R.string.no_description)
        }
    }

    private fun displayContentTV(data: TVEntity) {
        Glide.with(applicationContext).load(IMAGE_API_ENDPOINT + data.imgPoster).apply(
            RequestOptions.placeholderOf(R.drawable.ic_loading_black)
                .error(R.drawable.ic_error_image)
        ).apply(RequestOptions().override(700, 700)).into(activityDetailBinding.imageItem)

        activityDetailBinding.textTitle.text = data.title
        activityDetailBinding.textRatingFilm.text = data.ratingFilm
        activityDetailBinding.textGenre.text = when (data.genre?.isNotEmpty()) {
            true -> data.genre
            else -> getString(R.string.no_genres)
        }

        val tempRatingFor = when (data.ratingFor?.isNotEmpty()) {
            true -> data.ratingFor.toString()
            else -> getString(R.string.dashes)
        }
        val tempPlayedHour = when (data.playedHour?.isNotEmpty()) {
            true -> data.playedHour.toString()
            else -> getString(R.string.dashes)
        }
        activityDetailBinding.textRatingHour.text =
            getString(R.string.rating_hour, tempRatingFor, tempPlayedHour)

        activityDetailBinding.textDate.text = when (data.releasedYear?.isNotEmpty()) {
            true -> data.releasedYear
            else -> getString(R.string.dashes)
        }

        activityDetailBinding.textDescription.text = when (data.description?.isNotEmpty()) {
            true -> data.description
            else -> getString(R.string.no_description)
        }
    }
}