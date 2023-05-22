package dev.thorcode.jetbooksapp.ui.screen.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.thorcode.jetbooksapp.data.BookRepository
import dev.thorcode.jetbooksapp.model.MyProfile
import dev.thorcode.jetbooksapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AboutViewModel(private val repository: BookRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<MyProfile>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<MyProfile>> get() = _uiState

    fun getMyProfile() {
        viewModelScope.launch {
            repository.getMyProfile()
                _uiState.value = UiState.Success(repository.getMyProfile())
        }
    }
}