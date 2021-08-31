package com.hafizcode.moviesandtv.data.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.data.source.local.LocalDataSource
import com.hafizcode.moviesandtv.data.source.remote.RemoteDataSource
import com.hafizcode.moviesandtv.helper.AppExecutorTest
import com.hafizcode.moviesandtv.helper.LiveDataTestUtil
import com.hafizcode.moviesandtv.helper.PagedListUtil
import com.hafizcode.moviesandtv.utils.AppExecutors
import com.hafizcode.moviesandtv.utils.DataDummy
import com.hafizcode.moviesandtv.vo.Resource
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times

class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)
    private val movieRepository = FakeMovieRepository(remote, local, appExecutors)
    private val testExecutors: AppExecutors =
        AppExecutors(AppExecutorTest(), AppExecutorTest(), AppExecutorTest())

    private val movieResponse = DataDummy.generateDummyMovies()
    private val movieId = movieResponse[0].id
    private val movieDetail = movieResponse[0]

    private val tvsResponse = DataDummy.generateDummyTV()
    private val tvsId = tvsResponse[0].id
    private val tvsDetail = tvsResponse[0]

    @Test
    fun getMovies() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        movieRepository.getMovies()

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllMovies()
        assertNotNull(movieEntities)
        assertEquals(movieResponse.size, movieEntities.data?.size)
    }

    @Test
    fun getDetailMovie() {
        val dummyDetail = MutableLiveData<MovieEntity>()
        dummyDetail.value = movieDetail
        `when`(local.getDetailMovie(movieId)).thenReturn(dummyDetail)

        val movieDetailEntity =
            LiveDataTestUtil.getValue(movieRepository.getMovieDetail(movieId.toInt()))
        verify(local).getDetailMovie(movieId)
        assertNotNull(movieDetailEntity.data)
        assertEquals(movieDetail.id, movieDetailEntity.data?.id)
    }

    @Test
    fun getBookmarkedMovie() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getBookmarkedMovies()).thenReturn(dataSourceFactory)
        movieRepository.getBookmarkedMovies()

        val movieEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getBookmarkedMovies()
        assertNotNull(movieEntities)
        assertEquals(movieResponse.size, movieEntities.data?.size)
    }

    @Test
    fun setBookmarkedMovies() {
        val dataDummy = movieDetail
        val newState: Boolean = !dataDummy.bookmarked

        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).setBookmarkedMovie(dataDummy, newState)

        movieRepository.setBookmarkedMovies(dataDummy, newState)
        verify(local, times(1)).setBookmarkedMovie(dataDummy, newState)
    }

    @Test
    fun getTvsData() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TVEntity>
        `when`(local.getAllTvs()).thenReturn(dataSourceFactory)
        movieRepository.getTVs()

        val tvShowEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTV()))
        verify(local).getAllTvs()
        assertNotNull(tvShowEntities)
        assertEquals(tvsResponse.size, tvShowEntities.data?.size)
    }

    @Test
    fun getDetailTV() {
        val dummyDetail = MutableLiveData<TVEntity>()
        dummyDetail.value = tvsDetail
        `when`(local.getDetailTVs(tvsId)).thenReturn(dummyDetail)

        val tvShowEntities = LiveDataTestUtil.getValue(movieRepository.getTVDetail(tvsId.toInt()))
        verify(local).getDetailTVs(tvsId)
        assertNotNull(tvShowEntities.data)
        assertEquals(tvsDetail.id, tvShowEntities.data?.id)
    }

    @Test
    fun getBookmarkedTV() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TVEntity>
        `when`(local.getBookmarkedTVs()).thenReturn(dataSourceFactory)
        movieRepository.getBookmarkedTVs()

        val tvShowEntities =
            Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTV()))
        verify(local).getBookmarkedTVs()
        assertNotNull(tvShowEntities)
        assertEquals(tvsResponse.size, tvShowEntities.data?.size)
    }

    @Test
    fun setBookmarkedTvs() {
        val dataDummy = tvsDetail
        val newState: Boolean = !dataDummy.bookmarked

        `when`(appExecutors.diskIO()).thenReturn(testExecutors.diskIO())
        doNothing().`when`(local).setBookmarkedTV(dataDummy, newState)

        movieRepository.setBookmarkedTVs(dataDummy, newState)
        verify(local, times(1)).setBookmarkedTV(dataDummy, newState)
    }


}