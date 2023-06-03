package ru.iohin.songschords.feature.search.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import ru.iohin.feature.artist.nav.NavigationToArtist
import ru.iohin.songschords.core.api.navigation.AppNavigation
import ru.iohin.songschords.feature.search.R
import ru.iohin.songschords.feature.search.di.SearchFragmentComponent
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {
    @Inject
    lateinit var viewModelFactory: SearchViewModel.Factory
    @Inject
    lateinit var appNavigation: AppNavigation
    private val viewModel: SearchViewModel by viewModels { viewModelFactory }
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

        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
    }

    private fun showError(message: String) {
        MaterialAlertDialogBuilder(requireActivity())
            .setMessage(message)
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val hold = Hold()
//        hold.addTarget(view)
//        hold.duration = 150
//        exitTransition = hold

        spinner = view.findViewById(R.id.spinner)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = artistsAdapter
        postponeEnterTransition()
        recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }
}