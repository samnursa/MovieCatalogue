package com.example.newmoviecatalogue.data.repositories

import androidx.paging.PagingData
import com.example.newmoviecatalogue.data.model.Movie
import com.example.newmoviecatalogue.data.source.remote.MovieRemoteDataSource
import kotlinx.coroutines.flow.Flow

class MovieRepository(
    movieRemoteDataSource: MovieRemoteDataSource
) {

    val getAllMovie: Flow<PagingData<Movie>> =
        movieRemoteDataSource.getMovies()
            // Intermediate operation to filter the list of favorite topics
            //.map { news -> news.filter { userData.isFavoriteTopic(it) } }
            //.onEach { news -> saveInCache(news) }
            //.catch { emit( lastCachedNews())}
}