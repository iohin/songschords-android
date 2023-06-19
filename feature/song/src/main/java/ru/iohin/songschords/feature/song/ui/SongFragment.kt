package ru.iohin.songschords.feature.song.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import kotlinx.coroutines.launch
import ru.iohin.chords.ChordsListView
import ru.iohin.songschords.feature.song.R
import ru.iohin.songschords.feature.song.di.SongFragmentComponent
import ru.iohin.songschords.feature.song.nav.NavigationToSong
import javax.inject.Inject

class SongFragment : Fragment(R.layout.fragment_song) {
    private val args: SongFragmentArgs by navArgs()
    @Inject
    lateinit var viewModelFactory: SongViewModel.Factory
    private val viewModel: SongViewModel by viewModels { viewModelFactory }

    private lateinit var songNameView: TextView
    private lateinit var artistNameView: TextView
    private lateinit var chordsListView: ChordsListView
    private lateinit var contentView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SongFragmentComponent.getSongFragmentComponent(this).inject(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is SongState.SuccessSongState -> {
                            chordsListView.chords = state.song.chords.toTypedArray()
                            contentView.text = ContentConvertor.convert(state.song.content)
                        }
                        is SongState.ErrorSongState -> showError(state.message)
                        is SongState.LoadingSongState -> {}
                    }
                }
            }
        }

        viewModel.loadSong(args.id)
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
    }

    private fun showError(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songNameView = view.findViewById(R.id.song_name)
        artistNameView = view.findViewById(R.id.artist_name)
        chordsListView = view.findViewById(R.id.chords_list)
        contentView = view.findViewById(R.id.content)

        ViewCompat.setTransitionName(
            songNameView,
            "${NavigationToSong.SHARED_NAME}${args.id}"
        )
        ViewCompat.setTransitionName(
            artistNameView,
            "${NavigationToSong.SHARED_ARTIST_NAME}${args.artistId}"
        )
        ViewCompat.setTransitionName(
            contentView,
            "${NavigationToSong.SHARED_CONTAINER}${args.id}"
        )

        songNameView.text = Uri.decode(args.name)
        artistNameView.text = Uri.decode(args.artistName)
    }
}