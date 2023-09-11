package com.example.houlakapp.presentation

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.ListFragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.houlakapp.R
import com.example.houlakapp.databinding.FragmentSearchArtistBinding
import com.example.houlakapp.viewModels.SearchArtistViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchArtistFragment : Fragment() {

    private lateinit var binding : FragmentSearchArtistBinding
    private val viewModel by viewModels<SearchArtistViewModel>()
    private lateinit var mAdapter : ArtistListAdapter

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
            val artistSearched = binding.searchEditText.text.toString()
            viewModel.searchArtistByName(artistSearched)
        }

        binding.artistRv.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = ArtistListAdapter(
            onItemClick = {
                val action = SearchArtistFragmentDirections.actionListFragmentToDetailFragment(it)
                view.findNavController().navigate(action)
            }
        )

        binding.artistRv.adapter = mAdapter

        binding.newSearchButton.setOnClickListener {
            viewModel.newSearch()
        }



        viewModel.searchArtistState.observe(viewLifecycleOwner) {
            if (it.isLoading) binding.progressBar.isVisible = true
                else {
                    if (!it.showSearcher) {
                        binding.searcherLayout.isVisible = false
                        mAdapter.setItems(it.artistList)
                        binding.artistRv.isVisible = true
                        binding.newSearchButton.isVisible = true
                        binding.progressBar.isVisible = false

                    } else {
                        binding.searcherLayout.isVisible = true
                        binding.artistRv.isVisible = false
                        binding.newSearchButton.isVisible = false
                        binding.progressBar.isVisible = false
                        binding.searchEditText.text.clear()
                    }
                }
        }
    }
}