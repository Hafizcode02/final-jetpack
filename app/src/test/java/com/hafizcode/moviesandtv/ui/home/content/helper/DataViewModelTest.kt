package com.hafizcode.moviesandtv.ui.home.content.helper

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.data.source.MovieRepository
import com.hafizcode.moviesandtv.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataViewModelTest {

    private lateinit var viewModel: DataViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var tvObserver: Observer<Resource<PagedList<TVEntity>>>

    @Mock
    private lateinit var bookmarkMovieObserver: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var bookmarkTVObserver: Observer<PagedList<TVEntity>>

    @Mock
    private lateinit var moviePagedList: PagedList<MovieEntity>

    @Mock
    private lateinit var tvPagedList: PagedList<TVEntity>

    @Before
    fun setUp() {
        viewModel = DataViewModel(movieRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = Resource.success(moviePagedList)
        `when`(dummyMovies.data?.size).thenReturn(3)

        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(movieRepository.getMovies()).thenReturn(movies)

        val movie = viewModel.getMovies().value?.data
        verify(movieRepository).getMovies()
        assertNotNull(movie)
        assertEquals(3, movie?.size)

        viewModel.getMovies().observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovies)
    }

    @Test
    fun getBookmarkedMovies() {
        val dummyMovie = moviePagedList
        `when`(dummyMovie.size).thenReturn(3)

        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyMovie

        `when`(movieRepository.getBookmarkedMovies()).thenReturn(movies)
        val movie = viewModel.getBookmarkedMovies().value
        verify(movieRepository).getBookmarkedMovies()
        assertNotNull(movie)
        assertEquals(3, movie?.size)

        viewModel.getBookmarkedMovies().observeForever(bookmarkMovieObserver)
        verify(bookmarkMovieObserver).onChanged(dummyMovie)
    }

    @Test
    fun getTVs() {
        val dummyTVs = Resource.success(tvPagedList)
        `when`(dummyTVs.data?.size).thenReturn(3)

        val tvs = MutableLiveData<Resource<PagedList<TVEntity>>>()
        tvs.value = dummyTVs

        `when`(movieRepository.getTVs()).thenReturn(tvs)

        val tv = viewModel.getTvs().value?.data
        verify(movieRepository).getTVs()
        assertNotNull(tv)
        assertEquals(3, tv?.size)

        viewModel.getTvs().observeForever(tvObserver)
        verify(tvObserver).onChanged(dummyTVs)
    }

    @Test
    fun getBookmarkedTvs() {
        val dummyTV = tvPagedList
        `when`(dummyTV.size).thenReturn(3)

        val tvs = MutableLiveData<PagedList<TVEntity>>()
        tvs.value = dummyTV

        `when`(movieRepository.getBookmarkedTVs()).thenReturn(tvs)
        val tv = viewModel.getBookmarkedTVs().value
        verify(movieRepository).getBookmarkedTVs()
        assertNotNull(tv)
        assertEquals(3, tv?.size)

        viewModel.getBookmarkedTVs().observeForever(bookmarkTVObserver)
        verify(bookmarkTVObserver).onChanged(dummyTV)
    }
}