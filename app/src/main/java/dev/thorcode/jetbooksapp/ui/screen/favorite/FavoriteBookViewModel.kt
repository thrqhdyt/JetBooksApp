package dev.thorcode.jetbooksapp.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.thorcode.jetbooksapp.data.BookRepository
import dev.thorcode.jetbooksapp.model.Book
import dev.thorcode.jetbooksapp.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteBookViewModel(private val repository: BookRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Book>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Book>>> get() = _uiState

    fun getFavoriteBook() {
        viewModelScope.launch {
            repository.getFavoriteBook()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { book ->
                    _uiState.value = UiState.Success(book)
                }
        }
    }
}