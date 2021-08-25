package com.hafizcode.moviesandtv.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hafizcode.moviesandtv.data.source.remote.api.ApiClient
import com.hafizcode.moviesandtv.data.source.remote.response.ApiResponse
import com.hafizcode.moviesandtv.data.source.remote.response.ListResponse
import com.hafizcode.moviesandtv.data.source.remote.response.movie.MovieResponse
import com.hafizcode.moviesandtv.data.source.remote.response.movie.RatedForResponse
import com.hafizcode.moviesandtv.data.source.remote.response.tv.TVRatedResponse
import com.hafizcode.moviesandtv.data.source.remote.response.tv.TVResponse
import com.hafizcode.moviesandtv.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

class RemoteDataSource {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getMovies(): LiveData<ApiResponse<List<MovieResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<MovieResponse>>>()
        val client = ApiClient.instance.getMovies()

        client.enqueue(object : Callback<ListResponse<MovieResponse>> {
            override fun onResponse(
                call: Call<ListResponse<MovieResponse>>,
                response: Response<ListResponse<MovieResponse>>
            ) {
                resultMovies.value =
                    ApiResponse.success(response.body()?.result as List<MovieResponse>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<ListResponse<MovieResponse>>, t: Throwable) {
                Log.d("RemoteDataSource", "getMovies onFailure: ${t.message}")
            }

        })

        return resultMovies
    }

    fun getMovieDetail(movieId: Int): LiveData<ApiResponse<MovieResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailMovies = MutableLiveData<ApiResponse<MovieResponse>>()
        val client = ApiClient.instance.getDetailMovies(movieId)

        client.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                resultDetailMovies.value = ApiResponse.success(response.body() as MovieResponse)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                Log.d("RemoteDataSource", "getMovieDetail onFailure: ${t.message}")
            }

        })

        return resultDetailMovies
    }

    fun getMovieDetailRatedFor(movieId: Int): LiveData<ApiResponse<RatedForResponse>> {
        EspressoIdlingResource.increment()
        val resultRatedForMovie = MutableLiveData<ApiResponse<RatedForResponse>>()
        val client = ApiClient.instance.getRatedForMovies(movieId)

        client.enqueue(object : Callback<RatedForResponse> {
            override fun onResponse(
                call: Call<RatedForResponse>,
                response: Response<RatedForResponse>
            ) {
                resultRatedForMovie.value = ApiResponse.success(response.body() as RatedForResponse)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<RatedForResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                Log.d("RemoteDataSource", "getMovieDetailRatedFor onFailure: ${t.message}")
            }

        })

        return resultRatedForMovie
    }

    fun getTVs(): LiveData<ApiResponse<List<TVResponse>>> {
        EspressoIdlingResource.increment()
        val resultTVs = MutableLiveData<ApiResponse<List<TVResponse>>>()
        val client = ApiClient.instance.getTVs()

        client.enqueue(object : Callback<ListResponse<TVResponse>> {
            override fun onResponse(
                call: Call<ListResponse<TVResponse>>,
                response: Response<ListResponse<TVResponse>>
            ) {
                resultTVs.value = ApiResponse.success(response.body()?.result as List<TVResponse>)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<ListResponse<TVResponse>>, t: Throwable) {
                EspressoIdlingResource.decrement()
                Log.d("RemoteDataSource", "getTVs onFailure: ${t.message}")
            }


        })

        return resultTVs
    }

    fun getTVDetail(tvId: Int): LiveData<ApiResponse<TVResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailTVs = MutableLiveData<ApiResponse<TVResponse>>()
        val client = ApiClient.instance.getTvDetail(tvId)

        client.enqueue(object : Callback<TVResponse> {
            override fun onResponse(call: Call<TVResponse>, response: Response<TVResponse>) {
                resultDetailTVs.value = ApiResponse.success(response.body() as TVResponse)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TVResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                Log.d("RemoteDataSource", "getTVDetail onFailure: ${t.message}")
            }

        })

        return resultDetailTVs
    }

    fun getTVDetailRatedFor(tvId: Int): LiveData<ApiResponse<TVRatedResponse>> {
        EspressoIdlingResource.increment()
        val resultRatedTV = MutableLiveData<ApiResponse<TVRatedResponse>>()
        val client = ApiClient.instance.getRatedForTV(tvId)

        client.enqueue(object : Callback<TVRatedResponse> {
            override fun onResponse(
                call: Call<TVRatedResponse>,
                response: Response<TVRatedResponse>
            ) {
                resultRatedTV.value = ApiResponse.success(response.body() as TVRatedResponse)
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TVRatedResponse>, t: Throwable) {
                EspressoIdlingResource.decrement()
                Log.d("RemoteDataSource", "getTVDetailRatedFor onFailure: ${t.message}")
            }

        })

        return resultRatedTV
    }
}