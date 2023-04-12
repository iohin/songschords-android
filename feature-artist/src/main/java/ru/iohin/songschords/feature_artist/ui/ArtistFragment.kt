package ru.iohin.songschords.feature_artist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.iohin.songschords.core_api.entity.ArtistShort
import ru.iohin.songschords.feature_song.R

private const val ARG_NAME = "name"

class ArtistFragment : Fragment() {
    private var artistName: String = ""
    private lateinit var nameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            artistName = it.getString(ARG_NAME) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_artist, container, false)
        nameTextView = view.findViewById(R.id.artist_name)
        nameTextView.text = artistName
        return view
    }
}