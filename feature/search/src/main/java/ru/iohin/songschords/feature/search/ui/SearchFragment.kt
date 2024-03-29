package ru.iohin.songschords.feature.search.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.doOnPreDraw
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import kotlinx.coroutines.launch
import ru.iohin.songschords.core.api.navigation.AppNavigation
import ru.iohin.songschords.feature.artist.nav.NavigationToArtist
import ru.iohin.songschords.feature.search.R
import ru.iohin.songschords.feature.search.di.SearchFragmentComponent
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {
    @Inject
    lateinit var viewModelFactory: SearchViewModel.Factory
    @Inject
    lateinit var appNavigation: AppNavigation
    private val viewModel: SearchViewModel by viewModels { viewModelFactory }
    private lateinit var searchBar: SearchBar
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private val artistsAdapter = ArtistsAdapter().apply {
        onBottomReached = {
            viewModel.loadMore()
        }
        onArtistClick =
            ArtistsAdapter.OnArtistClick { artist, sharedContainerView, sharedNameView, sharedImageView ->
            appNavigation.getNavigation(NavigationToArtist::class)?.navigate(
                artist.id,
                artist.name,
                artist.imageUrl,
                sharedContainerView,
                sharedNameView,
                sharedImageView
            )
        }
    }
    private lateinit var spinner: ProgressBar
    private lateinit var suggestionsRecyclerView: RecyclerView
    private val suggestionsAdapter = SuggestionsAdapter().apply {
        onItemClick = { suggestion ->
            performQuery(suggestion)
        }
    }
    private val onBackPressedCallback = object: OnBackPressedCallback(true) {


        override fun handleOnBackPressed() {
            if (searchView.isShowing) {
                searchView.hide()
            } else {
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
                isEnabled = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SearchFragmentComponent.getSearchFragmentComponent(this)
            .inject(this)

        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when(state) {
                        is SearchState.LoadingSearchState -> spinner.visibility = View.VISIBLE
                        is SearchState.ErrorSearchState -> {
                            spinner.visibility = View.GONE
                            showError(state.message)
                        }
                        is SearchState.SearchResultsState -> {
                            spinner.visibility = View.GONE
                            artistsAdapter.artists = state.searchData.results
                            suggestionsAdapter.suggestions = state.searchData.suggestions
                        }
                    }
                }
            }
        }

        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    private fun showError(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun performQuery(query: String) {
        artistsAdapter.artists = emptyList()
        searchBar.text = query
        searchView.hide()
        viewModel.search(query)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBar = view.findViewById(R.id.open_search_bar)
        searchView = view.findViewById(R.id.open_search_view)
        setupSearchView(searchView)
        spinner = view.findViewById(R.id.spinner)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = artistsAdapter

        suggestionsRecyclerView = view.findViewById(R.id.search_suggestions)
        suggestionsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        suggestionsRecyclerView.adapter = suggestionsAdapter

        postponeEnterTransition()
        recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView
            .editText
            .doOnTextChanged { s: CharSequence?, start: Int, before: Int, count: Int ->
                if (!searchView.isShowing) return@doOnTextChanged
                if (s?.isEmpty() == true) {
                    searchBar.text = ""
                    viewModel.search("")
                } else {
                    viewModel.loadSuggestions(s.toString())
                }
            }
        searchView
            .editText
            .setOnEditorActionListener { _: TextView?, actionId: Int, _: KeyEvent? ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performQuery(searchView.text.toString())
                }
                false
            }
    }
}