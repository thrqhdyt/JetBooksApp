package dev.thorcode.jetbooksapp.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.thorcode.jetbooksapp.data.BookRepository
import dev.thorcode.jetbooksapp.model.Book
import dev.thorcode.jetbooksapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailBookViewModel(private val repository: BookRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Book>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Book>> get() = _uiState

    fun getBookById(bookId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getBookById(bookId))
        }
    }

    fun updateIsFavorite(bookId: Long, isFav: Boolean){
        viewModelScope.launch {
            repository.updateIsFavorite(bookId, isFav)
        }
    }
}