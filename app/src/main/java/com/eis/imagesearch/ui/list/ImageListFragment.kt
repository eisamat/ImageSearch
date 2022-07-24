package com.eis.imagesearch.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.eis.imagesearch.R
import com.eis.imagesearch.databinding.FragmentImageListBinding
import com.eis.imagesearch.domain.models.Image
import com.eis.imagesearch.ui.details.ImageDetailsFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ImageListFragment : Fragment(), ImagesAdapterListener
{
    private var _binding: FragmentImageListBinding? = null
    private val binding get() = _binding!!

    private val adapter = ImagesAdapter(this)
    private val viewModel: ImageListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        _binding = FragmentImageListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        binding.apply()
        {
            imagesList.adapter = adapter

            imagesList.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            imagesList.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

            queryInput.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    hideKeyboard()
                    viewModel.searchImages(queryInput.text.toString())
                    return@OnEditorActionListener true
                }
                false
            })
        }

        lifecycleScope.launch()
        {
            viewModel.observeUI().collect()
            {
                updateUI(it)
            }
        }
    }

    private fun updateUI(state: ImageListViewModel.UIState)
    {
        when (state)
        {
            ImageListViewModel.UIState.Loading    -> showLoadingBar()
            is ImageListViewModel.UIState.Success -> showItems(state)
        }
    }

    private fun showItems(state: ImageListViewModel.UIState.Success)
    {
        binding.contentGroup.visibility = View.VISIBLE
        binding.loadingBar.visibility = View.GONE

        binding.resultCount.text =
            getString(R.string.results_count_label, state.items.size, state.query)

        adapter.submitList(state.items)
    }

    private fun showLoadingBar()
    {
        binding.contentGroup.visibility = View.GONE
        binding.loadingBar.visibility = View.VISIBLE
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

    override fun onImageClicked(image: Image)
    {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage(R.string.open_more_details)
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ ->
                val data = Bundle()
                data.putSerializable(ImageDetailsFragment.IMAGE_DETAILS_KEY, image)
                findNavController().navigate(R.id.action_list_to_details, data)
            }
            .setNegativeButton(
                android.R.string.cancel
            ) { _, _ ->

            }

        val dialog = builder.create()
        dialog.show()
    }

    fun hideKeyboard()
    {
        val view: View? = requireActivity().currentFocus

        if (view != null)
        {
            val imm: InputMethodManager? = getSystemService(requireContext(), InputMethodManager::class.java)
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}