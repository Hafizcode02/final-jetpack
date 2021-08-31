package com.hafizcode.moviesandtv.ui.home.content.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.hafizcode.moviesandtv.databinding.FragmentTvBinding
import com.hafizcode.moviesandtv.ui.home.content.helper.DataViewModel
import com.hafizcode.moviesandtv.viewmodel.ViewModelFactory

class TvFragment : Fragment() {

    private lateinit var fragmentTvBinding: FragmentTvBinding
    private lateinit var viewModel: DataViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTvBinding = FragmentTvBinding.inflate(layoutInflater, container, false)
        return fragmentTvBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentTvBinding.rvTv.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = TvAdapter()
        }

        val factory = ViewModelFactory.getInstance(requireActivity())
        activity?.let {
            viewModel = ViewModelProvider(it, factory)[DataViewModel::class.java]
        }

        viewModel.getTvs().observe(viewLifecycleOwner, { listTV ->
            fragmentTvBinding.rvTv.adapter?.let { adapter ->
                when (adapter) {
                    is TvAdapter -> {
                        adapter.run { submitList(listTV.data) }
                    }
                }
            }
        })

    }
}