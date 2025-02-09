package com.example.testvk.imagelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testvk.R
import com.example.testvk.network.model.Image
import com.squareup.picasso.Picasso


class ImageAdapter : PagingDataAdapter<Image, ImageAdapter.ImageViewHolder>(ImageDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.image_list_item, parent, false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        getItem(position)?.let { image ->
            holder.itemView.findViewById<ProgressBar>(R.id.image_progress).visibility = View.VISIBLE
            Picasso.get()
                .load(image.poster.url)
                .into(holder.moviePoster, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        holder.itemView.findViewById<ProgressBar>(R.id.image_progress).visibility =
                            View.GONE
                    }

                    override fun onError(e: Exception?) {
                        holder.itemView.findViewById<ProgressBar>(R.id.image_progress).visibility =
                            View.GONE
                        holder.itemView.findViewById<ImageView>(R.id.image)
                            .setImageResource(R.drawable.stub)
                    }
                })
        }
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var moviePoster: ImageView = itemView.findViewById(R.id.image)
    }

    class ImageDiffCallback : DiffUtil.ItemCallback<Image>() {

        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean =
            oldItem == newItem
    }
}
