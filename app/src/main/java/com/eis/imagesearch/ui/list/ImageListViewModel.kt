package com.eis.imagesearch.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eis.imagesearch.domain.ImageRepository
import com.eis.imagesearch.domain.models.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val INITIAL_QUERY = "fruit"
private val EmptyState = ImageListViewModel.UIState.Success("", emptyList())

@HiltViewModel
class ImageListViewModel : ViewModel
{
    private val uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)

    sealed class UIState
    {
        object Loading : UIState()
        class Success(val query: String, val items: List<Image>) : UIState()
    }

    private val imageRepository: ImageRepository

    @Inject
    constructor(imageRepository: ImageRepository)
    {
        this.imageRepository = imageRepository
    }

    init
    {
        searchImages(INITIAL_QUERY)
    }

    fun observeUI(): Flow<UIState> = uiState

    fun searchImages(query: String)
    {
        if (query.isBlank())
        {
            uiState.value = EmptyState
            return
        }

        uiState.value = UIState.Loading

        viewModelScope.launch()
        {
            val images = imageRepository.getListOfImages(query)
            uiState.value = UIState.Success(query, images)
        }
    }
}