package com.example.houlakapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.houlakapp.R
import com.example.houlakapp.databinding.TopSongsViewHolderBinding
import com.example.houlakapp.model.Track

class TopSongsAdapter: RecyclerView.Adapter<TopSongsAdapter.SongsViewHolder>() {

    private var mItems: List<Track> = listOf()

    fun setItems(items: List<Track>) {
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SongsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SongsViewHolder(layoutInflater.inflate(R.layout.top_songs_view_holder, parent, false))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: SongsViewHolder, position: Int) {
        holder.bind(mItems[position])
    }

    class SongsViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        private val binding = TopSongsViewHolderBinding.bind(view)

        fun bind(item: Track?) {
            binding.songNameTv.text = item?.name
            /*Glide.with(binding.imgHomeHolder.context).load(item.images.firstOrNull()?.url ?: "https://storage.googleapis.com/proudcity/mebanenc/uploads/2021/03/placeholder-image.png")
                .into(binding.imgHomeHolder)*/
        }
    }

}