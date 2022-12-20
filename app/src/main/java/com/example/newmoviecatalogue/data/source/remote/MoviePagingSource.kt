package com.example.newmoviecatalogue.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newmoviecatalogue.data.model.Movie
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviePagingSource(
    private val service: MovieService
): PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: STARTING_PAGE_INDEX
            val response = service.getMovie(
                page = nextPageNumber.toString()
            )
            val movies = response.results
            val nextKey =
                if (movies.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    nextPageNumber + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            return LoadResult.Page(
                data = movies,
                prevKey = null, // Only paging forward.
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
