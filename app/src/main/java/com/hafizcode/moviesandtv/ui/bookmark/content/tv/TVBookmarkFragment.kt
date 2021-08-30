package com.hafizcode.moviesandtv.ui.bookmark.content.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.databinding.FragmentTvBinding
import com.hafizcode.moviesandtv.ui.detail.DetailActivity
import com.hafizcode.moviesandtv.ui.home.content.helper.DataCallback
import com.hafizcode.moviesandtv.ui.home.content.helper.DataViewModel
import com.hafizcode.moviesandtv.ui.home.content.tv.TvAdapter
import com.hafizcode.moviesandtv.utils.Helper
import com.hafizcode.moviesandtv.viewmodel.ViewModelFactory

class TVBookmarkFragment : Fragment(), DataCallback {

    private lateinit var fragmentTvBinding: FragmentTvBinding
    private lateinit var viewModel: DataViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTvBinding = FragmentTvBinding.inflate(layoutInflater, container, false)
        return fragmentTvBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentTvBinding.rvTv.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = TVBookmarkAdapter(this@TVBookmarkFragment)
        }

        val factory = ViewModelFactory.getInstance(requireActivity())
        activity?.let {
            viewModel = ViewModelProvider(it, factory)[DataViewModel::class.java]
        }

        viewModel.getBookmarkedTVs().observe(viewLifecycleOwner, { listTV ->
            fragmentTvBinding.rvTv.adapter?.let { adapter ->
                when (adapter) {
                    is TVBookmarkAdapter -> {
                        adapter.run { setBookmarkedTvs(listTV) }
                    }
                }
            }
        })
    }

    override fun onItemClicked(dataMovie: MovieEntity?, dataTV: TVEntity?) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.DATA_ID, dataTV?.id)
        intent.putExtra(DetailActivity.DATA_TYPE, Helper.TV_TYPE)
        startActivity(intent)
    }
}