package com.hafizcode.moviesandtv.ui.home.content.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hafizcode.moviesandtv.data.entity.TVEntity
import com.hafizcode.moviesandtv.databinding.ItemRowBinding
import com.hafizcode.moviesandtv.ui.detail.DetailActivity
import com.hafizcode.moviesandtv.utils.Helper
import com.hafizcode.moviesandtv.utils.Helper.IMAGE_API_ENDPOINT
import com.hafizcode.moviesandtv.utils.Helper.setImageWithGlide

class TvAdapter : PagedListAdapter<TVEntity, TvAdapter.TvViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TVEntity>() {
            override fun areItemsTheSame(oldItem: TVEntity, newItem: TVEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TVEntity, newItem: TVEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class TvViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TVEntity) {
            with(binding) {
                tvTitle.text = data.title
                setImageWithGlide(
                    itemView.context,
                    IMAGE_API_ENDPOINT + data.imgPoster,
                    imgItemPhoto
                )
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.DATA_ID, data.id)
                    intent.putExtra(DetailActivity.DATA_TYPE, Helper.TV_TYPE)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvViewHolder {
        val itemRowBinding =
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvViewHolder(itemRowBinding)
    }

    override fun onBindViewHolder(holder: TvViewHolder, position: Int) {
        val tvs = getItem(position)
        if (tvs != null) {
            holder.bind(tvs)
        }
    }
}