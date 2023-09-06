package com.example.houlakapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.houlakapp.R
import com.example.houlakapp.databinding.FragmentArtistDetailBinding
import com.example.houlakapp.databinding.FragmentSearchArtistBinding
import com.example.houlakapp.viewModels.SearchArtistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArtistDetailFragment : Fragment() {

    private lateinit var binding : FragmentArtistDetailBinding
    //private val viewModel by viewModels<SearchArtistViewModel>()
    private val args: ArtistDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artist_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArtistDetailBinding.bind(view)

        binding.idTv.text = args.id

    }
}