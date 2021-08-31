package com.hafizcode.moviesandtv.ui.bookmark.content.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.databinding.ItemRowBinding
import com.hafizcode.moviesandtv.utils.Helper

class MovieBookmarkAdapter(private val callback: MovieBookmarkFragment) :
    PagedListAdapter<MovieEntity, MovieBookmarkAdapter.MovieViewHolder>(
        DIFF_CALLBACK
    ) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MovieViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieEntity) {
            with(binding) {
                tvTitle.text = data.title
                Helper.setImageWithGlide(
                    itemView.context,
                    Helper.IMAGE_API_ENDPOINT + data.imgPoster,
                    imgItemPhoto
                )
                itemView.setOnClickListener {
                    callback.onItemClicked(data, null)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieBookmarkAdapter.MovieViewHolder {
        val itemRowBinding =
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemRowBinding)
    }

    override fun onBindViewHolder(holder: MovieBookmarkAdapter.MovieViewHolder, position: Int) {
        val bookmarkedMovies = getItem(position)
        if (bookmarkedMovies != null) {
            holder.bind(bookmarkedMovies)
        }
    }
}