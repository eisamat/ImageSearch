package com.eis.imagesearch.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eis.imagesearch.databinding.FragmentImageDetailsBinding
import com.eis.imagesearch.domain.models.Image
import com.squareup.picasso.Picasso

class ImageDetailsFragment : Fragment()
{
    private var _binding: FragmentImageDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        _binding = FragmentImageDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        val image = requireArguments().get(IMAGE_DETAILS_KEY) as Image

        binding.apply()
        {
            userLabel.text = image.userName
            tagLabel.text = image.tagsToString()
            likesLabel.text = image.likes.toString()
            downloadLabel.text = image.downloads.toString()
            commentLabel.text = image.comments.toString()

            Picasso.get()
                .load(image.image)
                .into(binding.image)
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

    companion object
    {
        const val IMAGE_DETAILS_KEY = "image.details.key"
    }
}