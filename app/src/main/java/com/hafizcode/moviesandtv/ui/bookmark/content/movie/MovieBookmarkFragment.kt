package com.hafizcode.moviesandtv.ui.bookmark.content.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.databinding.FragmentMovieBinding
import com.hafizcode.moviesandtv.ui.detail.DetailActivity
import com.hafizcode.moviesandtv.ui.home.content.helper.DataCallback
import com.hafizcode.moviesandtv.ui.home.content.helper.DataViewModel
import com.hafizcode.moviesandtv.utils.Helper
import com.hafizcode.moviesandtv.viewmodel.ViewModelFactory

class MovieBookmarkFragment : Fragment(), DataCallback {

    private lateinit var fragmentMovieBinding: FragmentMovieBinding
    private lateinit var viewModel: DataViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentMovieBinding.rvMovie.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = MovieBookmarkAdapter(this@MovieBookmarkFragment)
        }

        val factory = ViewModelFactory.getInstance(requireActivity())
        activity?.let {
            viewModel = ViewModelProvider(it, factory)[DataViewModel::class.java]
        }

        viewModel.getBookmarkedMovies().observe(viewLifecycleOwner, { listMovie ->
            fragmentMovieBinding.rvMovie.adapter?.let { adapter ->
                when (adapter) {
                    is MovieBookmarkAdapter -> adapter.setBookmarkedMovies(listMovie)
                }
            }
        })
    }

    override fun onItemClicked(dataMovie: MovieEntity?, dataTV: TVEntity?) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.DATA_ID, dataMovie?.id)
        intent.putExtra(DetailActivity.DATA_TYPE, Helper.MOVIE_TYPE)
        startActivity(intent)
    }
}