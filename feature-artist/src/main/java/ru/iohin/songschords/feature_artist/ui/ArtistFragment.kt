package ru.iohin.songschords.feature_artist.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.iohin.songschords.feature_song.R

private const val ARG_ID = "id"
private const val ARG_NAME = "name"
private const val ARG_IMAGE_URL = "imageUrl"

class ArtistFragment : Fragment() {
    private var artistId: Int = 0
    private var artistName: String = ""
    private var artistImageUrl: String = ""
    private lateinit var nameTextView: TextView
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            artistId = it.getInt(ARG_ID)
            artistName = it.getString(ARG_NAME) ?: ""
            artistImageUrl = it.getString(ARG_IMAGE_URL) ?: ""
        }
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameTextView = view.findViewById(R.id.artist_name)
        nameTextView.text = artistName
        ViewCompat.setTransitionName(nameTextView, "artist_name${artistId}")
        imageView = view.findViewById(R.id.artist_image)
        ViewCompat.setTransitionName(imageView, "artist_image${artistId}")
        postponeEnterTransition()
        Glide.with(this)
            .load(artistImageUrl)
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
    }
}