package dev.thorcode.jetbooksapp.data

import dev.thorcode.jetbooksapp.R
import dev.thorcode.jetbooksapp.model.Book
import dev.thorcode.jetbooksapp.model.FakeBookDataSource
import dev.thorcode.jetbooksapp.model.MyProfile
import dev.thorcode.jetbooksapp.ui.common.UiState
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepository {

    private val books = mutableListOf<Book>()

    init {
        if (books.isEmpty()) {
            FakeBookDataSource.dummyBooks.forEach {
                books.add(it)
            }
        }
    }

    fun getAllBooks(): Flow<List<Book>> {
        return flowOf(books)
    }

    fun getBookById(bookId: Long): Book {
        return books.first() {
            it.id == bookId
        }
    }

    fun getFavoriteBook(): Flow<List<Book>> {
        return getAllBooks()
            .map { book ->
                book.filter {
                    it.isFavorite
                }
            }
    }

    fun updateIsFavorite(bookId: Long, isFav: Boolean): Flow<Boolean> {
        val index = books.indexOfFirst { it.id == bookId }
        val result = if (index >= 0) {
            val book = books[index]
            books[index] = book.copy(isFavorite = isFav)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun searchBooks(query: String): UiState<List<Book>> {
        val filteredBooks = FakeBookDataSource.dummyBooks.filter {
            it.title.contains(query, ignoreCase = true)
        }

        return if (filteredBooks.isNotEmpty()) {
            UiState.Success(filteredBooks)
        } else {
            UiState.Error(R.string.book_is_not_found.toString())
        }
    }

    fun getMyProfile(): MyProfile {
        return MyProfile(
            image = R.drawable.about_profile,
            name = "Thoriq Hidayat",
            email = "thoriq1130@gmail.com"
        )
    }

    companion object {
        @Volatile
        private var instance: BookRepository? = null

        fun getInstance(): BookRepository =
            instance?: synchronized(this) {
                BookRepository().apply {
                    instance = this
                }
            }
    }
}