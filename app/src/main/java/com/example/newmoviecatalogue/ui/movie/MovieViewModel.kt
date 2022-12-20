package com.example.newmoviecatalogue.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.newmoviecatalogue.data.model.Movie
import com.example.newmoviecatalogue.data.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieRepository: MovieRepository
): ViewModel() {

    // Backing property to avoid state updates from other classes
    //private val _uiState = MutableStateFlow(MovieUiState.Success(emptyList()))
    // The UI collects from this StateFlow to get its state updates
    //val uiState: StateFlow<MovieUiState> = _uiState

    init {
//        viewModelScope.launch {
//            // Trigger the flow and consume its elements using collect
//            movieRepository.getAllMovie.collect { allMovie ->
//                _uiState.value = MovieUiState.Success(allMovie)
//            }
//        }
        getAllMovie()
    }

    fun getAllMovie(): Flow<PagingData<Movie>> {
        return movieRepository.getAllMovie
    }
}

sealed class MovieUiState {
    data class Success(val movies: PagingData<Movie>): MovieUiState()
    data class Error(val exception: Throwable): MovieUiState()
}
