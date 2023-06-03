package ru.iohin.songschords.feature_artist.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.iohin.feature.artist.nav.NavigationToArtist.Companion.SHARED_ARTIST_IMAGE
import ru.iohin.feature.artist.nav.NavigationToArtist.Companion.SHARED_ARTIST_NAME
import ru.iohin.songschords.feature_artist.R
import ru.iohin.songschords.feature_artist.di.ArtistFragmentComponent
import javax.inject.Inject

class ArtistFragment : Fragment(R.layout.fragment_artist) {
    private val args: ArtistFragmentArgs by navArgs()
    @Inject
    lateinit var viewModelFactory: ArtistViewModel.Factory
    private val viewModel: ArtistViewModel by viewModels { viewModelFactory }

    private lateinit var nameTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var imageView: ImageView
    private lateinit var recyclerView: RecyclerView
    private val songsAdapter = SongsAdapter().apply {
        onBottomReached = {
            viewModel.loadMore(args.id)
        }
        onSongClick = SongsAdapter.OnSongClick { song, sharedNameView ->

        }
    }
    private lateinit var spinner: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ArtistFragmentComponent.getArtistFragmentComponent(this).inject(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is ArtistState.LoadingArtistState -> spinner.visibility = View.VISIBLE
                        is ArtistState.ErrorArtistState -> {
                            spinner.visibility = View.GONE
                            showError(state.message)
                        }
                        is ArtistState.SuccessArtistState -> {
                            spinner.visibility = View.GONE
                            descriptionTextView.text = state.result.description
                            songsAdapter.songs = state.result.songs
                        }
                    }
                }
            }
        }
        viewModel.loadArtist(args.id)
        viewModel.loadSongs(args.id)

        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
    }

    private fun showError(message: String) {
        MaterialAlertDialogBuilder(requireActivity())
            .setMessage(message)
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameTextView = view.findViewById(R.id.artist_name)
        nameTextView.text = args.name
        ViewCompat.setTransitionName(
            nameTextView,
            "${SHARED_ARTIST_NAME}${args.id}"
        )
        descriptionTextView = view.findViewById(R.id.artist_description)
        imageView = view.findViewById(R.id.artist_image)
        ViewCompat.setTransitionName(
            imageView,
            "${SHARED_ARTIST_IMAGE}${args.id}"
        )
        postponeEnterTransition()
        Glide.with(this)
            .load(args.imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            })
            .into(imageView)

        spinner = view.findViewById(R.id.spinner)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = songsAdapter
        postponeEnterTransition()
        recyclerView.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }
}