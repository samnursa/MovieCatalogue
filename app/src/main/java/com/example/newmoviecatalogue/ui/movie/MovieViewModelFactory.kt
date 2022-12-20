package com.example.newmoviecatalogue.ui.movie

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newmoviecatalogue.data.repositories.MovieRepository
import com.example.newmoviecatalogue.data.source.remote.MovieClient
import com.example.newmoviecatalogue.data.source.remote.MovieRemoteDataSource

internal class MovieViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java))
            return MovieViewModel(MovieRepository(MovieRemoteDataSource(MovieClient().provideClientMovie(context)))) as T
        throw IllegalArgumentException()
    }
}