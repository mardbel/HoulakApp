package com.example.houlakapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.houlakapp.R
import com.example.houlakapp.databinding.FragmentSearchArtistBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchArtistFragment : Fragment() {

    lateinit var binding : FragmentSearchArtistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_artist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchArtistBinding.bind(view)

        binding.searchButton.setOnClickListener {
            Toast.makeText(activity, "apretaste el boton", Toast.LENGTH_LONG).show()
        }


    }
}