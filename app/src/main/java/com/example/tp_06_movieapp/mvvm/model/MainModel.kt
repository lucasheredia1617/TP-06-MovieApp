package com.example.tp_06_movieapp.mvvm.model

import com.example.tp_06_movieapp.database.MovieDatabase
import com.example.tp_06_movieapp.mvvm.contract.MainContract
import com.example.tp_06_movieapp.service.MovieService
import com.example.tp_06_movieapp.service.model.Movie
import com.example.tp_06_movieapp.util.CoroutineResult

class MainModel(
    private val service: MovieService,
    private val database: MovieDatabase
) : MainContract.Model {
    override suspend fun getMovies(): CoroutineResult<List<Movie>> {
        return when (val movies = service.getPopularMovies()) {
            is CoroutineResult.Success -> {
                database.insertMovies(movies.data.results)
                CoroutineResult.Success(database.getAllMovies())
            }
            is CoroutineResult.Failure -> {
                CoroutineResult.Success(database.getAllMovies())
                /*
                Show error fragment:
                CoroutineResult.Failure(Exception())
                 */
            }
        }
    }
}
