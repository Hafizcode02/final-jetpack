package com.hafizcode.moviesandtv.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.data.source.local.LocalDataSource
import com.hafizcode.moviesandtv.data.source.remote.RemoteDataSource
import com.hafizcode.moviesandtv.data.source.remote.response.ApiResponse
import com.hafizcode.moviesandtv.data.source.remote.response.movie.MovieResponse
import com.hafizcode.moviesandtv.data.source.remote.response.tv.TVResponse
import com.hafizcode.moviesandtv.utils.AppExecutors
import com.hafizcode.moviesandtv.utils.Helper.convertDate
import com.hafizcode.moviesandtv.utils.Helper.convertMinutesToHour
import com.hafizcode.moviesandtv.vo.Resource

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : MovieDataSource {

    private var genresData: String = ""
    private var ratedForCertification: String = ""
    private var playedHourTV: String = ""

    companion object {
        @Volatile
        private var instance: MovieRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource, localData, appExecutors)
            }
    }

    override fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MovieEntity>, List<MovieResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getMovies()
            }

            override fun saveCallResult(data: List<MovieResponse>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in data) {
                    val movie = MovieEntity(
                        id = response.id.toString(),
                        title = response.title.toString(),
                        description = "",
                        genre = "",
                        releasedYear = "",
                        ratingFor = "",
                        ratingFilm = "",
                        playedHour = "",
                        imgPoster = response.posterPath.toString()
                    )
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getMovieDetail(movieId: Int): LiveData<Resource<MovieEntity>> {

        ratedForCertification = ""
        genresData = ""

        return object : NetworkBoundResource<MovieEntity, MovieResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<MovieEntity> {
                return localDataSource.getDetailMovie(movieId.toString())
            }

            override fun shouldFetch(data: MovieEntity?): Boolean {
                return data != null && data.description == ""
            }

            override fun createCall(): LiveData<ApiResponse<MovieResponse>> {
                val dataRated = remoteDataSource.getMovieDetailRatedFor(movieId)
                dataRated.value?.body?.results?.forEach {
                    if (it?.iso31661.equals("RU", true)) {
                        ratedForCertification =
                            it?.releaseDates?.getOrNull(0)?.certification.toString()
                    }
                }
                return remoteDataSource.getMovieDetail(movieId)
            }

            override fun saveCallResult(data: MovieResponse) {
                data.genres?.forEachIndexed { index, value ->
                    if (index < 1) genresData += "${value?.name}"
                    else if (index >= 1) genresData += ", ${value?.name}"
                }
                val movie = MovieEntity(
                    id = data.id.toString(),
                    title = data.title,
                    description = data.overview,
                    genre = genresData,
                    releasedYear = convertDate(data.releaseDate.toString()),
                    ratingFor = ratedForCertification,
                    ratingFilm = data.voteAverage.toString(),
                    playedHour = data.runtime?.let { convertMinutesToHour(it) },
                    imgPoster = data.posterPath.toString(),
                )
                localDataSource.updateMovie(movie)
            }
        }.asLiveData()
    }

    override fun getTVs(): LiveData<Resource<PagedList<TVEntity>>> {
        return object : NetworkBoundResource<PagedList<TVEntity>, List<TVResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TVEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvs(), config).build()
            }

            override fun shouldFetch(data: PagedList<TVEntity>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<TVResponse>>> {
                return remoteDataSource.getTVs()
            }

            override fun saveCallResult(data: List<TVResponse>) {
                val tvList = ArrayList<TVEntity>()
                for (response in data) {
                    val tv = TVEntity(
                        id = response.id.toString(),
                        title = response.name.toString(),
                        description = "",
                        genre = "",
                        releasedYear = "",
                        ratingFor = "",
                        ratingFilm = "",
                        playedHour = "",
                        imgPoster = response.posterPath.toString()
                    )
                    tvList.add(tv)
                }
                localDataSource.insertTVs(tvList)
            }
        }.asLiveData()
    }

    override fun getTVDetail(tvId: Int): LiveData<Resource<TVEntity>> {

        ratedForCertification = ""
        genresData = ""

        return object : NetworkBoundResource<TVEntity, TVResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TVEntity> {
                return localDataSource.getDetailTVs(tvId.toString())
            }

            override fun shouldFetch(data: TVEntity?): Boolean {
                return data != null && data.playedHour == ""
            }

            override fun createCall(): LiveData<ApiResponse<TVResponse>> {
                val dataRated = remoteDataSource.getTVDetailRatedFor(tvId)
                dataRated.value?.body?.results?.forEach {
                    if (it?.iso31661.equals("RU", true)) {
                        ratedForCertification =
                            it?.rating.toString()
                    }
                }
                return remoteDataSource.getTVDetail(tvId)
            }

            override fun saveCallResult(data: TVResponse) {
                data.genres?.forEachIndexed { index, nameData ->
                    if (index < 1) genresData += "${nameData?.name}"
                    else if (index >= 1) genresData += ", ${nameData?.name}"
                }
                data.episodeRunTime?.let {
                    if (!data.episodeRunTime.isNullOrEmpty()) {
                        it[0]?.let { it1 ->
                            playedHourTV = convertMinutesToHour(
                                it1
                            )
                        }
                    }
                }
                val tv = TVEntity(
                    id = data.id.toString(),
                    title = data.name,
                    description = data.overview,
                    genre = genresData,
                    releasedYear = convertDate(data.firstAirDate.toString()),
                    ratingFor = ratedForCertification,
                    ratingFilm = data.voteAverage.toString(),
                    playedHour = playedHourTV,
                    imgPoster = data.posterPath.toString()
                )
                localDataSource.updateTV(tv)

            }

        }.asLiveData()
    }

    override fun getBookmarkedMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getBookmarkedMovies(), config).build()
    }

    override fun getBookmarkedTVs(): LiveData<PagedList<TVEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getBookmarkedTVs(), config).build()
    }

    override fun setBookmarkedMovies(movie: MovieEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setBookmarkedMovie(movie, state)
        }
    }

    override fun setBookmarkedTVs(tv: TVEntity, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.setBookmarkedTV(tv, state)
        }
    }

}