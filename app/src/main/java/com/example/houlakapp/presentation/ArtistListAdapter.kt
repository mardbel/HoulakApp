package com.example.houlakapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.houlakapp.R
import com.example.houlakapp.databinding.ArtistViewholderBinding
import com.example.houlakapp.model.Artist

class ArtistListAdapter(val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<ArtistListAdapter.ArtistViewHolder>() {

    private var mItems: List<Artist> = listOf()

    fun setItems(items: List<Artist>) {
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArtistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ArtistViewHolder(
            layoutInflater.inflate(R.layout.artist_viewholder, parent, false),
            onItemClick
        )
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(mItems[position])
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ArtistViewHolder(
        view: View,
        val onItemClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ArtistViewholderBinding.bind(view)

        fun bind(item: Artist) {
            binding.tvName.text = item.name
            Glide.with(binding.imgHomeHolder.context).load(item.images.first().url)
                .into(binding.imgHomeHolder)
            binding.root.setOnClickListener {
                onItemClick(item.id)
            }
        }
    }
}