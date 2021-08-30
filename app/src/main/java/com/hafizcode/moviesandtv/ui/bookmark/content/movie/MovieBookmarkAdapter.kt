package com.hafizcode.moviesandtv.ui.bookmark.content.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hafizcode.moviesandtv.data.entity.MovieEntity
import com.hafizcode.moviesandtv.databinding.ItemRowBinding
import com.hafizcode.moviesandtv.utils.Helper

class MovieBookmarkAdapter(private val callback: MovieBookmarkFragment) :
    RecyclerView.Adapter<MovieBookmarkAdapter.MovieViewHolder>() {

    private val listBookmarkedMovies = ArrayList<MovieEntity>()

    fun setBookmarkedMovies(movies: List<MovieEntity>?) {
        if (movies == null) return
        this.listBookmarkedMovies.clear()
        this.listBookmarkedMovies.addAll(movies)
        notifyDataSetChanged()
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
        val bookmarkedMovies = listBookmarkedMovies[position]
        holder.bind(bookmarkedMovies)
    }

    override fun getItemCount(): Int {
        return listBookmarkedMovies.size
    }
}