package com.example.houlakapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.houlakapp.R
import com.example.houlakapp.databinding.FragmentArtistDetailBinding
import com.example.houlakapp.model.Artist
import com.example.houlakapp.model.Track
import com.example.houlakapp.viewModels.ArtistDetailViewModel
import com.example.houlakapp.viewModels.ArtistDetailState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistDetailFragment : Fragment() {

    private lateinit var binding : FragmentArtistDetailBinding
    private val args: ArtistDetailFragmentArgs by navArgs()
    private val viewModel by viewModels<ArtistDetailViewModel>()
    private lateinit var mAdapter : TopSongsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artist_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArtistDetailBinding.bind(view)

        viewModel.getArtistById(args.id!!)
        viewModel.getTopTracks(args.id!!)

        viewModel.state.observe(viewLifecycleOwner) {
            if (!it.expiredToken) {
                setupUi(it.artist, it.topTracks)
            } else Toast.makeText(activity?.applicationContext, getString(R.string.generic_error), Toast.LENGTH_LONG).show()
        }

    }

    private fun setupUi(artistData: Artist?, topSongs: List<Track>) {
        binding.nameTv.text = artistData?.name
        val genresAsList = mutableListOf<String>()
        artistData?.genres?.forEach {
            genresAsList.add(it)
        }
        val popularity = artistData?.popularity.toString()
        binding.popularityTv.text = "Popularity: $popularity"
        binding.genresTv.text = if (genresAsList.isNotEmpty()) genresAsList.toString() else getString(R.string.no_genre)
        Glide.with(binding.photoIv.context).load(
            if(artistData?.images.isNullOrEmpty()) { getString(R.string.image_place_holder)
            } else artistData!!.images.random().url
        ).into(binding.photoIv)

        binding.topSongsRv.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = TopSongsAdapter()
        binding.topSongsRv.adapter = mAdapter
        mAdapter.setItems(topSongs)

    }
}