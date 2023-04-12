package ru.iohin.songschords.feature_search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import ru.iohin.songschords.feature_search.R
import ru.iohin.songschords.feature_search.di.SearchFragmentComponent
import javax.inject.Inject

class SearchFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: SearchViewModel.Factory
    private val viewModel: SearchViewModel by viewModels { viewModelFactory }
    private lateinit var recyclerView: RecyclerView
    private val artistsAdapter = ArtistsAdapter().apply {
        onBottomReached = {
            viewModel.loadMore()
        }
    }
    private lateinit var spinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SearchFragmentComponent.getSearchFragmentComponent(this)
            .inject(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when(state) {
                        is SearchState.LoadingSearchState -> spinner.visibility = View.VISIBLE
                        is SearchState.ErrorSearchState -> {
                            spinner.visibility = View.GONE
                            showError(state.message)
                        }
                        is SearchState.SearchResultsState -> {
                            spinner.visibility = View.GONE
                            artistsAdapter.artists = state.results
                        }
                    }
                }
            }
        }
    }

    private fun showError(message: String) {
        MaterialAlertDialogBuilder(requireActivity())
            .setMessage(message)
            .show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        spinner = view.findViewById(R.id.spinner)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = artistsAdapter
        return view
    }
}