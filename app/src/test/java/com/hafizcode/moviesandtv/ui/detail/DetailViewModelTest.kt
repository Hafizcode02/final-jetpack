package com.hafizcode.moviesandtv.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.data.source.MovieRepository
import com.hafizcode.moviesandtv.utils.DataDummy
import com.hafizcode.moviesandtv.utils.Helper.MOVIE_TYPE
import com.hafizcode.moviesandtv.utils.Helper.TV_TYPE
import com.hafizcode.moviesandtv.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val dummyTv = DataDummy.generateDummyTV()[0]

    private val dummyMovieId = dummyMovie.id
    private val dummyTvId = dummyTv.id

    private lateinit var detailViewModel: DetailViewModel

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<TVEntity>>

    @Before
    fun setUpMovie() {
        detailViewModel = DetailViewModel(movieRepository)
    }

    @Test
    fun getDetailMovie() {
        val dummyDetailMovie = Resource.success(dummyMovie)
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyDetailMovie

        `when`(movieRepository.getMovieDetail(dummyMovieId.toInt())).thenReturn(movie)

        detailViewModel.setData(dummyMovieId.toInt(), MOVIE_TYPE)
        detailViewModel.detailMovie().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyDetailMovie)
    }

    @Test
    fun setBookmarkedMovie() {
        val dummyDetailMovie = Resource.success(dummyMovie)
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyDetailMovie

        `when`(movieRepository.getMovieDetail(dummyMovieId.toInt())).thenReturn(movie)

        detailViewModel.setData(dummyMovieId.toInt(), MOVIE_TYPE)
        detailViewModel.setBookmarkedMovie()
        verify(movieRepository).setBookmarkedMovies(movie.value!!.data as MovieEntity, true)
        verifyNoMoreInteractions(movieObserver)
    }

    @Test
    fun getDetailTvs() {
        val dummyTVShow = Resource.success(dummyTv)
        val tvShow = MutableLiveData<Resource<TVEntity>>()
        tvShow.value = dummyTVShow

        `when`(movieRepository.getTVDetail(dummyTvId.toInt())).thenReturn(tvShow)

        detailViewModel.setData(dummyTvId.toInt(), TV_TYPE)
        detailViewModel.detailTV().observeForever(tvObserver)
        verify(tvObserver).onChanged(dummyTVShow)
    }

    @Test
    fun setBookmarkedTv() {
        val dummyTVShow = Resource.success(dummyTv)
        val tvShow = MutableLiveData<Resource<TVEntity>>()
        tvShow.value = dummyTVShow

        `when`(movieRepository.getTVDetail(dummyTvId.toInt())).thenReturn(tvShow)

        detailViewModel.setData(dummyTvId.toInt(), TV_TYPE)
        detailViewModel.setBookmarkedTV()
        verify(movieRepository).setBookmarkedTVs(tvShow.value!!.data as TVEntity, true)
        verifyNoMoreInteractions(tvObserver)
    }

}