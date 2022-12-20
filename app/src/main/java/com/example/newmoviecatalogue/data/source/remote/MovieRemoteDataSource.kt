package com.example.newmoviecatalogue.data.source.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newmoviecatalogue.data.model.Movie
import com.example.newmoviecatalogue.data.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

const val NETWORK_PAGE_SIZE = 25

class MovieRemoteDataSource(
    private val movieService: MovieService
) {
//    val getMovie: Flow<List<Movie>> = flow {
//        while(true) {
//            val allMovie = apiService.getMovie().results
//            emit(allMovie) // Emits the result of the request to the flow
//        }
//    }

    fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(service = movieService)
            }
        ).flow
    }
}