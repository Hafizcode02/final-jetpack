package com.hafizcode.moviesandtv.ui.bookmark.content.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.databinding.ItemRowBinding
import com.hafizcode.moviesandtv.utils.Helper

class TVBookmarkAdapter(private val callback: TVBookmarkFragment) :
    RecyclerView.Adapter<TVBookmarkAdapter.TvViewHolder>() {

    private val listBookmarkedTvs = ArrayList<TVEntity>()

    fun setBookmarkedTvs(tvs: List<TVEntity>?) {
        if (tvs == null) return
        this.listBookmarkedTvs.clear()
        this.listBookmarkedTvs.addAll(tvs)
        notifyDataSetChanged()
    }

    inner class TvViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TVEntity) {
            with(binding) {
                tvTitle.text = data.title
                Helper.setImageWithGlide(
                    itemView.context,
                    Helper.IMAGE_API_ENDPOINT + data.imgPoster,
                    imgItemPhoto
                )
                itemView.setOnClickListener {
                    callback.onItemClicked(null, data)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TVBookmarkAdapter.TvViewHolder {
        val itemRowBinding =
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemRowBinding)
    }

    override fun onBindViewHolder(holder: TVBookmarkAdapter.TvViewHolder, position: Int) {
        val bookmarkedTvs = listBookmarkedTvs[position]
        holder.bind(bookmarkedTvs)
    }

    override fun getItemCount(): Int {
        return listBookmarkedTvs.size
    }
}