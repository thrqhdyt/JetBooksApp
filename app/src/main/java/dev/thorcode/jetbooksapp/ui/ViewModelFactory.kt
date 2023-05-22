package dev.thorcode.jetbooksapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.thorcode.jetbooksapp.data.BookRepository
import dev.thorcode.jetbooksapp.ui.screen.about.AboutViewModel
import dev.thorcode.jetbooksapp.ui.screen.detail.DetailBookViewModel
import dev.thorcode.jetbooksapp.ui.screen.favorite.FavoriteBookViewModel
import dev.thorcode.jetbooksapp.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: BookRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailBookViewModel::class.java)) {
            return DetailBookViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteBookViewModel::class.java)) {
            return FavoriteBookViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AboutViewModel::class.java)) {
            return AboutViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}