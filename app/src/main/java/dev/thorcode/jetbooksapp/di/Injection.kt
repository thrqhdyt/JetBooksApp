package dev.thorcode.jetbooksapp.di

import dev.thorcode.jetbooksapp.data.BookRepository

object Injection {
    fun provideRepository(): BookRepository {
        return BookRepository.getInstance()
    }
}